package melonslise.spook.common.capability;

import melonslise.spook.SpookMod;
import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.init.SpookNetwork;
import melonslise.spook.common.network.toclient.UpdateSanityPacket;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.nbt.FloatTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public class Sanity implements ISanity
{
	public static final ResourceLocation ID = new ResourceLocation(SpookMod.ID, "sanity");

	public final Player player;

	public float sanity = 100f;

	public Sanity(Player player)
	{
		this.player = player;
	}

	@Override
	public float get()
	{
		return this.sanity;
	}

	@Override
	public void set(float sanity)
	{
		float oldSanity = this.sanity;
		this.sanity = Mth.clamp(sanity, 0f, 100f);

		if(oldSanity != this.sanity && this.player instanceof ServerPlayer player)
		{
			if(this.sanity > 90f && this.player.level.dimension().toString().contains("spook:fogworld"))
			{
				player.teleportTo(player.server.getLevel(Level.OVERWORLD), player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
			}
			else
			{
				sync(player, this.sanity);
			}
		}
	}

	@Override
	public float getChance()
	{
		// basically 80 sanity = 0%, 0 sanity = 100%
		return Mth.clamp(SpookyUtil.remap(this.sanity, 0f, 80f, 1f, 0f), 0f, 1f);
	}

	@Override
	public void deserializeNBT(FloatTag nbt)
	{
		this.sanity = nbt.getAsFloat();
	}

	@Override
	public FloatTag serializeNBT()
	{
		return FloatTag.valueOf(this.sanity);
	}

	public static void sync(ServerPlayer player)
	{
		player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity ->
		{
			sync(player, sanity.get());
		});
	}

	public static void sync(ServerPlayer player, float sanity)
	{
		SpookNetwork.MAIN.send(PacketDistributor.PLAYER.with(() -> player), new UpdateSanityPacket(sanity));
	}
}