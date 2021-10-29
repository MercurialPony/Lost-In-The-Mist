package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.SpookMod;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;

@Mixin(CatRenderer.class)
public class CatRendererMixin
{
	private static final ResourceLocation[] TWISTED_TEXTURES = new ResourceLocation[] {
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_tabby.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_black.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_red.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_siamese.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_british_shorthair.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_calico.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_persian.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_ragdoll.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_white.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_jellie.png"),
		new ResourceLocation(SpookMod.ID, "textures/entity/twisted_cat/twisted_all_black.png") };

	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Cat;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(Cat cat, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			cir.setReturnValue(TWISTED_TEXTURES[cat.getCatType()]);
		}
	}
}