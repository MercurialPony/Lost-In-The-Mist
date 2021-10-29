package melonslise.spook.common.event;

import melonslise.spook.SpookMod;
import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.init.SpookChunkGenerators;
import melonslise.spook.common.init.SpookEntityTypes;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = SpookMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SpookModEvents
{
	private SpookModEvents() {}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent e)
	{
		SpookChunkGenerators.register();
		SpookEntityTypes.registerSpawns();
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent e)
	{
		SpookCapabilities.register(e);
	}

	@SubscribeEvent
	public static void createAttributes(EntityAttributeCreationEvent e)
	{
		SpookEntityTypes.createAttributes(e);
	}
}