package melonslise.spook.common.capability;

import melonslise.spook.SpookMod;
import melonslise.spook.common.util.SpookyUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.common.util.Constants;

public class PlayerBackup implements IPlayerBackup
{
	public static final ResourceLocation ID = new ResourceLocation(SpookMod.ID, "player_backup");

	public final Player player;

	public CompoundTag playerData;

	public PlayerBackup(Player player)
	{
		this.player = player;
	}

	@Override
	public CompoundTag get()
	{
		return this.playerData;
	}

	@Override
	public void set(CompoundTag nbt)
	{
		this.playerData = nbt;
	}

	@Override
	public void clearPlayer()
	{
		this.player.getInventory().clearContent();
		this.player.setHealth(this.player.getMaxHealth());
		FoodData foodData = this.player.getFoodData();
		foodData.setFoodLevel(20);
		foodData.setSaturation(5f);
		foodData.setExhaustion(0f);
		this.player.experienceLevel = 0;
		this.player.totalExperience = 0;
		this.player.experienceProgress = 0f;
		this.player.setScore(0);
	}

	@Override
	public void backup()
	{
		this.playerData = new CompoundTag(); // FIXME find a way to clear the tag instead of re-creating?
		this.playerData.put("Inventory", this.player.getInventory().save(new ListTag()));
		this.playerData.putFloat("Health", this.player.getHealth());
		this.player.getFoodData().addAdditionalSaveData(this.playerData);
		this.playerData.putInt("ExperienceLevel", this.player.experienceLevel);
		this.playerData.putInt("TotalExperience", this.player.totalExperience);
		this.playerData.putFloat("ExperienceProgress", this.player.experienceProgress);
		this.playerData.putInt("Score", this.player.getScore());
	}

	@Override
	public void restore()
	{
		if(this.playerData == null || this.playerData.isEmpty())
			return;

		this.player.getInventory().load(this.playerData.getList("Inventory", Constants.NBT.TAG_COMPOUND));
		this.player.setHealth(this.playerData.getFloat("Health"));
		this.player.getFoodData().readAdditionalSaveData(this.playerData);
		this.player.experienceLevel = this.playerData.getInt("ExperienceLevel");
		this.player.totalExperience = this.playerData.getInt("TotalExperience");
		this.player.experienceProgress = this.playerData.getFloat("ExperienceProgress");
		this.player.setScore(this.playerData.getInt("Score"));
	}

	@Override
	public CompoundTag serializeNBT()
	{
		if(this.playerData == null || !SpookyUtil.inFogworld(this.player))
			return new CompoundTag();

		return this.playerData;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.playerData = nbt;
	}
}