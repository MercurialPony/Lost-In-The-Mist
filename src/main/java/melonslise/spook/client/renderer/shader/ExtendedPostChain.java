package melonslise.spook.client.renderer.shader;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedPostChain extends PostChain
{
	public ExtendedPostChain(TextureManager texManager, ResourceManager resManager, RenderTarget target, ResourceLocation name) throws JsonSyntaxException, IOException
	{
		super(texManager, resManager, target, name);
	}

	public ExtendedPostChain(String domain, String name) throws JsonSyntaxException, IOException
	{
		this(Minecraft.getInstance().getTextureManager(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getMainRenderTarget(), new ResourceLocation(domain, "shaders/post/" + name + ".json"));
		this.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
	}

	public EffectInstance getMainShader()
	{
		return this.passes.get(0).getEffect();
	}

	@Override
	public void process(float frameTime)
	{
		Window w = Minecraft.getInstance().getWindow();
		if(this.screenWidth != w.getWidth() || this.screenHeight != w.getHeight())
			this.resize(w.getWidth(), w.getHeight());
		super.process(frameTime);
	}
}