package melonslise.spook;

import melonslise.spook.common.init.SpookEntityTypes;
import melonslise.spook.common.init.SpookItems;
import melonslise.spook.common.init.SpookLootModifiers;
import melonslise.spook.common.init.SpookNetwork;
import melonslise.spook.common.init.SpookParticleTypes;
import melonslise.spook.common.init.SpookSoundEvents;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SpookMod.ID)
public class SpookMod
{
	public static final String ID = "spook";

	public SpookMod()
	{
		SpookItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		SpookEntityTypes.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		SpookSoundEvents.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
		SpookParticleTypes.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		SpookLootModifiers.SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());

		SpookNetwork.register();
	}
}