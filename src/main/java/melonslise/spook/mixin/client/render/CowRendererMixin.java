package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.client.renderer.entity.TwistedCowRenderer;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;

@Mixin(CowRenderer.class)
public class CowRendererMixin
{
	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Cow;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(Cow cow, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			cir.setReturnValue(TwistedCowRenderer.TEXTURE);
		}
	}
}