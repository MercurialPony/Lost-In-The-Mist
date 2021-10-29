package melonslise.spook.common.init;

import melonslise.spook.SpookMod;
import melonslise.spook.common.entity.ITwistedMob;
import melonslise.spook.common.entity.TwistedCow;
import melonslise.spook.common.entity.TwistedMushroomCow;
import melonslise.spook.common.entity.TwistedPig;
import melonslise.spook.common.entity.TwistedSheep;
import melonslise.spook.common.entity.TwistedWolf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SpookEntityTypes
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SpookMod.ID);

	public static final RegistryObject<EntityType<TwistedPig>> TWISTED_PIG = ENTITY_TYPES.register("twisted_pig", () ->
		EntityType.Builder.<TwistedPig>of(TwistedPig::new, MobCategory.MONSTER)
			.sized(0.9f, 0.9f)
			.clientTrackingRange(8)
			.build(SpookMod.ID + ":twisted_pig"));

	public static final RegistryObject<EntityType<TwistedSheep>> TWISTED_SHEEP = ENTITY_TYPES.register("twisted_sheep", () ->
		EntityType.Builder.<TwistedSheep>of(TwistedSheep::new, MobCategory.MONSTER)
			.sized(0.9f, 1.3f)
			.clientTrackingRange(8)
			.build(SpookMod.ID + ":twisted_sheep"));

	public static final RegistryObject<EntityType<TwistedCow>> TWISTED_COW = ENTITY_TYPES.register("twisted_cow", () ->
		EntityType.Builder.<TwistedCow>of(TwistedCow::new, MobCategory.MONSTER)
			.sized(0.9f, 1.4f)
			.clientTrackingRange(8)
			.build(SpookMod.ID + ":twisted_cow"));

	public static final RegistryObject<EntityType<TwistedWolf>> TWISTED_WOLF = ENTITY_TYPES.register("twisted_wolf", () ->
		EntityType.Builder.<TwistedWolf>of(TwistedWolf::new, MobCategory.MONSTER)
			.sized(0.6f, 0.85f)
			.clientTrackingRange(8)
			.build(SpookMod.ID + ":twisted_wolf"));

	public static final RegistryObject<EntityType<TwistedMushroomCow>> TWISTED_MUSHROOM_COW = ENTITY_TYPES.register("twisted_mushroom_cow", () ->
		EntityType.Builder.<TwistedMushroomCow>of(TwistedMushroomCow::new, MobCategory.MONSTER)
			.sized(0.9f, 1.4f)
			.clientTrackingRange(8)
			.build(SpookMod.ID + ":twisted_mushroom_cow"));

	private SpookEntityTypes() {}

	public static void createAttributes(EntityAttributeCreationEvent e)
	{
		e.put(TWISTED_PIG.get(), TwistedPig.createAttributes().build());
		e.put(TWISTED_SHEEP.get(), TwistedSheep.createAttributes().build());
		e.put(TWISTED_COW.get(), TwistedCow.createAttributes().build());
		e.put(TWISTED_WOLF.get(), TwistedWolf.createAttributes().build());
		e.put(TWISTED_MUSHROOM_COW.get(), TwistedMushroomCow.createAttributes().build());
	}

	public static void enchanceBiome(BiomeLoadingEvent e)
	{
		if(e.getCategory() == Biome.BiomeCategory.NETHER || e.getCategory() == Biome.BiomeCategory.THEEND)
		{
			return;
		}

		e.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TWISTED_PIG.get(), 100, 4, 4));
		e.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TWISTED_SHEEP.get(), 100, 4, 4));
		e.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TWISTED_COW.get(), 100, 4, 4));
		e.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TWISTED_WOLF.get(), 100, 4, 4));
		e.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TWISTED_MUSHROOM_COW.get(), 50, 2, 2));
	}

	public static void registerSpawns()
	{
		SpawnPlacements.register(TWISTED_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ITwistedMob::canSpawn);
		SpawnPlacements.register(TWISTED_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ITwistedMob::canSpawn);
		SpawnPlacements.register(TWISTED_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ITwistedMob::canSpawn);
		SpawnPlacements.register(TWISTED_WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ITwistedMob::canSpawn);
		SpawnPlacements.register(TWISTED_MUSHROOM_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ITwistedMob::canSpawn);
	}
}