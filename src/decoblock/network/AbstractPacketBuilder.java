package decoblock.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import decoblock.CommonProxy;

public abstract class AbstractPacketBuilder implements IPacketBuilder {
	private String subchannel;
	
	protected AbstractPacketBuilder(String subchannel)
	{
		this.subchannel = subchannel;
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
	public void writePacketData(DataOutputStream dos) throws IOException
	{
	}

	@Override
	public void readPacketData(DataInputStream dis) throws IOException
	{
	}

	@Override
	public void handleData(INetworkManager manager)
	{
	}
}
