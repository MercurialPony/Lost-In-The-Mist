package melonslise.spook.common.init;

import melonslise.spook.SpookMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class SpookDimensionKeys
{
	public static final ResourceKey<Level> FOGWORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(SpookMod.ID, "fogworld"));

	private SpookDimensionKeys() {}
}