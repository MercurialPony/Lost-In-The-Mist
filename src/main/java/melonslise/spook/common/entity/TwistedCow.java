package melonslise.spook.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TwistedCow extends Monster implements ITwistedMob
{
	public TwistedCow(EntityType<? extends Monster> type, Level level)
	{
		super(type, level);
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2d, true));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1d));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createAttributes()
	{
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 10d).add(Attributes.MOVEMENT_SPEED, 0.2d);
	}

	@Override
	public float getVoicePitch()
	{
		return 0.05f;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.COW_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource)
	{
		return SoundEvents.GHAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.COW_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state)
	{
		this.playSound(SoundEvents.COW_STEP, 0.15f, 1f);
	}
}