package melonslise.spook.client.event;

import melonslise.spook.SpookMod;
import melonslise.spook.client.init.SpookEntityRenderers;
import melonslise.spook.client.init.SpookParticles;
import melonslise.spook.client.init.SpookShaders;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpookMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SpookClientModEvents
{
	private SpookClientModEvents() {}

	@SubscribeEvent
	public static void registerReloadListeners(RegisterClientReloadListenersEvent e)
	{
		e.registerReloadListener(new SpookShaders());
	}

	@SubscribeEvent
	public static void registerParticleFactories(ParticleFactoryRegisterEvent e)
	{
		SpookParticles.register();
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers e)
	{
		SpookEntityRenderers.register(e);
	}

	@SubscribeEvent
	public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions e)
	{
		SpookEntityRenderers.registerLayers(e);
	}
}