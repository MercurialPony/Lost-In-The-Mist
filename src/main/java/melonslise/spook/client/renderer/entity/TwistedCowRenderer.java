package melonslise.spook.client.renderer.entity;

import melonslise.spook.SpookMod;
import melonslise.spook.common.entity.TwistedCow;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TwistedCowRenderer extends MobRenderer<TwistedCow, CowModel<TwistedCow>>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cow/twisted_cow.png");

	public TwistedCowRenderer(EntityRendererProvider.Context ctx)
	{
		super(ctx, new CowModel<>(ctx.bakeLayer(ModelLayers.COW)), 0.7f);
	}

	@Override
	public ResourceLocation getTextureLocation(TwistedCow cow)
	{
		return TEXTURE;
	}
}