package melonslise.spook.client.misc;

import java.util.Random;

import io.netty.util.internal.ThreadLocalRandom;
import melonslise.spook.client.sound.ConfigurableSound;
import melonslise.spook.common.init.SpookSoundEvents;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AmbienceHandler
{
	public static final AmbienceHandler INSTANCE = new AmbienceHandler(ThreadLocalRandom.current());

	public static final ConfigurableSound
		STATIC_LOOP = ConfigurableSound.forAmbient(SpookSoundEvents.STATIC_AMBIENCE_LOOP, 0.15f, 1f).loop(),
		WOBBLY_SOUND = ConfigurableSound.forAmbient(SpookSoundEvents.WOBBLY_AMBIENCE, 0.2f, 0.8f),
		GHOSTLY_SOUND = ConfigurableSound.forAmbient(SpookSoundEvents.GHOSTLY_AMBIENCE, 0.4f, 0.8f),
		SIREN_SOUND = ConfigurableSound.forAmbient(SpookSoundEvents.SIREN, 0.8f, 1f),
		LONG_AMBIENCE_SOUND = ConfigurableSound.forAmbient(SpookSoundEvents.LONG_AMBIENCE, 0.8f, 1f);

	public final Random rng;

	protected int ambienceTimer = 100;
	protected int sirenTimer;

	protected boolean wasInFogworld;
	protected boolean isFirst = true;

	private AmbienceHandler(Random rng)
	{
		this.rng = rng;
	}

	protected ConfigurableSound randomAmbientSound()
	{
		if(this.isFirst)
		{
			this.isFirst = false;
			return WOBBLY_SOUND;
		}

		return this.rng.nextBoolean() ? WOBBLY_SOUND : GHOSTLY_SOUND;
	}

	public void resetAmbienceTimer()
	{
		this.ambienceTimer = 1000 + this.rng.nextInt(1000 + 1);
	}

	public void tick()
	{
		Minecraft mc = Minecraft.getInstance();

		if(mc.player == null || mc.isPaused())
		{
			return;
		}

		boolean inFogworld = SpookyUtil.inFogworld(mc.player);

		float time = mc.level.getTimeOfDay(1f);

		boolean isSirenTime = time > 0.25f && time <= 0.28f;
		boolean isAmbienceTime = time > 0.286f && time <= 0.45f;

		if(inFogworld)
		{
			if(!this.wasInFogworld || !mc.getSoundManager().isActive(STATIC_LOOP))
			{
				mc.getSoundManager().play(STATIC_LOOP);
			}

			if(this.ambienceTimer > 0)
			{
				--this.ambienceTimer;

				if(this.ambienceTimer == 0)
				{
					boolean isNight = time > 0.22f && time <= 0.74f;

					if(!isNight)
					{
						mc.getSoundManager().play(this.randomAmbientSound());
					}

					this.resetAmbienceTimer();
				}
			}

			if(isSirenTime && !mc.getSoundManager().isActive(SIREN_SOUND))
			{
				mc.getSoundManager().play(SIREN_SOUND);
			}

			if(isAmbienceTime && !mc.getSoundManager().isActive(LONG_AMBIENCE_SOUND))
			{
				mc.getSoundManager().play(LONG_AMBIENCE_SOUND);
			}
		}
		else if(this.wasInFogworld)
		{
			mc.getSoundManager().stop(STATIC_LOOP);
		}

		this.wasInFogworld = inFogworld;
	}
}