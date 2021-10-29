package melonslise.spook.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import melonslise.spook.client.init.SpookShaders;
import melonslise.spook.client.misc.SanityEffectHandler;
import melonslise.spook.client.renderer.shader.ExtendedPostChain;
import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.util.Mth;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
	private static final Matrix4f PROJECTION_INVERSE = new Matrix4f();
	private static final Matrix4f VIEW_INVERSE = new Matrix4f();

	@Shadow
	private PostChain transparencyChain;

	@Unique
	private RenderTarget depthCopy;

	@Inject(
		at = @At(
			value = "invoke",
			target = "Lnet/minecraft/client/renderer/PostChain;process(F)V",
			ordinal = 1),
		method = "renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lcom/mojang/math/Matrix4f;)V")
	private void renderLevelPreTransparency(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		// This stuff is required because apparently the transparency (fabulous) shader decides it's a wonderful idea to nuke the depth buffer!!
		// FIXME because of this stuff like particles are not recorded in the depth buffer so the fog eats them up
		Minecraft mc = Minecraft.getInstance();
		RenderTarget main = mc.getMainRenderTarget();

		if(this.depthCopy == null)
		{
			this.depthCopy = new TextureTarget(mc.getWindow().getWidth(), mc.getWindow().getHeight(), true, Minecraft.ON_OSX);
		}
		else if(this.depthCopy.width != main.width || this.depthCopy.height != main.height)
		{
			this.depthCopy.resize(main.width, main.height, false);
		}

		this.depthCopy.setClearColor(0f, 0f, 0f, 0f);
		this.depthCopy.copyDepthFrom(main);
	}

	@Inject(
		at = @At(
			value = "TAIL"),
		method = "renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lcom/mojang/math/Matrix4f;)V")
	private void renderLevelPostTransparency(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		this.applyFog(mtx, frameTime);
		this.applyDistortion(frameTime); // FIXME move to GameRenderer#renderLevel TAIL
		this.applyBlur(frameTime);
	}

	@Unique
	private void applyFog(PoseStack mtx, float frameTime)
	{
		Minecraft mc = Minecraft.getInstance();

		if(!SpookyUtil.inFogworld(mc.player))
		{
			return;
		}

		ExtendedPostChain shaderChain = SpookShaders.getFog();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			PROJECTION_INVERSE.load(RenderSystem.getProjectionMatrix());
			PROJECTION_INVERSE.invert();

			VIEW_INVERSE.load(mtx.last().pose());
			VIEW_INVERSE.invert();

			shader.safeGetUniform("ProjInverseMat").set(PROJECTION_INVERSE);
			shader.safeGetUniform("ViewInverseMat").set(VIEW_INVERSE);

			shader.safeGetUniform("Darkness").set(SpookyUtil.getDarkness(mc.player.level));

			if(this.transparencyChain != null)
			{
				mc.getMainRenderTarget().copyDepthFrom(depthCopy);
			}

			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}

	@Unique
	private void applyBlur(float frameTime)
	{
		Minecraft mc = Minecraft.getInstance();

		if(!SanityEffectHandler.INSTANCE.playing())
		{
			return;
		}

		ExtendedPostChain shaderChain = SpookShaders.getBlur();
		EffectInstance shader = shaderChain.getMainShader();

		if (shader != null)
		{
			shader.safeGetUniform("BlurStrength").set(SanityEffectHandler.INSTANCE.getBlurStrength(frameTime));

			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}

	@Unique
	private void applyDistortion(float frameTime)
	{
		Minecraft mc = Minecraft.getInstance();

		float sanity = Mth.clamp(mc.player.getCapability(SpookCapabilities.SANITY).map(s -> s.get()).orElse(80f), 30f, 80f);

		if(sanity >= 80f)
		{
			return;
		}

		ExtendedPostChain shaderChain = SpookShaders.getDistortAndDesaturate();
		EffectInstance shader = shaderChain.getMainShader();

		if (shader != null)
		{
			shader.safeGetUniform("DistortStrength").set(SpookyUtil.remap(sanity, 30f, 80f, -0.5f, 0f));
			shader.safeGetUniform("Saturation").set(SpookyUtil.remap(sanity, 30f, 80f, 0f, 1f));

			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
}