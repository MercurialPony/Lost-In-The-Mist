package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.client.renderer.entity.TwistedMushroomCowRenderer;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.MushroomCowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.MushroomCow;

@Mixin(MushroomCowRenderer.class)
public class MushroomCowRendererMixin
{
	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/MushroomCow;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(MushroomCow cow, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			cir.setReturnValue(TwistedMushroomCowRenderer.TEXTURES[cow.getMushroomType().ordinal()]);
		}
	}
}