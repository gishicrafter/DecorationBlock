package decoblock.network;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;

import decoblock.ServerTextureCache;
import decoblock.ServerTextureManager;
import decoblock.TextureType;


public class PacketBuilderRequestImage extends AbstractPacketBuilder
{
	public static final String subchannel = "RqImg";
	
	public String name;
	public TextureType type;
	
	public PacketBuilderRequestImage()
	{
		super(subchannel);
	}
	
	public PacketBuilderRequestImage(String name, TextureType type)
	{
		super(subchannel);
		this.name = name;
		this.type = type;
	}

	@Override
	public void writePacketData(DataOutputStream dos) throws IOException {
 		dos.writeUTF(type.typeText());
 		dos.writeUTF(name);
	}

	@Override
	public void readPacketData(DataInputStream dis) throws IOException {
		type = TextureType.fromTypeText(dis.readUTF());
		name = dis.readUTF();
	}

	@Override
	public void handleData(INetworkManager manager) {
		ServerTextureCache cache = ServerTextureManager.instance.getTexture(name, type);
		byte[] image = cache.getImage();
		
		PacketBuilderTransferImage res = new PacketBuilderTransferImage(name, type, image);
		Packet packet = res.buildPacket();
		if(packet != null) manager.addToSendQueue(packet);
	}

}
