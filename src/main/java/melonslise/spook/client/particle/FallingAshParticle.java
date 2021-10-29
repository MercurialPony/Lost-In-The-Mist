package melonslise.spook.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FallingAshParticle extends TextureSheetParticle
{
	protected FallingAshParticle(ClientLevel level, double posX, double posY, double posZ, int blocksAboveSurface, SpriteSet sprites)
	{
		super(level, posX, posY, posZ, 0d, 0d, 0d);

		this.friction = 0.96f;
		this.gravity = 0.03f;
		this.speedUpWhenYMotionIsBlocked = true;
		this.xd *= 0.2d;
		this.yd = -0.02d;
		this.zd *= 0.2d;
		float color = 0.8f + level.random.nextFloat() * 0.2f;
		this.rCol = color;
		this.gCol = color;
		this.bCol = color;
		this.quadSize *= 0.75F * 1f;
		this.lifetime = blocksAboveSurface * 35;
		this.setSpriteFromAge(sprites);
		this.hasPhysics = false;
	}

	@Override
	public ParticleRenderType getRenderType()
	{
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType>
	{
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites)
		{
			this.sprites = sprites;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
		{
			// use x motion as blocks above surface because I'm too lazy to create a special deserializer,etc
			return new FallingAshParticle(level, posX, posY, posZ, (int) motionX, this.sprites);
		}
	}
}