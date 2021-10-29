package melonslise.spook.client.misc;

import java.util.Random;

import io.netty.util.internal.ThreadLocalRandom;
import melonslise.spook.client.sound.ConfigurableSound;
import melonslise.spook.common.entity.ITwistedMob;
import melonslise.spook.common.init.SpookSoundEvents;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VhsEffectHandler
{
	public static final VhsEffectHandler INSTANCE = new VhsEffectHandler(100, ThreadLocalRandom.current());

	public static final ConfigurableSound STATIC_LOOP = ConfigurableSound.forAmbient(SpookSoundEvents.LOUD_STATIC_AMBIENCE_LOOP, 0.6f, 1f).loop();

	public final int effectDuration;
	public final Random rng;

	protected int timer;
	protected int effectTimer;

	protected boolean wasNearMob;

	private VhsEffectHandler(int soundDuration, Random rng)
	{
		this.effectDuration = soundDuration;
		this.rng = rng;
		this.setFirstTimer();
	}

	public boolean playing()
	{
		Minecraft mc = Minecraft.getInstance();
		return mc.player != null && SpookyUtil.inFogworld(mc.player) || this.effectTimer > 0;
	}

	public boolean wasNearMob()
	{
		return this.wasNearMob;
	}

	protected void play()
	{
		this.effectTimer = this.effectDuration;
	}

	protected void setFirstTimer()
	{
		this.timer = 100 + this.rng.nextInt(600 + 1);
	}

	protected void resetTimer()
	{
		this.timer = 900 + this.rng.nextInt(3100 + 1);
	}

	public void tick()
	{
		Minecraft mc = Minecraft.getInstance();

		if(this.effectTimer > 0)
		{
			--this.effectTimer;

			if(this.effectTimer == 0 && !this.wasNearMob)
			{
				mc.getSoundManager().stop(STATIC_LOOP);
				this.resetTimer();
			}
		}

		if(mc.player == null || mc.isPaused())
		{
			if(this.timer > 0)
			{
				--this.timer;
	
				if(this.timer == 0)
				{
					this.play();
					mc.getSoundManager().play(STATIC_LOOP);
				}
			}
		}
		else
		{
			boolean isNearMob = !mc.level.getEntities(mc.player, mc.player.getBoundingBox().inflate(6d, 3d, 6d), entity -> entity instanceof ITwistedMob).isEmpty();

			if(isNearMob && !this.wasNearMob)
			{
				mc.getSoundManager().play(STATIC_LOOP);
			}
			else if(!isNearMob && this.wasNearMob)
			{
				mc.getSoundManager().stop(STATIC_LOOP);
			}

			this.wasNearMob = isNearMob;
		}
	}
}