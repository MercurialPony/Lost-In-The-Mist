package melonslise.spook.client.init;

import melonslise.spook.client.model.TwistedSheepFurModel;
import melonslise.spook.client.model.TwistedSheepModel;
import melonslise.spook.client.model.TwistedWolfModel;
import melonslise.spook.client.renderer.entity.TwistedCowRenderer;
import melonslise.spook.client.renderer.entity.TwistedMushroomCowRenderer;
import melonslise.spook.client.renderer.entity.TwistedPigRenderer;
import melonslise.spook.client.renderer.entity.TwistedSheepRenderer;
import melonslise.spook.client.renderer.entity.TwistedWolfRenderer;
import melonslise.spook.common.init.SpookEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
public final class SpookEntityRenderers
{
	private SpookEntityRenderers() {}

	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e)
	{
		e.registerLayerDefinition(TwistedSheepModel.LAYER, TwistedSheepModel::createLayer);
		e.registerLayerDefinition(TwistedWolfModel.LAYER, TwistedWolfModel::createLayer);
		e.registerLayerDefinition(TwistedSheepFurModel.LAYER, TwistedSheepFurModel::createLayer);
	}

	public static void register(EntityRenderersEvent.RegisterRenderers e)
	{
		e.registerEntityRenderer(SpookEntityTypes.TWISTED_PIG.get(), TwistedPigRenderer::new);
		e.registerEntityRenderer(SpookEntityTypes.TWISTED_SHEEP.get(), TwistedSheepRenderer::new);
		e.registerEntityRenderer(SpookEntityTypes.TWISTED_COW.get(), TwistedCowRenderer::new);
		e.registerEntityRenderer(SpookEntityTypes.TWISTED_WOLF.get(), TwistedWolfRenderer::new);
		e.registerEntityRenderer(SpookEntityTypes.TWISTED_MUSHROOM_COW.get(), TwistedMushroomCowRenderer::new);
	}
}