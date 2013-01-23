package decoblock.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;

import cpw.mods.fml.common.FMLLog;

import decoblock.ServerTextureCache;
import decoblock.ServerTextureManager;
import decoblock.TextureType;

public class PacketBuilderRequestTextureList extends AbstractPacketBuilder
{
	public static final String subchannel = "RqTxList";
	
	public PacketBuilderRequestTextureList()
	{
		super(subchannel);
	}

	@Override
	public void handleData(INetworkManager manager) {
		ServerTextureCache[] cross = ServerTextureManager.instance.getTextureList(TextureType.CROSS);
		ServerTextureCache[] plate = ServerTextureManager.instance.getTextureList(TextureType.PLATE);
		
		PacketBuilderTextureList res = new PacketBuilderTextureList();
		Packet packet;
		
		int i;
		boolean successed;
		for(i = 0; cross != null && i < cross.length; ++i){
			successed = res.addTexture(cross[i].getTextureType(), cross[i].getTextureName());
			if(!successed){
				if(res.size() > 0){
					packet = res.buildPacket();
					if(packet != null){
						manager.addToSendQueue(packet);
						FMLLog.finer("Packet was sent to player : " + res.size() + " bytes");
						res = new PacketBuilderTextureList();
						res.addTexture(cross[i].getTextureType(), cross[i].getTextureName());
					}else{
						FMLLog.warning("Failed to write data packet.");
					}
				}else{
					// failed to process;
					FMLLog.warning("Failed to process RequestTextureList (cross)");
					return;
				}
			}
		}
		for(i = 0; plate != null && i < plate.length; ++i){
			successed = res.addTexture(plate[i].getTextureType(), plate[i].getTextureName());
			if(!successed){
				if(res.size() > 0){
					packet = res.buildPacket();
					if(packet != null){
						manager.addToSendQueue(packet);
						FMLLog.finer("Packet was sent to player : " + res.size() + " bytes");
						res = new PacketBuilderTextureList();
						res.addTexture(plate[i].getTextureType(), plate[i].getTextureName());
					}else{
						FMLLog.warning("Failed to write data packet.");
					}
				}else{
					// failed to process;
					FMLLog.warning("Failed to process RequestTextureList (plate)");
					return;
				}
			}
		}
		
		if(res.size()>0)
		{
			packet = res.buildPacket();
			if(packet != null){
				manager.addToSendQueue(packet);
				FMLLog.finer("Packet was sent to player : " + res.size() + " bytes");
			}else{
				FMLLog.warning("Failed to write data packet.");
			}
		}
	}
}
