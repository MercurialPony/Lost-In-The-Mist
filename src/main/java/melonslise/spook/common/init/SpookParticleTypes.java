package melonslise.spook.common.init;

import java.util.function.Supplier;

import melonslise.spook.SpookMod;
import melonslise.spook.client.particle.DecayParticleType;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SpookParticleTypes
{
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SpookMod.ID);

	public static final RegistryObject<SimpleParticleType> FALLING_ASH = add("falling_ash");

	public static final RegistryObject<ParticleType<BlockParticleOption>> DECAY = add("decay", DecayParticleType::new);

	private SpookParticleTypes() {}

	public static RegistryObject<SimpleParticleType> add(String name)
	{
		return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
	}

	public static <T extends ParticleOptions> RegistryObject<ParticleType<T>> add(String name, Supplier<ParticleType<T>> type)
	{
		return PARTICLE_TYPES.register(name, type);
	}
}