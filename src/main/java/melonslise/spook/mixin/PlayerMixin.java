package melonslise.spook.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import melonslise.spook.common.event.FogworldHandler;
import melonslise.spook.common.event.SpookForgeEvents;
import melonslise.spook.common.init.SpookDimensionKeys;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public class PlayerMixin
{
	// this is required because all the events and shit is called per tick so we'd be running our sanity check every tick which is not what we want. We want the check to fire once per sleep
	@Inject(
		at = @At( // this is basically between the ++sleepCounter and the following if
			value = "FIELD",
			target = "Lnet/minecraft/world/entity/player/Player;sleepCounter:I",
			opcode = Opcodes.GETFIELD,
			ordinal = 1), // second access (in the if statement)
		method = "tick()V")
	private void tick(CallbackInfo ci)
	{
		if((Object) this instanceof ServerPlayer player)
		{
			if(player.getSleepTimer() == 100)
			{
				if(FogworldHandler.tryUpdate(player))
				{
					// prevent ticking entity crash
					SpookForgeEvents.END_OF_TICK_ACTIONS.add(() ->
						player.teleportTo(player.server.getLevel(SpookDimensionKeys.FOGWORLD), player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot()));
				}
			}
		}
	}
}