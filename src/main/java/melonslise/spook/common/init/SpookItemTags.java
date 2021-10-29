package melonslise.spook.common.init;

import melonslise.spook.SpookMod;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public final class SpookItemTags
{
	public static final Tag.Named<Item> BAD_FOOD = bind("bad_food");

	private SpookItemTags() {}

	public static Tag.Named<Item> bind(String name)
	{
		return ItemTags.bind(SpookMod.ID + ":" + name);
	}
}