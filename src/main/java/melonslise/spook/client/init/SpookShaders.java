package melonslise.spook.client.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import melonslise.spook.SpookMod;
import melonslise.spook.client.renderer.shader.ExtendedPostChain;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpookShaders implements ResourceManagerReloadListener
{
	protected static final List<ExtendedPostChain> SHADERS = new ArrayList<>(2);

	protected static ExtendedPostChain fog, vhs, blur, distortAndDesaturate;

	@Override
	public void onResourceManagerReload(ResourceManager mgr)
	{
		this.clear();
		try
		{
			init(mgr);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void init(ResourceManager mgr) throws IOException
	{
		fog = add(new ExtendedPostChain(SpookMod.ID, "fog"));
		vhs = add(new ExtendedPostChain(SpookMod.ID, "vhs"));
		blur = add(new ExtendedPostChain(SpookMod.ID, "blur"));
		distortAndDesaturate = add(new ExtendedPostChain(SpookMod.ID, "distortanddesaturate"));
	}

	public void clear()
	{
		SHADERS.forEach(PostChain::close);
		SHADERS.clear();
	}

	public static ExtendedPostChain add(ExtendedPostChain shader)
	{
		SHADERS.add(shader);
		return shader;
	}

	public static ExtendedPostChain getFog()
	{
		return fog;
	}

	public static ExtendedPostChain getVhs()
	{
		return vhs;
	}

	public static ExtendedPostChain getBlur()
	{
		return blur;
	}

	public static ExtendedPostChain getDistortAndDesaturate()
	{
		return distortAndDesaturate;
	}
}