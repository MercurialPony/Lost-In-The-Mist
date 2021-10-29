package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.client.renderer.entity.TwistedPigRenderer;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;

@Mixin(PigRenderer.class)
public class PigRendererMixin
{
	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Pig;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(Pig pig, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			cir.setReturnValue(TwistedPigRenderer.TEXTURE);
		}
	}
}