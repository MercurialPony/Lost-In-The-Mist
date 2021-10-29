package melonslise.spook.common.item;

import java.util.List;

import melonslise.spook.SpookMod;
import melonslise.spook.common.init.SpookCapabilities;
import melonslise.spook.common.init.SpookSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SanityItem extends Item
{
	public static final Component TOOLTIP = new TranslatableComponent("tooltip." + SpookMod.ID + ".sanity_item");

	public final float sanity;

	public SanityItem(Properties props, float sanity)
	{
		super(props);
		this.sanity = sanity;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		level.playSound(player, player.getX(), player.getY(), player.getZ(), SpookSoundEvents.PAGE_FLIP.get(), SoundSource.PLAYERS, 1f , 1f);

		if(!level.isClientSide)
		{
			player.getCapability(SpookCapabilities.SANITY).ifPresent(sanity -> sanity.set(sanity.get() + this.sanity));
		}

		if(!player.getAbilities().instabuild)
		{
			stack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> lines, TooltipFlag flag)
	{
		lines.add(TOOLTIP);
	}
}