package melonslise.spook.client.renderer.entity;

import melonslise.spook.SpookMod;
import melonslise.spook.common.entity.TwistedPig;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TwistedPigRenderer extends MobRenderer<TwistedPig, PigModel<TwistedPig>>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_pig/twisted_pig.png");

	public TwistedPigRenderer(EntityRendererProvider.Context ctx)
	{
		super(ctx, new PigModel<>(ctx.bakeLayer(ModelLayers.PIG)), 0.7f);
	}

	@Override
	public ResourceLocation getTextureLocation(TwistedPig pig)
	{
		return TEXTURE;
	}
}