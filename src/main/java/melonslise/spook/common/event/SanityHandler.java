package melonslise.spook.common.event;

import melonslise.spook.SpookMod;
import melonslise.spook.common.capability.Sanity;
import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.init.SpookItemTags;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpookMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class SanityHandler
{
	@SubscribeEvent
	public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent e)
	{
		if(e.getPlayer() instanceof ServerPlayer player)
		{
			Sanity.sync(player);
		}
	}

	@SubscribeEvent
	public static void playerRespawned(PlayerEvent.PlayerRespawnEvent e)
	{
		if(e.getPlayer() instanceof ServerPlayer player)
		{
			if(!SpookyUtil.inFogworld(player))
			{
				player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(100f));
			}
			else
			{
				Sanity.sync(player);
			}
		}
	}

	@SubscribeEvent
	public static void playerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent e)
	{
		if(e.getPlayer() instanceof ServerPlayer player)
		{
			Sanity.sync(player);
		}
	}

	@SubscribeEvent
	public static void playerCloned(PlayerEvent.Clone e)
	{
		Player original = e.getOriginal();
		Player player = e.getPlayer();

		if(player.level.isClientSide || !e.isWasDeath())
		{
			return;
		}

		original.reviveCaps();

		original.getCapability(SpookCapabilities.SANITY).ifPresent(oldSanity ->
			player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity ->
				sanity.set(oldSanity.get())));

		original.invalidateCaps();
	}

	@SubscribeEvent
	public static void entityDied(LivingDeathEvent e)
	{
		LivingEntity entity = e.getEntityLiving();

		if(entity.getType().getCategory().isFriendly() && entity.getKillCredit() instanceof ServerPlayer player)
		{
			player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() - 5f));
		}
	}

	@SubscribeEvent
	public static void entityDamaged(LivingDamageEvent e)
	{
		if(e.getEntityLiving() instanceof ServerPlayer player)
		{
			float damage = Math.min(player.getHealth(), e.getAmount());
			player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() - damage * 1.5f));
		}
	}

	@SubscribeEvent
	public static void playerTick(TickEvent.PlayerTickEvent e)
	{
		if(e.phase != TickEvent.Phase.END && e.player instanceof ServerPlayer player && player.level.getGameTime() % 200 == 0) // every 10 seconds
		{
			int light = player.level.getMaxLocalRawBrightness(player.blockPosition());

			if(SpookyUtil.inFogworld(player))
			{
				if(light < 5)
				{
					player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() - 1f));
				}
			}
			else if(light > 11)
			{
				player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() + 1f));
			}
		}
	}

	@SubscribeEvent
	public static void blockBroken(BlockEvent.BreakEvent e)
	{
		BlockState state = e.getState();

		if(e.getPlayer() instanceof ServerPlayer player && state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state))
		{
			player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() + 3f));
		}
	}

	@SubscribeEvent
	public static void itemUsed(LivingEntityUseItemEvent.Finish e)
	{
		ItemStack stack = e.getItem();
		Item item = stack.getItem();

		if(e.getEntityLiving() instanceof ServerPlayer player && item.isEdible())
		{
			float sanityDelta = stack.is(SpookItemTags.BAD_FOOD) ? -3f : item.getFoodProperties().getNutrition(); // / 1.5f
			player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() + sanityDelta));
		}
	}

	@SubscribeEvent
	public static void itemFished(ItemFishedEvent e)
	{
		if(e.getPlayer() instanceof ServerPlayer player)
		{
			player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() + 3f));
		}
	}
}