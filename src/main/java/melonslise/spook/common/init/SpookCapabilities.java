package melonslise.spook.common.init;

import melonslise.spook.common.capability.IPlayerBackup;
import melonslise.spook.common.capability.ISanity;
import melonslise.spook.common.capability.PlayerBackup;
import melonslise.spook.common.capability.Sanity;
import melonslise.spook.common.capability.SerializableCapabilityProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public final class SpookCapabilities
{
	public static final Capability<IPlayerBackup> PLAYER_BACKUP = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<ISanity> SANITY = CapabilityManager.get(new CapabilityToken<>() {});

	private SpookCapabilities() {}

	public static void register(RegisterCapabilitiesEvent e)
	{
		e.register(IPlayerBackup.class);
		e.register(ISanity.class);
	}

	public static void attachToEntity(AttachCapabilitiesEvent<Entity> e)
	{
		if(e.getObject() instanceof Player player)
		{
			e.addCapability(PlayerBackup.ID, new SerializableCapabilityProvider(PLAYER_BACKUP, new PlayerBackup(player)));
			e.addCapability(Sanity.ID, new SerializableCapabilityProvider(SANITY, new Sanity(player)));
		}
	}
}