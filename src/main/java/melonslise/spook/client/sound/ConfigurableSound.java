package melonslise.spook.client.sound;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.RegistryObject;

@OnlyIn(Dist.CLIENT)
public class ConfigurableSound extends AbstractSoundInstance
{
	public ConfigurableSound(SoundEvent sound, SoundSource category)
	{
		super(sound.getLocation(), category);
	}

	public ConfigurableSound(RegistryObject<SoundEvent> sound, SoundSource category)
	{
		super(sound.getId(), category);
	}

	public static ConfigurableSound forAmbient(RegistryObject<SoundEvent> sound, float volume, float pitch)
	{
		return new ConfigurableSound(sound, SoundSource.AMBIENT).volume(volume).pitch(pitch).attenuation(Attenuation.NONE).relative();
	}

	public ConfigurableSound volume(float volume)
	{
		this.volume = volume;
		return this;
	}

	public ConfigurableSound pitch(float pitch)
	{
		this.pitch = pitch;
		return this;
	}

	public ConfigurableSound loop()
	{
		this.looping = true;
		return this;
	}

	public ConfigurableSound relative()
	{
		this.relative = true;
		return this;
	}

	public ConfigurableSound attenuation(Attenuation attenuation)
	{
		this.attenuation = attenuation;
		return this;
	}
}