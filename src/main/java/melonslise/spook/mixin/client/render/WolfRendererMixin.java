package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.client.renderer.entity.TwistedWolfRenderer;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

@Mixin(WolfRenderer.class)
public class WolfRendererMixin
{


	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Wolf;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(Wolf wolf, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			if (wolf.isTame())
			{
				cir.setReturnValue(TwistedWolfRenderer.TAME_TEXTURE);
			}
			else
			{
				cir.setReturnValue(wolf.isAngry() ? TwistedWolfRenderer.ANGRY_TEXTURE : TwistedWolfRenderer.TEXTURE);
			}
		}
	}
}