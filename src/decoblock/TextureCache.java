package decoblock;

import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.src.ModLoader;
import decoblock.network.PacketBuilderRequestImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TextureCache implements Comparable<TextureCache>
{
	private String textureName;
	private String textureFullName;
	private TextureType textureType;
	private boolean local;
	private boolean cached;
	private boolean loaded;
	private File localFile;
	private File cacheFile;
	
	public TextureCache(String name, TextureType type)
	{
		loaded = false;
		textureName = name;
		textureType = type;
		textureFullName = type.typeText() + "/" + name;
		localFile = new File(new File(DecorationBlock.proxy.getBaseDir(), CommonProxy.DIR_LOCAL_TEXTURE), textureFullName);
		cacheFile = new File(new File(new File(DecorationBlock.proxy.getBaseDir(), CommonProxy.DIR_CACHE_TEXTURE), TextureManager.instance.getCacheSubdir()), textureFullName);
		if(localFile.exists())
		{
			local = true;
		}
		else if(cacheFile.exists())
		{
			local = false;
			cached = true;
		}
		else
		{
			local = false;
			cached = false;
		}
	}
	
	private RenderEngine getRenderEngine()
	{
		return ModLoader.getMinecraftInstance().renderEngine;
	}
	
	public int getTexture()
	{
		RenderEngine re = getRenderEngine();
		if(!loaded) loadTexture();
		
		if(loaded){
			return re.getTexture("%decoration%/" + textureFullName);
		}else{
			ModLoader.getLogger().finer("Get texture from default icon");
			return re.getTexture(CommonProxy.ICON_BLOCK_DECORATION);
		}
	}
	
	public String getTextureName()
	{
		return textureName;
	}
	
	public TextureType getTextureType()
	{
		return textureType;
	}
	
	private BufferedImage readTextureImage(InputStream par1InputStream) throws IOException
	{
		BufferedImage var2 = ImageIO.read(par1InputStream);
		par1InputStream.close();
		return var2;
	}
	
	public void loadTexture()
	{
		RenderEngine re = getRenderEngine();
		InputStream is;
		try{
			if(local){
				is = new FileInputStream(localFile);
			}else if(cached || cacheFile.exists()){
				cached = true;
				is = new FileInputStream(cacheFile);
			}else{
				DecorationBlock.proxy.sendToServer((new PacketBuilderRequestImage(textureName, textureType)).buildPacket());
				return;
			}
			
			int tex = re.getTexture("%decoration%/" + textureFullName);
			BufferedImage img = readTextureImage(is);
			re.setupTexture(img, tex);
			
			loaded = true;
			//ModLoader.getLogger().finer("Image %decoration%/" + textureFullName + " was loaded into texture " + tex);
			
		}catch(Exception e){
			ModLoader.getLogger().throwing("TextureCache", "loadTexture", e);
			ModLoader.throwException("Exception occured in TextureCache", e);
		}
	}
	
	@Override
	public int compareTo(TextureCache y)
	{
		int result = textureType.compareTo(y.textureType);
		if(result == 0){
			result = textureName.compareTo(y.textureName);
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o)
	{
		return (o instanceof TextureCache) && (compareTo((TextureCache)o) == 0);
	}
	
	@Override
	public int hashCode()
	{
		return textureName.hashCode() ^ textureType.hashCode();
	}
}
