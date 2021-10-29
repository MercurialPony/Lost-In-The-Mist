package melonslise.spook.mixin.client.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.SpookMod;
import melonslise.spook.client.util.SpookyClientUtil;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;

@Mixin(VillagerRenderer.class)
public class VillagerRendererMixin
{
	private static final ResourceLocation TWISTED_VILLAGER_BASE_SKIN = new ResourceLocation(SpookMod.ID, "textures/entity/twisted_villager/twisted_villager.png");

	@Inject(at = @At("HEAD"), method = "getTextureLocation(Lnet/minecraft/world/entity/npc/Villager;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
	private void getTextureLocation(Villager villager, CallbackInfoReturnable<ResourceLocation> cir)
	{
		if(SpookyClientUtil.isPlayerInsane())
		{
			cir.setReturnValue(TWISTED_VILLAGER_BASE_SKIN);
		}
	}
}