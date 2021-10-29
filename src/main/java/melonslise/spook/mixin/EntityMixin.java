package melonslise.spook.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.world.entity.Entity;

@Mixin(Entity.class)
public class EntityMixin
{
	@Inject(at = @At("HEAD"), method = "canChangeDimensions()Z", cancellable = true)
	private void canChangeDimensions(CallbackInfoReturnable<Boolean> cir)
	{
		Entity player = (Entity) (Object) this;

		if(SpookyUtil.inFogworld(player))
		{
			cir.setReturnValue(false);
		}
	}
}