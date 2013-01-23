package decoblock.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.TreeMap;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import decoblock.CommonProxy;

public class PacketHandler implements IPacketHandler {

	public static TreeMap<String, Class<? extends IPacketBuilder>> packetBuilderList = new TreeMap<String, Class<? extends IPacketBuilder>>();
	
	public static void registerPacketBuilder(Class<? extends IPacketBuilder> klass)
	{
		try{
			String subchannel = (String) klass.getField("subchannel").get(null);
			if(subchannel != null && !subchannel.equals("")){
				packetBuilderList.put(subchannel, klass);
			}
		}catch(Exception e){
		}
	}
	
	static{
		registerPacketBuilder(PacketBuilderRequestImage.class);
		registerPacketBuilder(PacketBuilderRequestTextureList.class);
		registerPacketBuilder(PacketBuilderSetDecorationMode.class);
		registerPacketBuilder(PacketBuilderSetMirror.class);
		registerPacketBuilder(PacketBuilderSetRotate.class);
		registerPacketBuilder(PacketBuilderSetTexture.class);
		registerPacketBuilder(PacketBuilderTextureList.class);
		registerPacketBuilder(PacketBuilderTileEntityData.class);
		registerPacketBuilder(PacketBuilderTransferImage.class);
		registerPacketBuilder(PacketBuilderInitClient.class);
	}
	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		if(packet.channel.equals(CommonProxy.CHANNEL)){
			ByteArrayInputStream bais = new ByteArrayInputStream(packet.data);
	 		DataInputStream dis = new DataInputStream(bais);
			try{
				String subChannel = dis.readUTF();
				if(packetBuilderList.get(subChannel) != null){
					IPacketBuilder builder = packetBuilderList.get(subChannel).newInstance();
					builder.readPacketData(dis);
					builder.handleData(manager);
				}
			}catch(IOException e){
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try{
				dis.close();
			}catch(Exception e){
			}
		}
	}

}
