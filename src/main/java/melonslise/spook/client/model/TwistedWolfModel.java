package melonslise.spook.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import melonslise.spook.SpookMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class TwistedWolfModel<T extends Entity> extends EntityModel<T>
{
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(SpookMod.ID, "twisted_wolf"), "main");

	protected final ModelPart head;
	protected final ModelPart realHead;
	protected final ModelPart body;
	protected final ModelPart rightHindLeg;
	protected final ModelPart leftHindLeg;
	protected final ModelPart rightFrontLeg;
	protected final ModelPart leftFrontLeg;
	protected final ModelPart tail;
	protected final ModelPart realTail;
	protected final ModelPart upperBody;

	protected final ImmutableList<ModelPart> headParts, bodyParts;

	private static final int LEG_SIZE = 8;

	public TwistedWolfModel(ModelPart part)
	{
		this.head = part.getChild("head");
		this.realHead = this.head.getChild("real_head");
		this.body = part.getChild("body");
		this.upperBody = part.getChild("upper_body");
		this.rightHindLeg = part.getChild("right_hind_leg");
		this.leftHindLeg = part.getChild("left_hind_leg");
		this.rightFrontLeg = part.getChild("right_front_leg");
		this.leftFrontLeg = part.getChild("left_front_leg");
		this.tail = part.getChild("tail");
		this.realTail = this.tail.getChild("real_tail");

		this.headParts = ImmutableList.of(this.head);
		this.bodyParts = ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.upperBody);
	}

	public static LayerDefinition createLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		float f = 13.5F;
		PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
		partdefinition1.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F).texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F), PartPose.ZERO);
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
		CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
		partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-2.5F, 16.0F, 7.0F));
		partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(0.5F, 16.0F, 7.0F));
		partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-2.5F, 16.0F, -4.0F));
		partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(0.5F, 16.0F, -4.0F));
		PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, ((float)Math.PI / 5F), 0.0F, 0.0F));
		partdefinition2.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	protected Iterable<ModelPart> headParts()
	{
		return this.headParts;
	}

	protected Iterable<ModelPart> bodyParts()
	{
		return this.bodyParts;
	}

	@Override
	public void prepareMobModel(T entity, float f1, float f2, float f3)
	{
		this.tail.yRot = 0.0F;
		this.realTail.xRot = 1f;

		this.body.setPos(0.0F, 14.0F, 2.0F);
		this.body.xRot = ((float)Math.PI / 2F);
		this.upperBody.setPos(-1.0F, 14.0F, -3.0F);
		this.upperBody.xRot = this.body.xRot;
		this.tail.setPos(-1.0F, 12.0F, 8.0F);
		this.rightHindLeg.setPos(-2.5F, 16.0F, 7.0F);
		this.leftHindLeg.setPos(0.5F, 16.0F, 7.0F);
		this.rightFrontLeg.setPos(-2.5F, 16.0F, -4.0F);
		this.leftFrontLeg.setPos(0.5F, 16.0F, -4.0F);
		this.rightHindLeg.xRot = Mth.cos(f1 * 0.6662F) * 1.4F * f2;
		this.leftHindLeg.xRot = Mth.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
		this.rightFrontLeg.xRot = Mth.cos(f1 * 0.6662F + (float)Math.PI) * 1.4F * f2;
		this.leftFrontLeg.xRot = Mth.cos(f1 * 0.6662F) * 1.4F * f2;

		/*
		this.realHead.zRot = p_104132_.getHeadRollAngle(p_104135_) + p_104132_.getBodyRollAngle(p_104135_, 0.0F);
		this.upperBody.zRot = p_104132_.getBodyRollAngle(p_104135_, -0.08F);
		this.body.zRot = p_104132_.getBodyRollAngle(p_104135_, -0.16F);
		this.realTail.zRot = p_104132_.getBodyRollAngle(p_104135_, -0.2F);
		 */
	}

	public void setupAnim(T p_104137_, float f1, float f2, float f3, float f4, float f5)
	{
		this.head.xRot = f5 * ((float) Math.PI / 180F);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack mtx, VertexConsumer bld, int i1, int i2, float f1, float f2, float f3, float f4)
	{
		this.headParts().forEach((p_102061_) ->
		{
			p_102061_.render(mtx, bld, i1, i2, f1, f2, f3, f4);
		});
		this.bodyParts().forEach((p_102051_) ->
		{
			p_102051_.render(mtx, bld, i1, i2, f1, f2, f3, f4);
		});
	}
}