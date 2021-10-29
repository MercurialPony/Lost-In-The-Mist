package melonslise.spook.common.init;

import melonslise.spook.SpookMod;
import melonslise.spook.common.network.toclient.UpdateSanityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public final class SpookNetwork
{
	public static final SimpleChannel MAIN = NetworkRegistry.newSimpleChannel(new ResourceLocation(SpookMod.ID, "main"), () -> SpookMod.ID, a -> true, a -> true);

	private SpookNetwork() {}

	public static void register()
	{
		MAIN.registerMessage(0, UpdateSanityPacket.class, UpdateSanityPacket::encode, UpdateSanityPacket::decode, UpdateSanityPacket::handle);
	}
}