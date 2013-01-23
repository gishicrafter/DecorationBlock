package decoblock.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;

interface IPacketBuilder
{
	public void writePacketData(DataOutputStream dos) throws IOException;
	public void readPacketData(DataInputStream dis) throws IOException;
	public Packet buildPacket();
	public void handleData(INetworkManager manager);
}
