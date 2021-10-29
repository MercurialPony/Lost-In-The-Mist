package melonslise.spook.common.init;

import melonslise.spook.SpookMod;
import melonslise.spook.common.worldgen.CopyingChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public final class SpookChunkGenerators
{
	private SpookChunkGenerators() {}

	public static void register()
	{
		// FIXME is this the right way?
		Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(SpookMod.ID, "copy"), CopyingChunkGenerator.CODEC);
	}
}