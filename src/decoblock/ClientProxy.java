package decoblock;

import java.io.File;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import decoblock.render.TileEntityDecorationRenderer;

public class ClientProxy extends CommonProxy
{
	
	@Override
	public void prepareDir()
	{
		super.prepareDir();
		
		try{
			File cachedir = new File(new File(getBaseDir(), DIR_CACHE_TEXTURE), DEFAULT_CACHE_SUBDIR);
			if(!cachedir.exists()){
				cachedir.mkdirs();
			}
			
			prepareDirsForTextureTypes(cachedir);
			
		}catch(Exception e){
			FMLLog.getLogger().throwing("CommonProxyClient", "prepareDir", e);
			FMLCommonHandler.instance().raiseException(e, "Exception occured in CommonProxyClient", true);
		}
	}
	
	@Override
	public void sendToServer(Packet packet)
	{
		FMLClientHandler.instance().getClient().getSendQueue().addToSendQueue(packet);
	}
	
	@Override
	public void sendToPlayer(EntityPlayer player, Packet packet)
	{
		EntityPlayerMP target = (EntityPlayerMP)player;
		if(target == null) return;
		target.playerNetServerHandler.sendPacketToPlayer(packet);
	}
	
	@Override
	public void sendToPlayer(NetServerHandler handler, Packet packet)
	{
		handler.sendPacketToPlayer(packet);
	}
	
	@Override
	public EntityPlayerMP getPlayer(NetServerHandler handler)
	{
		return handler.getPlayer();
	}
	
	@Override
	public World getWorld(int dimension)
	{
		return FMLClientHandler.instance().getClient().getIntegratedServer().worldServerForDimension(dimension);
	}
	
	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override
	public int cacheTexture(String name)
	{
		return -1;
	}
	
	@Override
	public File getBaseDir()
	{
		return Minecraft.getMinecraftDir();
	}
	
	@Override
	public long getSystemTime()
	{
		return Minecraft.getSystemTime();
	}
	
	@Override
	public void registerRenderer()
	{
		MinecraftForgeClient.preloadTexture(BLOCKS_PNG);
		
		renderID = RenderingRegistry.getNextAvailableRenderId();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoration.class, new TileEntityDecorationRenderer());
	}
	
	@Override
	public void resetTextureManager(String cacheSubDir)
	{
		TextureManager.reset(cacheSubDir);
	}
}
