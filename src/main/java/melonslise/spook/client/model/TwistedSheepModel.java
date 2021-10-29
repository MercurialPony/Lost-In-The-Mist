package melonslise.spook.client.model;

import melonslise.spook.SpookMod;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TwistedSheepModel<T extends Entity> extends QuadrupedModel<T>
{
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(SpookMod.ID, "twisted_sheep"), "main");

	public TwistedSheepModel(ModelPart part)
	{
		super(part, false, 8f, 4f, 2f, 2f, 24);
	}
	
	public static LayerDefinition createLayer()
	{
		MeshDefinition mesh = QuadrupedModel.createBodyMesh(12, CubeDeformation.NONE);
		PartDefinition part = mesh.getRoot();
		part.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F), PartPose.offset(0.0F, 6.0F, -8.0F));
		part.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 8).addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
		return LayerDefinition.create(mesh, 64, 32);
	}
}