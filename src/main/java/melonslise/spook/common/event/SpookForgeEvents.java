package melonslise.spook.common.event;

import java.util.ArrayDeque;

import melonslise.spook.SpookMod;
import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.init.SpookEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpookMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpookForgeEvents
{
	public static final ArrayDeque<Runnable> END_OF_TICK_ACTIONS = new ArrayDeque<>();

	private SpookForgeEvents() {}

	@SubscribeEvent
	public static void attachCapabilitiesToEntity(AttachCapabilitiesEvent<Entity> e)
	{
		SpookCapabilities.attachToEntity(e);
	}

	@SubscribeEvent
	public static void enhanceBiome(BiomeLoadingEvent e)
	{
		SpookEntityTypes.enchanceBiome(e);
	}

	@SubscribeEvent
	public static void serverTick(TickEvent.ServerTickEvent e)
	{
		if(e.phase != TickEvent.Phase.END)
			return;

		END_OF_TICK_ACTIONS.forEach(Runnable::run);
		END_OF_TICK_ACTIONS.clear();
	}

	@SubscribeEvent
	public static void playerCloned(PlayerEvent.Clone e)
	{
		Player original = e.getOriginal();
		Player player = e.getPlayer();

		if(player.level.isClientSide || !e.isWasDeath())
			return;

		original.reviveCaps();

		player.getCapability(SpookCapabilities.PLAYER_BACKUP).ifPresent(backup ->
			original.getCapability(SpookCapabilities.PLAYER_BACKUP).ifPresent(oldBackup ->
				backup.set(oldBackup.get())));

		original.invalidateCaps();
	}
}