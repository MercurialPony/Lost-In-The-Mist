package melonslise.spook.mixin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import melonslise.spook.common.worldgen.CopyingChunkGenerator;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.ServerLevelData;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin
{
	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/resources/ResourceKey;create(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/resources/ResourceKey;"),
		method = "createLevels(Lnet/minecraft/server/level/progress/ChunkProgressListener;)V",
		locals = LocalCapture.CAPTURE_FAILHARD)
	private void createLevels(ChunkProgressListener progressListener, CallbackInfo ci, ServerLevelData serverLevelData, WorldGenSettings genSettings, boolean flag, long i, long j, List<CustomSpawner> spawnerList, MappedRegistry<LevelStem> dimensionRegistry, LevelStem overworld, ChunkGenerator overworldGenerator, DimensionType overworldType, ServerLevel overworldLevel, DimensionDataStorage dimensionStorage, WorldBorder border, Iterator iterator, Map.Entry<ResourceKey<LevelStem>, LevelStem> entry)
	{
		LevelStem dimension = entry.getValue();
		if(dimension.generator instanceof CopyingChunkGenerator dummy)
			dimension.generator = dimensionRegistry.get(dummy.dimensionId).generator();
	}
}