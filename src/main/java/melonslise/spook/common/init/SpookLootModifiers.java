package melonslise.spook.common.init;

import melonslise.spook.SpookMod;
import melonslise.spook.common.loot.SanityItemLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SpookLootModifiers
{
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, SpookMod.ID);

	public static final RegistryObject<SanityItemLootModifier.Serializer> SANITY_ITEM_ADDER = SERIALIZERS.register("sanity_item_adder", SanityItemLootModifier.Serializer::new);

	private SpookLootModifiers() {}
}