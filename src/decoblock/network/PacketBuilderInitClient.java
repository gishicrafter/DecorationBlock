package decoblock.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import decoblock.BlockDecoration;
import decoblock.CommonProxy;
import decoblock.DecorationBlock;
import decoblock.ModConfiguration;
import decoblock.ModItems;
import decoblock.TextureManager;

import net.minecraft.network.INetworkManager;

public class PacketBuilderInitClient extends AbstractPacketBuilder {
	
	public static final String subchannel = "InitCl";
	
	public String cacheSubDir = ModConfiguration.cacheSubDir;
	public float hardness = ModConfiguration.hardness;
	public float hardnessCollidable = ModConfiguration.hardnessCollidable;
	
	public PacketBuilderInitClient()
	{
		super(subchannel);
	}

	@Override
	public void writePacketData(DataOutputStream dos) throws IOException {
		dos.writeUTF(cacheSubDir);
		dos.writeFloat(hardness);
		dos.writeFloat(hardnessCollidable);
	}

	@Override
	public void readPacketData(DataInputStream dis) throws IOException {
		cacheSubDir = dis.readUTF();
		hardness = dis.readFloat();
		hardnessCollidable = dis.readFloat();
	}

	@Override
	public void handleData(INetworkManager manager) {
		DecorationBlock.proxy.resetTextureManager(cacheSubDir);
		BlockDecoration decoration = (BlockDecoration)ModItems.decoration;
		decoration.setHardness(hardness);
		decoration.setHardnessCollidable(hardnessCollidable);
	}

}
