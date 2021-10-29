package melonslise.spook.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import melonslise.spook.client.model.TwistedSheepFurModel;
import melonslise.spook.client.model.TwistedSheepModel;
import melonslise.spook.common.entity.TwistedSheep;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TwistedSheepFurLayer extends RenderLayer<TwistedSheep, TwistedSheepModel<TwistedSheep>>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");

	protected final TwistedSheepFurModel<TwistedSheep> model;

	public TwistedSheepFurLayer(RenderLayerParent<TwistedSheep, TwistedSheepModel<TwistedSheep>> parent, EntityModelSet modelSet)
	{
		super(parent);
		this.model = new TwistedSheepFurModel<>(modelSet.bakeLayer(ModelLayers.SHEEP_FUR));
	}

	@Override
	public void render(PoseStack mtx, MultiBufferSource bufferSource, int i1, TwistedSheep sheep, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		if (sheep.isInvisible())
		{
			if (Minecraft.getInstance().shouldEntityAppearGlowing(sheep))
			{
				this.getParentModel().copyPropertiesTo(this.model);
				this.model.prepareMobModel(sheep, f1, f2, f3);
				this.model.setupAnim(sheep, f1, f2, f4, f5, f6);
				VertexConsumer bld = bufferSource .getBuffer(RenderType.outline(TEXTURE));
				this.model.renderToBuffer(mtx, bld, i1, LivingEntityRenderer.getOverlayCoords(sheep, 0f), 0f, 0f, 0f, 1f);
			}

		}
		else
		{
			float r, g, b;
			if (sheep.hasCustomName() && "jeb_".equals(sheep.getName().getContents()))
			{
				int i = sheep.tickCount / 25 + sheep.getId();
				int j = DyeColor.values().length;
				int k = i % j;
				int l = (i + 1) % j;
				float val = ((float) (sheep.tickCount % 25) + f3) / 25.0F;
				float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
				float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
				r = afloat1[0] * (1.0F - val) + afloat2[0] * val;
				g = afloat1[1] * (1.0F - val) + afloat2[1] * val;
				b = afloat1[2] * (1.0F - val) + afloat2[2] * val;
			}
			else
			{
				float[] afloat = Sheep.getColorArray(DyeColor.WHITE); // FIXME color here
				r = afloat[0];
				g = afloat[1];
				b = afloat[2];
			}

			coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, TEXTURE, mtx, bufferSource, i1, sheep, f1, f2, f4, f5, f6, f3, r, g, b);
		}
	}
}