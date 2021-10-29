package melonslise.spook.common.entity;

import java.util.Random;

import melonslise.spook.common.init.SpookDimensionKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;

public interface ITwistedMob
{
	public static boolean canSpawn(EntityType<? extends Monster> type, ServerLevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, Random rng)
	{
		return levelAccessor instanceof ServerLevel level && level.dimension() == SpookDimensionKeys.FOGWORLD && Monster.checkMonsterSpawnRules(type, level, spawnType, pos, rng);
	}
}