package melonslise.spook.common.loot;

import java.util.List;
import java.util.Random;

import com.google.gson.JsonObject;

import melonslise.spook.common.init.SpookItems;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class SanityItemLootModifier extends LootModifier
{
	public final int count;
	public final float chance;

	public SanityItemLootModifier(LootItemCondition[] conditions, int count, float chance)
	{
		super(conditions);
		this.count = count;
		this.chance = chance;
	}

	public static ItemStack randomItem(Random rng) // FIXME jesus. this is ugly
	{
		switch(rng.nextInt(3))
		{
		case 0:
			return new ItemStack(SpookItems.OLD_BOOK.get());
		case 1:
			return new ItemStack(SpookItems.OLD_NOTES.get());
		default:
			switch(rng.nextInt(3))
			{
			case 0:
				return new ItemStack(SpookItems.OLD_PHOTO_1.get());
			case 1:
				return new ItemStack(SpookItems.OLD_PHOTO_2.get());
			default:
				return new ItemStack(SpookItems.OLD_PHOTO_3.get());
			}
		}
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> loot, LootContext ctx)
	{
		Random rng = ctx.getRandom();

		for(int i = 0; i < this.count; ++i)
		{
			if(SpookyUtil.chance(rng, this.chance))
			{
				loot.add(randomItem(rng));
			}
		}

		return loot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<SanityItemLootModifier>
	{
		@Override
		public SanityItemLootModifier read(ResourceLocation id, JsonObject o, LootItemCondition[] conditions)
		{
			return new SanityItemLootModifier(conditions, GsonHelper.getAsInt(o, "count"), GsonHelper.getAsFloat(o, "chance"));
		}

		@Override
		public JsonObject write(SanityItemLootModifier lootModifier)
		{
			JsonObject o = this.makeConditions(lootModifier.conditions);
			o.addProperty("count", lootModifier.count);
			o.addProperty("chance", lootModifier.chance);
			return o;
		}
	}
}