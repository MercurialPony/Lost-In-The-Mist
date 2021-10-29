package melonslise.spook.common.network.toclient;

import java.util.function.Supplier;

import melonslise.spook.common.init.SpookCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class UpdateSanityPacket
{
	private final float sanity;

	public UpdateSanityPacket(float sanity)
	{
		this.sanity = sanity;
	}

	public static UpdateSanityPacket decode(FriendlyByteBuf buf)
	{
		return new UpdateSanityPacket(buf.readFloat());
	}

	public static void encode(UpdateSanityPacket pkt, FriendlyByteBuf buf)
	{
		buf.writeFloat(pkt.sanity);
	}

	public static void handle(UpdateSanityPacket pkt, Supplier<NetworkEvent.Context> ctx)
	{
		// Use runnable, lambda causes issues with class loading
		ctx.get().enqueueWork(new Runnable()
		{
			@Override
			public void run()
			{
				Minecraft.getInstance().player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(pkt.sanity));

				/*
				// prevents effect from recurring after every re-attachment of the cap (e.g. changing dims)
				if(oldSanity == 100)
				{
					return;
				}

				SpookClientForgeEvents.SANITY_HANDLER.play(oldSanity, pkt.sanity);
				*/
			}
		});

		ctx.get().setPacketHandled(true);
	}
}