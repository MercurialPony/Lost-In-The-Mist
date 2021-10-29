package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.SpookMod;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;

@Mixin(HorseRenderer.class)
public class HorseRendererMixin
{
	private static final ResourceLocation[] TWISTED_TEXTURES = new ResourceLocation[] {
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_horse_white.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_horse_creamy.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_horse_chestnut.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_horse_brown.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_horse_black.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_horse_gray.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_horse_darkbrown.png") };

	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/horse/Horse;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(Horse horse, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			cir.setReturnValue(TWISTED_TEXTURES[horse.getVariant().ordinal()]);
		}
	}
}