package melonslise.spook.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TwistedMushroomCowMushroomLayer<T extends LivingEntity> extends RenderLayer<T, CowModel<T>>
{
	public TwistedMushroomCowMushroomLayer(RenderLayerParent<T, CowModel<T>> parent)
	{
		super(parent);
	}

	public void render(PoseStack mtx, MultiBufferSource bufferSource, int i1, T entity, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		Minecraft mc = Minecraft.getInstance();
		boolean flag = mc.shouldEntityAppearGlowing(entity) && entity.isInvisible();

		if (!entity.isInvisible() || flag)
		{
			BlockRenderDispatcher blockRenderer = mc.getBlockRenderer();
			BlockState state = Blocks.RED_MUSHROOM.defaultBlockState(); // FIXME
			int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
			BakedModel model = blockRenderer.getBlockModel(state);
			mtx.pushPose();
			mtx.translate((double) 0.2F, (double) -0.35F, 0.5D);
			mtx.mulPose(Vector3f.YP.rotationDegrees(-48.0F));
			mtx.scale(-1.0F, -1.0F, 1.0F);
			mtx.translate(-0.5D, -0.5D, -0.5D);
			this.renderMushroomBlock(mtx, bufferSource, i1, flag, blockRenderer, state, overlay, model);
			mtx.popPose();
			mtx.pushPose();
			mtx.translate((double) 0.2F, (double) -0.35F, 0.5D);
			mtx.mulPose(Vector3f.YP.rotationDegrees(42.0F));
			mtx.translate((double) 0.1F, 0.0D, (double) -0.6F);
			mtx.mulPose(Vector3f.YP.rotationDegrees(-48.0F));
			mtx.scale(-1.0F, -1.0F, 1.0F);
			mtx.translate(-0.5D, -0.5D, -0.5D);
			this.renderMushroomBlock(mtx, bufferSource, i1, flag, blockRenderer, state, overlay, model);
			mtx.popPose();
			mtx.pushPose();
			this.getParentModel().getHead().translateAndRotate(mtx);
			mtx.translate(0.0D, (double) -0.7F, (double) -0.2F);
			mtx.mulPose(Vector3f.YP.rotationDegrees(-78.0F));
			mtx.scale(-1.0F, -1.0F, 1.0F);
			mtx.translate(-0.5D, -0.5D, -0.5D);
			this.renderMushroomBlock(mtx, bufferSource, i1, flag, blockRenderer, state, overlay, model);
			mtx.popPose();
		}
	}

	public void renderMushroomBlock(PoseStack mtx, MultiBufferSource bufferSource, int i1, boolean b1, BlockRenderDispatcher blockRenderer, BlockState state, int i2, BakedModel model)
	{
		if (b1)
		{
			blockRenderer.getModelRenderer().renderModel(mtx.last(), bufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), state, model, 0.0F, 0.0F, 0.0F, i1, i2);
		}
		else
		{
			blockRenderer.renderSingleBlock(state, mtx, bufferSource, i1, i2);
		}
	}
}