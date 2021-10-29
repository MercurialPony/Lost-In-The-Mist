package melonslise.spook.client.renderer.entity;

import melonslise.spook.SpookMod;
import melonslise.spook.client.layer.TwistedSheepFurLayer;
import melonslise.spook.client.model.TwistedSheepModel;
import melonslise.spook.common.entity.TwistedSheep;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TwistedSheepRenderer extends MobRenderer<TwistedSheep, TwistedSheepModel<TwistedSheep>>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_sheep/twisted_sheep.png");

	public TwistedSheepRenderer(EntityRendererProvider.Context ctx)
	{
		super(ctx, new TwistedSheepModel<>(ctx.bakeLayer(TwistedSheepModel.LAYER)), 0.7f);
		this.addLayer(new TwistedSheepFurLayer(this, ctx.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(TwistedSheep sheep)
	{
		return TEXTURE;
	}
}