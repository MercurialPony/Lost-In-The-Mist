package melonslise.spook.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.init.SpookDimensionKeys;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

@Mixin(ServerLevel.class)
public class ServerLevelMixin
{
	@Redirect(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/dimension/DimensionType;hasSkylight:Z"),
		method = "tick(Ljava/util/function/BooleanSupplier;)V")
	private boolean redirectTickHasSkyLight(DimensionType type) // FIXME
	{
		ServerLevel level = (ServerLevel) (Object) this;

		// disable weather
		return level.dimension() == SpookDimensionKeys.FOGWORLD ? false : type.hasSkyLight();
	}

	@Inject(at = @At("HEAD"), method = "wakeUpAllPlayers()V")
	private void wakeUpAllPlayers(CallbackInfo ci)
	{
		ServerLevel level = (ServerLevel) (Object) this;

		for(ServerPlayer player : level.players())
		{
			if(player.isSleeping())
			{
				wakeUp(player);
			}
		}
	}

	// @Inject(at = @At("HEAD"), method = "lambda$wakeUpAllPlayers$6(Lnet/minecraft/server/level/ServerPlayer;)V", cancellable = true)
	private static void wakeUp(ServerPlayer player) // , CallbackInfo ci
	{
		if(player.level.dimension() == Level.OVERWORLD)
		{
			player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() + 5f)); // FIXME don't add this if player gets transported to fogworld
		}
	}
}