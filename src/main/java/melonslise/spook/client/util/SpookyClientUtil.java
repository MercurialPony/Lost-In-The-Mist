package melonslise.spook.client.util;

import melonslise.spook.common.init.SpookCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class SpookyClientUtil
{
	private SpookyClientUtil() {}

	public static final boolean isPlayerInsane()
	{
		Player player = Minecraft.getInstance().player;

		if(player == null)
		{
			return false;
		}

		float sanity = player.getCapability(SpookCapabilities.SANITY).map(s -> s.get()).orElse(100f);
		return sanity <= 55f;
	}
}