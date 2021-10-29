package melonslise.spook.common.capability;

import net.minecraft.nbt.FloatTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISanity extends INBTSerializable<FloatTag>
{
	float get();

	void set(float sanity);

	float getChance();
}