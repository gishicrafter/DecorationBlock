package decoblock.network;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLLog;

import decoblock.DecorationBlock;
import decoblock.TileEntityDecoration;

public class PacketBuilderSetTexture extends AbstractPacketBuilder
{
	public static final String subchannel = "SetTexture";
	
	public String name;
	public int x;
	public int y;
	public int z;
	public int dimension;
	
	public PacketBuilderSetTexture()
	{
		super(subchannel);
	}
	
	public PacketBuilderSetTexture(String name, int x, int y, int z, int dimension)
	{
		super(subchannel);
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dimension;
	}

	@Override
	public void writePacketData(DataOutputStream dos) throws IOException {
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(z);
		dos.writeInt(dimension);
		dos.writeUTF(name);
	}

	@Override
	public void readPacketData(DataInputStream dis) throws IOException {
		x = dis.readInt();
		y = dis.readInt();
		z = dis.readInt();
		dimension = dis.readInt();
		name = dis.readUTF();
	}

	@Override
	public void handleData(INetworkManager manager) {
		World world;
		world = DecorationBlock.proxy.getWorld(dimension);
		
		TileEntityDecoration tileEntity = (TileEntityDecoration)(world.getBlockTileEntity(x, y, z));
		if(tileEntity != null){
			tileEntity.setTextureName(name);
		}else{
			FMLLog.warning("tileEntity = null");
		}
	}
}
