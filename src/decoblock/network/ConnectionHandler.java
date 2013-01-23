/**
 * 
 */
package decoblock.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import decoblock.BlockDecoration;
import decoblock.CommonProxy;
import decoblock.DecorationBlock;
import decoblock.ModConfiguration;
import decoblock.ModItems;


public class ConnectionHandler implements IConnectionHandler {

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.network.IConnectionHandler#playerLoggedIn(cpw.mods.fml.common.network.Player, net.minecraft.network.packet.NetHandler, net.minecraft.network.INetworkManager)
	 */
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		// (Server side)
		// Send block parameters and texture list.
		Packet packet = new PacketBuilderInitClient().buildPacket();
		if(packet != null) manager.addToSendQueue(packet);
		new PacketBuilderRequestTextureList().handleData(manager);
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.network.IConnectionHandler#connectionReceived(net.minecraft.network.NetLoginHandler, net.minecraft.network.INetworkManager)
	 */
	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		// do nothing.
		return null;
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.network.IConnectionHandler#connectionOpened(net.minecraft.network.packet.NetHandler, java.lang.String, int, net.minecraft.network.INetworkManager)
	 */
	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
		// (Client side) called when remote connection is opened.
		// do nothing

	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.network.IConnectionHandler#connectionOpened(net.minecraft.network.packet.NetHandler, net.minecraft.server.MinecraftServer, net.minecraft.network.INetworkManager)
	 */
	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
		// (Client side) called when local connection is opened.
		// Setup block parameters to default and reset TextureManager.
		BlockDecoration decoration = (BlockDecoration)ModItems.decoration;
		decoration.setHardness(ModConfiguration.hardness);
		decoration.setHardnessCollidable(ModConfiguration.hardnessCollidable);
		DecorationBlock.proxy.resetTextureManager(CommonProxy.DEFAULT_CACHE_SUBDIR);
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.network.IConnectionHandler#connectionClosed(net.minecraft.network.INetworkManager)
	 */
	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub
		// (All sides) called when connection closes.

	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.network.IConnectionHandler#clientLoggedIn(net.minecraft.network.packet.NetHandler, net.minecraft.network.INetworkManager, net.minecraft.network.packet.Packet1Login)
	 */
	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
		// (Client side) called when client established the connection to server.
		// do nothing.

	}

}
