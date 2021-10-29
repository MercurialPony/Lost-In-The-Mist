package melonslise.spook.client.misc;

import melonslise.spook.client.sound.ConfigurableSound;
import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.init.SpookSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SanityEffectHandler
{
	public static final SanityEffectHandler INSTANCE = new SanityEffectHandler();

	public static final ConfigurableSound
		BREATH_SOUND = ConfigurableSound.forAmbient(SpookSoundEvents.SCARED_BREATH, 1f, 1f),
		BREATH_LOOP = ConfigurableSound.forAmbient(SpookSoundEvents.SCARED_BREATH_LOOP, 0.5f, 1f).loop(),
		EXHALE_SOUND = ConfigurableSound.forAmbient(SpookSoundEvents.EXHALE, 0.5f, 1f);

	protected int effectDuration;
	protected int effectTimer;

	protected float maxBlur;

	protected float lastSanity;

	private SanityEffectHandler() {}

	// A parabola  y = -4 * d / c^2 * (x - c / 2) ^ 2 + d, which intersects x at 0 and c (i.e. moves from 0 to c), and reaches its peak at d (i.e. max value)
	// c in our case is effectDuration
	// d is maxBlur
	public float getBlurStrength(float frameTime)
	{
		final float x = this.effectDuration - this.effectTimer + frameTime;
		final float c = this.effectDuration;
		final float d = this.maxBlur;

		float f = -4f * d * (x - c / 2f) * (x - c / 2f) / (c * c) + d;
		return f;
	}

	protected void play(int duration, float maxBlur)
	{
		this.effectTimer = this.effectDuration = duration;
		this.maxBlur = maxBlur;
	}

	public boolean playing()
	{
		return this.effectTimer > 0;
	}

	public void tick()
	{
		Minecraft mc = Minecraft.getInstance();

		if(mc.isPaused())
		{
			return;
		}

		if(this.effectTimer > 0)
		{
			--this.effectTimer;
		}

		if(mc.player == null)
		{
			return;
		}

		float sanity = mc.player.getCapability(SpookCapabilities.SANITY).map(s -> s.get()).orElse(-1f);

		if(sanity < 0f)
		{
			return;
		}

		if(sanity <= 30f)
		{
			if(!mc.getSoundManager().isActive(BREATH_LOOP))
			{
				mc.getSoundManager().play(BREATH_LOOP);
			}

			if(!this.playing())
			{
				this.play(80, 0.02f);
			}
		}
		else
		{
			mc.getSoundManager().stop(BREATH_LOOP);

			if(this.lastSanity < sanity)
			{
				if(sanity > 80f)
				{
					if(this.lastSanity <= 80f)
					{
						mc.getSoundManager().play(EXHALE_SOUND);
					}
				}
				else if(sanity > 55f)
				{
					if(this.lastSanity <= 55f)
					{
						mc.getSoundManager().play(EXHALE_SOUND);
					}
				}
				else if(sanity > 30f)
				{
					if(this.lastSanity <= 30f)
					{
						mc.getSoundManager().play(EXHALE_SOUND);
					}
				}
			}

			if(sanity <= 55f)
			{
				if(this.lastSanity > 55f)
				{
					this.play(80, 0.1f);
					mc.getSoundManager().play(BREATH_SOUND);
				}
			}
			else if(sanity <= 80f)
			{
				if(this.lastSanity > 80f)
				{
					this.play(60, 0.05f);
					mc.getSoundManager().play(BREATH_SOUND);
				}
			}
		}

		this.lastSanity = sanity;
	}
}