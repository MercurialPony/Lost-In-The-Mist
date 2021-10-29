package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.SpookMod;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;

@Mixin(ChickenRenderer.class)
public class ChickenRendererMixin
{
	private static final ResourceLocation TWISTED_CHICKEN_LOCATION = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_chicken.png");

	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Chicken;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(Chicken chicken, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			cir.setReturnValue(TWISTED_CHICKEN_LOCATION);
		}
	}
}