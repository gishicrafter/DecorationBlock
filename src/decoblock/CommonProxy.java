package decoblock;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommonProxy
{
	public static final String BLOCKS_PNG = "decoblock/block_texture.png";
	public static final String CHANNEL = "DecoBlock";
	
	public static final String DIR_LOCAL_TEXTURE = "resources/mod/decorations";
	public static final String DIR_CACHE_TEXTURE = "cache/decorations";
	
	public static final String DEFAULT_CACHE_SUBDIR = "share";

	public static final String ICON_BLOCK_DECORATION = "/decoblock/icon/decorationBlock.png";
	
	public static int renderID = 0;
	
	public static void prepareDirsForTextureTypes(File basedir)
	{
		for(TextureType type : TextureType.availableTypes){
			File dir = new File(basedir, type.typeText());
			if(!dir.exists()){
				dir.mkdir();
			}
		}
	}
	
	public void prepareDir()
	{
		try{
			File resdir = new File(getBaseDir(), DIR_LOCAL_TEXTURE);
			if(!resdir.exists()){
				resdir.mkdirs();
			}
			
			prepareDirsForTextureTypes(resdir);
			
		}catch(Exception e){
			ModLoader.getLogger().throwing("CommonProxy", "prepareDir", e);
			ModLoader.throwException("Exception occured in CommonProxy", e);
		}
	}
	
	public void sendToServer(Packet packet){}
	
	public void sendToPlayer(EntityPlayer player, Packet packet)
	{
		EntityPlayerMP target = (EntityPlayerMP)player;
		if(target == null) return;
		target.playerNetServerHandler.sendPacketToPlayer(packet);
	}
	
	public void sendToPlayer(NetServerHandler handler, Packet packet)
	{
		handler.sendPacketToPlayer(packet);
	}
	
	
	public EntityPlayerMP getPlayer(NetServerHandler handler)
	{
		try{
			return (EntityPlayerMP) ModLoader.getPrivateValue(NetServerHandler.class, handler, "playerEntity");
		}catch(Exception e){
		}
		return null;
	}	
	
	public WorldServer getWorld(EntityPlayerMP player)
	{
		return player.mcServer.worldServerForDimension(player.dimension);
	}
	
	public World getWorld(int dimension)
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dimension);
	}
	
	public World getClientWorld()
	{
		return null;
	}
	
	public int cacheTexture(String name)
	{
		return -1;
	}
	
	public File getBaseDir()
	{
		return MinecraftServer.getServer().getFile(".");
	}
	
	public long getSystemTime()
	{
		return 0;
	}

	
	public void registerRenderer()
	{
	}
	
	public void resetTextureManager(String cacheSubDir)
	{
	}
}
