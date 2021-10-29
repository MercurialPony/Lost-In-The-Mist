package melonslise.spook.common.worldgen;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;

public class CopyingChunkGenerator extends ChunkGenerator
{
	public static final Codec<CopyingChunkGenerator> CODEC = RecordCodecBuilder.create((bld) -> bld
		.group(
			ResourceLocation.CODEC.fieldOf("dimension").forGetter(inst -> inst.dimensionId))
		.apply(bld, CopyingChunkGenerator::new));

	public final ResourceLocation dimensionId;

	public CopyingChunkGenerator(ResourceLocation dimId)
	{
		super(null, null);
		this.dimensionId = dimId;
	}

	@Override
	protected Codec<? extends ChunkGenerator> codec()
	{
		return CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long seed)
	{
		return this;
	}

	@Override
	public void buildSurfaceAndBedrock(WorldGenRegion region, ChunkAccess chunk)
	{
	}

	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, StructureFeatureManager structureManager, ChunkAccess chunk)
	{
		return null;
	}

	@Override
	public int getBaseHeight(int x, int z, Heightmap.Types heightmapType, LevelHeightAccessor heightAccessor)
	{
		return 0;
	}

	@Override
	public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor)
	{
		return null;
	}
}