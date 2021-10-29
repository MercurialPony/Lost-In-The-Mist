package melonslise.spook.client.event;

import melonslise.spook.SpookMod;
import melonslise.spook.client.init.SpookShaders;
import melonslise.spook.client.misc.AmbienceHandler;
import melonslise.spook.client.misc.SanityEffectHandler;
import melonslise.spook.client.misc.VhsEffectHandler;
import melonslise.spook.client.renderer.shader.ExtendedPostChain;
import melonslise.spook.client.util.SpookyClientUtil;
import melonslise.spook.common.init.SpookParticleTypes;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpookMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class SpookClientForgeEvents
{
	private static final BlockPos.MutableBlockPos MUTABLE_POS = new BlockPos.MutableBlockPos();

	public static float shaderTime;

	private SpookClientForgeEvents() {}

	@SubscribeEvent
	public static void renderTick(TickEvent.RenderTickEvent e)
	{
		if(e.phase != TickEvent.Phase.END)
			return;

		if(VhsEffectHandler.INSTANCE.playing())
		{
			shaderTime += Minecraft.getInstance().getDeltaFrameTime(); // FIXME can overflow
			
			ExtendedPostChain shaderChain = SpookShaders.getVhs();
			EffectInstance shader = shaderChain.getMainShader();

			if(shader != null)
			{
				shader.safeGetUniform("GameTime").set(shaderTime);

				shader.safeGetUniform("NoiseIntensity").set(VhsEffectHandler.INSTANCE.wasNearMob() ? 0.0148f : 0.0068f);

				shaderChain.process(e.renderTickTime);
				Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
			}
		}
	}

	@SubscribeEvent
	public static void clientTick(TickEvent.ClientTickEvent e)
	{
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;

		if(player != null && !mc.isPaused() && SpookyUtil.inFogworld(player))
		{
			spawnParticles(player);
		}

		if(e.phase == TickEvent.Phase.START)
		{
			AmbienceHandler.INSTANCE.tick();
			VhsEffectHandler.INSTANCE.tick();
			SanityEffectHandler.INSTANCE.tick();
		}
	}

	private static void spawnParticles(Player player)
	{
		Level level = player.level;

		int radius = 64;
		int density = 32;

		float time = level.getTimeOfDay(1f);

		boolean isDecayTime = time > 0.255f && time <= 0.285f;

		for(int i = 0; i < density; ++i)
		{
			int x = player.getBlockX() + level.random.nextInt(radius * 2 + 1) - radius;
			int z = player.getBlockZ() + level.random.nextInt(radius * 2 + 1) - radius;
			int height = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

			int blocksAbove = level.random.nextInt(4) + 3;
			int y = Math.max(player.getBlockY(), height) + blocksAbove;
			// particle lifetime is dependent on the distance to the ground. If the player is above the terrain then we want to add the the y difference to the amount of blocks above
			level.addParticle(SpookParticleTypes.FALLING_ASH.get(), x + level.random.nextDouble(), y + level.random.nextDouble(), z + level.random.nextDouble(), Math.max(player.getBlockY() - height, 0) + blocksAbove, 0d, 0d);
		}

		if(!isDecayTime)
		{
			return;
		}

		radius = 16;
		density = 24;

		for(int i = 0; i < density; ++i)
		{
			int x = player.getBlockX() + level.random.nextInt(radius * 2 + 1) - radius;
			int z = player.getBlockZ() + level.random.nextInt(radius * 2 + 1) - radius;
			int height = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

			level.addParticle(new BlockParticleOption(SpookParticleTypes.DECAY.get(), level.getBlockState(MUTABLE_POS.set(x, height - 1, z))), x + level.random.nextDouble(), height, z + level.random.nextDouble(), 0d, 0d, 0d);
		}
	}
}