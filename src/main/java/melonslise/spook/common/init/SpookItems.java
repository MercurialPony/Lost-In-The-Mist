package melonslise.spook.common.init;

import melonslise.spook.SpookMod;
import melonslise.spook.common.item.SanityItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SpookItems
{
	public static final CreativeModeTab MAIN_TAB = new CreativeModeTab(SpookMod.ID)
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(OLD_PHOTO_2.get());
		}
	};

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SpookMod.ID);

	public static final RegistryObject<Item>
		OLD_BOOK = ITEMS.register("old_book", () -> new SanityItem(new Item.Properties().tab(MAIN_TAB), 10f)),
		OLD_PHOTO_1 = ITEMS.register("old_photo_1", () -> new SanityItem(new Item.Properties().tab(MAIN_TAB), 10f)),
		OLD_PHOTO_2 = ITEMS.register("old_photo_2", () -> new SanityItem(new Item.Properties().tab(MAIN_TAB), 10f)),
		OLD_PHOTO_3 = ITEMS.register("old_photo_3", () -> new SanityItem(new Item.Properties().tab(MAIN_TAB), 10f)),
		OLD_NOTES = ITEMS.register("old_notes", () -> new SanityItem(new Item.Properties().tab(MAIN_TAB), 10f)),

		TWISTED_PIG_SPAWN_EGG = ITEMS.register("twisted_pig_spawn_egg", () -> new ForgeSpawnEggItem(SpookEntityTypes.TWISTED_PIG, 0xF0A5A2, 0xDB635F, new Item.Properties().tab(MAIN_TAB))),
		TWISTED_SHEEP_SPAWN_EGG = ITEMS.register("twisted_sheep_spawn_egg", () -> new ForgeSpawnEggItem(SpookEntityTypes.TWISTED_SHEEP, 0xE7E7E7, 0xFFB5B5, new Item.Properties().tab(MAIN_TAB))),
		TWISTED_COW_SPAWN_EGG = ITEMS.register("twisted_cow_spawn_egg", () -> new ForgeSpawnEggItem(SpookEntityTypes.TWISTED_COW, 0x443626, 0xA1A1A1, new Item.Properties().tab(MAIN_TAB))),
		TWISTED_WOLF_SPAWN_EGG = ITEMS.register("twisted_wolf_spawn_egg", () -> new ForgeSpawnEggItem(SpookEntityTypes.TWISTED_WOLF, 0xD7D3D3, 0xCEAF96, new Item.Properties().tab(MAIN_TAB))),
		TWISTED_MOOSHROOM_SPAWN_EGG = ITEMS.register("twisted_mooshroom_spawn_egg", () -> new ForgeSpawnEggItem(SpookEntityTypes.TWISTED_MUSHROOM_COW, 0xA00F10, 0xB7B7B7, new Item.Properties().tab(MAIN_TAB)));

	private SpookItems() {}
}