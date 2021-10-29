package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.SpookMod;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

@Mixin(ChestedHorseRenderer.class)
public class ChestedHorseRendererMixin
{
	private static final ResourceLocation TWISTED_DONKEY_LOCATION = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_donkey.png");
	private static final ResourceLocation TWISTED_MULE_LOCATION = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_horse/twisted_mule.png");

	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/horse/AbstractChestedHorse;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(AbstractChestedHorse horse, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			EntityType type = horse.getType();
			if(type == EntityType.DONKEY)
			{
				cir.setReturnValue(TWISTED_DONKEY_LOCATION);
			}
			else if(type == EntityType.MULE)
			{
				cir.setReturnValue(TWISTED_MULE_LOCATION);
			}
		}
	}
}