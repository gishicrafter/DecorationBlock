package decoblock.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import decoblock.CommonProxy;
import decoblock.DecorationBlock;
import decoblock.TextureType;


public class PacketBuilderTransferImage implements IPacketBuilder
{
	public static final String subchannel = "TrImg";
	
	public String name;
	public TextureType type;
	public byte[] data;
	
	public PacketBuilderTransferImage()
	{
	}
	
	public PacketBuilderTransferImage(String name, TextureType type, byte[] data)
	{
		this.name = name;
		this.type = type;
		this.data = data;
	}
	
	@Override
	public Packet buildPacket()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		boolean result = false;
		try {
			dos.writeUTF(subchannel);
			writePacketData(dos);
			result = true;
		} catch (IOException e) {
		}
		try {
			dos.close();
		} catch (IOException e) {
		}
		if(result)
			return new Packet250CustomPayload(CommonProxy.CHANNEL, baos.toByteArray());
		else
			return null;
	}

	@Override
	public void writePacketData(DataOutputStream dos) throws IOException {
		dos.writeUTF(type.typeText());
		dos.writeUTF(name);
		if(data == null){
			dos.writeShort(-1);
		}else{
			dos.writeShort(data.length);
			dos.write(data, 0, data.length);
		}
	}

	@Override
	public void readPacketData(DataInputStream dis) throws IOException {
		type = TextureType.fromTypeText(dis.readUTF());
		name = dis.readUTF();
		int length = dis.readShort();
		if(length < 0){
			data = null;
		}else{
			data = new byte[length];
			dis.readFully(data);
		}
	}

	@Override
	public void handleData(INetworkManager manager) {
		if(data != null){
			File file = 
			new File(
				new File(
					new File(DecorationBlock.proxy.getBaseDir(), CommonProxy.DIR_CACHE_TEXTURE)
					, type.typeText()
				)
				, name
			);
			FileOutputStream fos = null;
			try{
				fos = new FileOutputStream(file);
				fos.write(data);
			}catch(Exception e){
			}finally{
				try{
					fos.close();
				}catch(Exception e2){
				}
			}
		}
	}
}
