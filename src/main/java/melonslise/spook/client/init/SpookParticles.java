package melonslise.spook.client.init;

import melonslise.spook.client.particle.DecayParticle;
import melonslise.spook.client.particle.FallingAshParticle;
import melonslise.spook.common.init.SpookParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class SpookParticles
{
	private SpookParticles() {}

	public static void register()
	{
		Minecraft mc = Minecraft.getInstance();

		mc.particleEngine.register(SpookParticleTypes.FALLING_ASH.get(), FallingAshParticle.Provider::new);
		mc.particleEngine.register(SpookParticleTypes.DECAY.get(), new DecayParticle.Provider());
	}
}