package melonslise.spook.common.util;

import java.util.BitSet;
import java.util.Random;

import io.netty.buffer.Unpooled;
import melonslise.spook.common.entity.ITwistedMob;
import melonslise.spook.common.init.SpookDimensionKeys;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lighting.LevelLightEngine;

public final class SpookyUtil
{
	private SpookyUtil() {}

	public static boolean chance(Random rng, float ch)
	{
		return ch == 1f || ch != 0f && rng.nextFloat() <= ch;
	}

	public static float remap(float v, float minOld, float maxOld, float minNew, float maxNew)
	{
		return minNew + (v - minOld) * (maxNew - minNew) / (maxOld - minOld);
	}

	public static boolean allowedInFogworld(Entity entity)
	{
		return entity instanceof ITwistedMob || entity != null && !(entity instanceof Mob);
	}

	public static boolean inFogworld(Entity entity)
	{
		return entity.level.dimension() == SpookDimensionKeys.FOGWORLD;
	}

	public static float getDarkness(Level level) // FIXME interpolate
	{
		float f1 = Mth.cos(level.getTimeOfDay(1f) * Mth.PI * 2f) * 2f + 0.5f;
		f1 = Mth.clamp(f1, 0f, 1f);
		return 1f -  f1;
	}

	public static Entity copy(Entity entity, Level level)
	{
		Entity copy = entity.getType().create(level);
		CompoundTag nbt = entity.serializeNBT();
		nbt.putUUID("UUID", copy.getUUID());
		copy.load(nbt);
		return copy;
	}

	// copy the ClientBoundLevelChunkPacket code because I don't know any better...
	// don't do biomes cos they already match in our case
	public static void replaceChunk(LevelChunk from, LevelChunk to)
	{
		Level level = to.getLevel();
		ChunkPos pos = to.getPos();
		LevelChunkSection[] sections = to.getSections();

		// put data in right format
		byte[] buf = new byte[calculateChunkSize(from)];
		BitSet set = extractChunkData(from, new FriendlyByteBuf(Unpooled.wrappedBuffer(buf).writerIndex(0))); // getWriteBuffer
		FriendlyByteBuf friendlyBuf = new FriendlyByteBuf(Unpooled.wrappedBuffer(buf)); // getReadBuffer

		// clear all BEs
		to.getBlockEntities().values().forEach(to::onBlockEntityRemove);
		to.getBlockEntities().clear();

		// replace each section
		for (int i = 0; i < sections.length; ++i)
		{
			LevelChunkSection section = sections[i];
			if (!set.get(i))
			{
				if (section != LevelChunk.EMPTY_SECTION)
				{
					sections[i] = LevelChunk.EMPTY_SECTION;
				}
			}
			else
			{
				if (section == LevelChunk.EMPTY_SECTION)
				{
					section = new LevelChunkSection(to.getSectionYFromSectionIndex(i));
					sections[i] = section;
				}

				section.read(friendlyBuf);
			}
		}

		// set all heightmaps
		for(var entry : from.getHeightmaps())
		{
			to.setHeightmap(entry.getKey(), entry.getValue().getRawData().clone());
		}

		// update light engine
		LevelLightEngine fromLightEngine = from.getLevel().getLightEngine();
		LevelLightEngine lightEngine = level.getLightEngine();
		lightEngine.enableLightSources(pos, true);

		for (int i = 0; i < sections.length; ++i)
		{
			LevelChunkSection section = sections[i];
			int k = level.getSectionYFromSectionIndex(i);
			lightEngine.updateSectionStatus(SectionPos.of(pos.x, k, pos.z), LevelChunkSection.isEmpty(section));
		}

		// This part is from ChunkSerializer / ClientPacketListener
		for(int i = fromLightEngine.getMinLightSection(); i < fromLightEngine.getMaxLightSection(); ++i)
		{
			SectionPos sectionPos = SectionPos.of(pos, i);

			// FIXME dont if empty chunk?
			DataLayer blockLayer = fromLightEngine.getLayerListener(LightLayer.BLOCK).getDataLayerData(sectionPos);
			if(blockLayer != null && !blockLayer.isEmpty())
				lightEngine.queueSectionData(LightLayer.BLOCK, sectionPos, new DataLayer(blockLayer.getData().clone()), true);

			DataLayer skyLayer = fromLightEngine.getLayerListener(LightLayer.SKY).getDataLayerData(sectionPos);
			if(skyLayer != null && !skyLayer.isEmpty())
				lightEngine.queueSectionData(LightLayer.SKY, sectionPos, new DataLayer(skyLayer.getData().clone()), true);
			// FIXME setSectionDirtyWithNeighbors??
		}

		// Update BEs
		for(BlockEntity fromEntity : from.getBlockEntities().values())
		{
			BlockEntity toEntity = to.getBlockEntity(fromEntity.getBlockPos(), LevelChunk.EntityCreationType.IMMEDIATE);
			if(toEntity != null)
			{
				toEntity.handleUpdateTag(fromEntity.getUpdateTag());
			}
		}
	}

	private static int calculateChunkSize(LevelChunk chunk)
	{
		LevelChunkSection[] sections = chunk.getSections();
		int length = sections.length;
		int size = 0;

		for (int i = 0; i < length; ++i)
		{
			LevelChunkSection section = sections[i];
			if (section != LevelChunk.EMPTY_SECTION && !section.isEmpty())
			{
				size += section.getSerializedSize();
			}
		}

		return size;
	}

	private static BitSet extractChunkData(LevelChunk chunk, FriendlyByteBuf buf)
	{
		BitSet bits = new BitSet();
		LevelChunkSection[] sections = chunk.getSections();
		int length = sections.length;

		for (int i = 0; i < length; ++i)
		{
			LevelChunkSection section = sections[i];
			if (section != LevelChunk.EMPTY_SECTION && !section.isEmpty())
			{
				bits.set(i);
				section.write(buf);
			}
		}

		return bits;
	}
}