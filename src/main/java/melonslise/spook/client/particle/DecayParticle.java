package melonslise.spook.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DecayParticle extends TerrainParticle
{
	public DecayParticle(ClientLevel level, double x, double y, double z, double speedX, double speedY, double speedZ, BlockState state)
	{
		super(level, x, y, z, speedX, speedY, speedZ, state);
		this.gravity = -0.1f;
		this.xd = 0d;
		this.yd = 0d;
		this.zd = 0d;
		this.lifetime = 60;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<BlockParticleOption>
	{
		public Particle createParticle(BlockParticleOption options, ClientLevel level, double x, double y, double z, double speedX, double speedY, double speedZ)
		{
			BlockState state = options.getState();
			return !state.isAir() && !state.is(Blocks.MOVING_PISTON) ? new DecayParticle(level, x, y, z, speedX, speedY, speedZ, state) : null;
		}
	}
}