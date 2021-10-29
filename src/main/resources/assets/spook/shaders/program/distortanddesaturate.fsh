#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 OutSize;

uniform float DistortStrength;
uniform float Saturation;

in vec2 texCoord;

out vec4 fragColor;

// https://www.shadertoy.com/view/td2GzW
// Thank you!

vec2 distort(vec2 res, vec2 uv, float power)
{
	vec2 p = uv * res / res.x; //normalized coords with some cheat (assume 1:1 prop)

	float prop = res.x / res.y; // screen proportion

	vec2 m = vec2(0.5, 0.5 / prop); // center of screen
	vec2 d = p - m; //vector from center to current fragment
	float r = sqrt(dot(d, d)); // distance of pixel from center

	float bind; //radius of 1:1 effect

	if (power > 0.0)
	{
        bind = sqrt(dot(m, m)); //stick to corners
	}
	else
	{
		if (prop < 1.0)
		{
			bind = m.x;
		}
		else
		{
        bind = m.y;
		}
	} //stick to borders

	//Weird formulas
	uv = p;
	if (power < 0.0) //antifisheye
	{
		uv = m + normalize(d) * atan(r * -power * 10.0) * bind / atan(-power * bind * 10.0);
	}

    uv.y *= prop;

	return uv;
}

vec3 desaturate(vec3 color, float saturation)
{
	return mix(vec3(dot(color.rgb, vec3(0.299, 0.587, 0.114))), color.rgb, saturation);
}

void main()
{
	fragColor = vec4(desaturate(texture(DiffuseSampler, distort(OutSize, texCoord, DistortStrength)).rgb, Saturation), 1.0);
}