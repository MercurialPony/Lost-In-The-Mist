package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.client.renderer.entity.TwistedSheepRenderer;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;

@Mixin(SheepRenderer.class)
public class SheepRendererMixin
{
	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Sheep;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(Sheep sheep, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			cir.setReturnValue(TwistedSheepRenderer.TEXTURE);
		}
	}
}