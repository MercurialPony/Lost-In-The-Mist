package melonslise.spook.client.renderer.entity;

import melonslise.spook.SpookMod;
import melonslise.spook.client.model.TwistedWolfModel;
import melonslise.spook.common.entity.TwistedWolf;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TwistedWolfRenderer extends MobRenderer<TwistedWolf, TwistedWolfModel<TwistedWolf>>
{
	public static final ResourceLocation
		TEXTURE = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_wolf/twisted_wolf.png"),
		TAME_TEXTURE = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_wolf/twisted_wolf_tame.png"),
		ANGRY_TEXTURE = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_wolf/twisted_wolf_angry.png");

	public TwistedWolfRenderer(EntityRendererProvider.Context ctx)
	{
		super(ctx, new TwistedWolfModel<>(ctx.bakeLayer(ModelLayers.WOLF)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(TwistedWolf wolf)
	{
		return ANGRY_TEXTURE;
	}
}