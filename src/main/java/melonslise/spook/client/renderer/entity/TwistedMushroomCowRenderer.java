package melonslise.spook.client.renderer.entity;

import melonslise.spook.SpookMod;
import melonslise.spook.client.layer.TwistedMushroomCowMushroomLayer;
import melonslise.spook.common.entity.TwistedMushroomCow;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TwistedMushroomCowRenderer extends MobRenderer<TwistedMushroomCow, CowModel<TwistedMushroomCow>>
{
	public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cow/twisted_red_mooshroom.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cow/twisted_brown_mooshroom.png") };

	public TwistedMushroomCowRenderer(EntityRendererProvider.Context ctx)
	{
		super(ctx, new CowModel<>(ctx.bakeLayer(ModelLayers.MOOSHROOM)), 0.7f);
		this.addLayer(new TwistedMushroomCowMushroomLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(TwistedMushroomCow cow)
	{
		return TEXTURES[0];
	}
}