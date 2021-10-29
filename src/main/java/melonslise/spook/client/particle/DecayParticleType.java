package melonslise.spook.client.particle;

import com.mojang.serialization.Codec;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;

public class DecayParticleType extends ParticleType<BlockParticleOption>
{
	public DecayParticleType()
	{
		super(false, BlockParticleOption.DESERIALIZER);
	}

	@Override
	public Codec<BlockParticleOption> codec()
	{
		return BlockParticleOption.codec(this);
	}
}