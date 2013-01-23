package decoblock.network;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLLog;

import decoblock.DecorationBlock;
import decoblock.TileEntityDecoration;

public class PacketBuilderTileEntityData extends AbstractPacketBuilder
{
	public static final String subchannel = "TEData";
	
	public int x;
	public int y;
	public int z;
	public int dimension;
	public int mode;
	public NBTTagCompound tag;
	
	public PacketBuilderTileEntityData()
	{
		super(subchannel);
	}
	
	public PacketBuilderTileEntityData(int x, int y, int z, int dimension, int mode, NBTTagCompound tag)
	{
		super(subchannel);
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dimension;
		this.mode = mode;
		this.tag = tag;
	}
	
	protected static void writeNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutputStream par1DataOutputStream) throws IOException
	{
		if (par0NBTTagCompound == null)
		{
			par1DataOutputStream.writeShort(-1);
		}
		else
		{
			byte[] var2 = CompressedStreamTools.compress(par0NBTTagCompound);
			par1DataOutputStream.writeShort((short)var2.length);
		par1DataOutputStream.write(var2);
		}
	}

	@Override
	public void writePacketData(DataOutputStream dos) throws IOException {
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(z);
		dos.writeInt(dimension);
		dos.writeInt(mode);
		writeNBTTagCompound(tag, dos);
	}

	@Override
	public void readPacketData(DataInputStream dis) throws IOException {
		x = dis.readInt();
		y = dis.readInt();
		z = dis.readInt();
		dimension = dis.readInt();
		mode = dis.readInt();
		tag = Packet.readNBTTagCompound(dis);
	}

	@Override
	public void handleData(INetworkManager manager) {
		World world = DecorationBlock.proxy.getClientWorld();
		if(world == null) return;
		TileEntityDecoration tileEntity = (TileEntityDecoration)(world.getBlockTileEntity(x, y, z));
		if(tileEntity != null){
			tileEntity.readFromNBT(tag);
		}else{
			FMLLog.warning("tileEntity = null");
		}
	}
}
