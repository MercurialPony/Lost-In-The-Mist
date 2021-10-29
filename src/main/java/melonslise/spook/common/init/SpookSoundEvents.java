package melonslise.spook.common.init;

import melonslise.spook.SpookMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SpookSoundEvents
{
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SpookMod.ID);

	public static final RegistryObject<SoundEvent>
		CREEPY_AMBIENCE = add("creepy_ambience"),
		WOBBLY_AMBIENCE = add("wobbly_ambience"),
		GHOSTLY_AMBIENCE = add("ghostly_ambience"),
		STATIC_AMBIENCE_LOOP = add("static_ambience_loop"),
		LOUD_STATIC_AMBIENCE_LOOP = add("loud_static_ambience_loop"),
		SCARED_BREATH = add("scared_breath"),
		SCARED_BREATH_LOOP = add("scared_breath_loop"),
		EXHALE = add("exhale"),
		PAGE_FLIP = add("page_flip"),
		SIREN = add("siren"),
		LONG_AMBIENCE = add("long_ambience");

	private SpookSoundEvents() {}

	public static RegistryObject<SoundEvent> add(String name)
	{
		return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(SpookMod.ID, name)));
	}
}