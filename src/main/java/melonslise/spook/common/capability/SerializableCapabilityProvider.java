package melonslise.spook.common.capability;

import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

public class SerializableCapabilityProvider<A extends INBTSerializable<B>, B extends Tag> extends CapabilityProvider<A> implements ICapabilitySerializable<B>
{
	public SerializableCapabilityProvider(Capability<A> cap, A inst)
	{
		super(cap, inst);
	}

	@Override
	public B serializeNBT()
	{
		return this.inst.serializeNBT();
	}

	@Override
	public void deserializeNBT(B nbt)
	{
		this.inst.deserializeNBT(nbt);
	}
}