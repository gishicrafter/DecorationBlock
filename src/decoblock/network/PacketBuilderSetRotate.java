package decoblock.network;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLLog;

import decoblock.DecorationBlock;
import decoblock.TileEntityDecoration;

public class PacketBuilderSetRotate extends AbstractPacketBuilder
{
	public static final String subchannel = "SetRotate";
	
	public int rotate;
	public int x;
	public int y;
	public int z;
	public int dimension;
	
	public PacketBuilderSetRotate()
	{
		super(subchannel);
	}
	
	public PacketBuilderSetRotate(int rotate, int x, int y, int z, int dimension)
	{
		super(subchannel);
		this.rotate = rotate;
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
		dos.writeByte(rotate);
	}

	@Override
	public void readPacketData(DataInputStream dis) throws IOException {
		x = dis.readInt();
		y = dis.readInt();
		z = dis.readInt();
		dimension = dis.readInt();
		rotate = dis.readByte();
	}

	@Override
	public void handleData(INetworkManager manager) {
		World world;

		world = DecorationBlock.proxy.getWorld(dimension);
		
		TileEntityDecoration tileEntity = (TileEntityDecoration)(world.getBlockTileEntity(x, y, z));
		if(tileEntity != null){
			tileEntity.setRotate(rotate);
		}else{
			FMLLog.warning("tileEntity = null");
		}
	}
}
