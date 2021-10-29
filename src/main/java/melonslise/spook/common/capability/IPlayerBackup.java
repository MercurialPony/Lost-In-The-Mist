package melonslise.spook.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerBackup extends INBTSerializable<CompoundTag>
{
	CompoundTag get();

	void set(CompoundTag nbt);

	void clearPlayer();

	void backup();

	void restore();
}