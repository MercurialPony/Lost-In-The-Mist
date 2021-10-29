package melonslise.spook.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Streams;

import melonslise.spook.SpookMod;
import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.init.SpookDimensionKeys;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpookMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FogworldHandler
{
	public static final List<Entity>
		ENTITIES_TO_ADD = new ArrayList<>(),
		ENTITIES_TO_REMOVE = new ArrayList<>();

	@SubscribeEvent
	public static void entityAdded(EntityJoinWorldEvent e)
	{
		Entity entity = e.getEntity();

		if(!e.loadedFromDisk() && SpookyUtil.inFogworld(entity) && !SpookyUtil.allowedInFogworld(entity))
		{
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void validSleepLocation(SleepingLocationCheckEvent e)
	{
		if(SpookyUtil.inFogworld(e.getEntityLiving()))
		{
			e.setResult(Result.ALLOW);
		}
	}

	@SubscribeEvent
	public static void validSleepTime(SleepingTimeCheckEvent e)
	{
		if(SpookyUtil.inFogworld(e.getEntityLiving()))
		{
			e.setResult(Result.ALLOW);
		}
	}

	@SubscribeEvent
	public static void entityDied(LivingDeathEvent e)
	{
		if(e.getEntityLiving() instanceof ServerPlayer player)
		{
			if(tryUpdate(player))
			{
				player.setRespawnPosition(SpookDimensionKeys.FOGWORLD, player.getRespawnPosition(), player.getRespawnAngle(), player.isRespawnForced(), false);
			}
		}
	}

	// FIXME if there were multiple players in fogworld, one left, the others went back to OW, and the other guy reconnects then there will be no entities
	@SubscribeEvent
	public static void playerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent e)
	{
		if(e.getPlayer().level.isClientSide)
		{
			return;
		}

		ServerPlayer player = (ServerPlayer) e.getPlayer();
		ServerLevel level = player.getLevel();

		long playerCount = level.getServer().getLevel(SpookDimensionKeys.FOGWORLD).players().size();

		if(e.getTo() == SpookDimensionKeys.FOGWORLD)
		{
			// FIXME do this only if the player entered through sleeping
			// player.startSleeping(player.blockPosition());

			player.getCapability(SpookCapabilities.PLAYER_BACKUP).ifPresent(backup ->
			{
				backup.backup();
				backup.clearPlayer();
			});

			player.setRespawnPosition(SpookDimensionKeys.FOGWORLD, player.getRespawnPosition(), player.getRespawnAngle(), player.isRespawnForced(), false);
	
			if(playerCount > 1)
			{
				return;
			}

			for(Entity entity : ENTITIES_TO_ADD)
			{
				level.addFreshEntity(entity);
			}
	
			ENTITIES_TO_ADD.clear();
		}
		else if(e.getFrom() == SpookDimensionKeys.FOGWORLD)
		{
			player.getCapability(SpookCapabilities.PLAYER_BACKUP).ifPresent(backup ->
			{
				backup.restore();
			});

			player.setRespawnPosition(Level.OVERWORLD, player.getRespawnPosition(), player.getRespawnAngle(), player.isRespawnForced(), false);

			if(playerCount > 0)
			{
				return;
			}

			for(Entity entity : level.getServer().getLevel(e.getFrom()).getEntities().getAll())
			{
				if(entity == null || entity instanceof Player || entity instanceof Mob)
				{
					continue;
				}

				ENTITIES_TO_REMOVE.add(entity);
			}

			// avoid out of bounds
			for(Entity entity : ENTITIES_TO_REMOVE)
			{
				entity.discard();
			}

			ENTITIES_TO_REMOVE.clear();
		}
	}

	public static void update(MinecraftServer server)
	{
		if(server.getLevel(SpookDimensionKeys.FOGWORLD).players().size() > 0)
		{
			return;
		}

		ServerLevel overworld = server.getLevel(Level.OVERWORLD);
		ServerLevel fogworld = server.getLevel(SpookDimensionKeys.FOGWORLD);

		overworld.getChunkSource().chunkMap.visibleChunkMap.values().stream()
			.map(holder -> holder.getTickingChunk())
			.filter(Objects::nonNull)
			.forEach(chunk -> SpookyUtil.replaceChunk(chunk, fogworld.getChunkSource().getChunk(chunk.getPos().x, chunk.getPos().z, true)));

		ENTITIES_TO_ADD.clear();

		Streams.stream(overworld.getEntities().getAll())
			.filter(SpookyUtil::allowedInFogworld)
			.filter(entity -> !(entity instanceof Player))
			.map(entity -> SpookyUtil.copy(entity, fogworld))
			.forEach(ENTITIES_TO_ADD::add);
	}

	public static boolean tryUpdate(ServerPlayer player)
	{
		if(player.level.dimension() != Level.OVERWORLD)
		{
			return false;
		}

		float chance = player.getCapability(SpookCapabilities.SANITY).map(sanity -> sanity.getChance()).orElse(0f);

		if(SpookyUtil.chance(player.level.random, chance))
		{
			FogworldHandler.update(player.server);

			return true;
		}

		return false;
	}
}