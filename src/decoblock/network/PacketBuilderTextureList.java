package decoblock.network;

import decoblock.*;

import java.util.ArrayList;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;

public class PacketBuilderTextureList extends AbstractPacketBuilder
{
	public static final String subchannel = "Texturelist";
	
	private ArrayList<TextureType> typeList = new ArrayList();
	private ArrayList<String> nameList = new ArrayList();
	private int bytesCount;
	
	public PacketBuilderTextureList()
	{
		super(subchannel);
		bytesCount = 0;
	}
	
	public int size()
	{
		return bytesCount;
	}
	
	public boolean addTexture(TextureType type, String name)
	{
		String full = type.typeText() + "/" + name;
		int bytesCountNew;
		try{
			bytesCountNew = bytesCount + 2 + name.getBytes("UTF8").length;
			if(bytesCountNew > 32000){
				return false;
			}
			typeList.add(type);
			nameList.add(name);
			bytesCount = bytesCountNew;
			return true;
		}catch(Exception e){
			FMLLog.getLogger().throwing("PacketBuilderTextureList", "addTexture", e);
			FMLCommonHandler.instance().raiseException(e, "Exception occured in PacketBuilderTextureList", true);
		}
		return false;
	}
	
	TextureType[] getTypeList()
	{
		TextureType[] result = new TextureType[typeList.size()];
		return typeList.toArray(result);
	}
	
	String[] getNameList()
	{
		String[] result = new String[nameList.size()];
		return nameList.toArray(result);
	}

	@Override
	public void writePacketData(DataOutputStream dos) throws IOException {
		int size = typeList.size();
		for(int i=0; i<size; ++i){
			String full = typeList.get(i).typeText() + "/" + nameList.get(i);
			dos.writeUTF(full);
		}
	}

	@Override
	public void readPacketData(DataInputStream dis) throws IOException {
		while(dis.available()>0){
			String full[] = dis.readUTF().split("/",2);
			TextureType type = TextureType.fromTypeText(full[0]);
			String name = full[1];
			addTexture(type, name);
		}
	}

	@Override
	public void handleData(INetworkManager manager) {
		TextureType[] typeList = getTypeList();
		String[] nameList = getNameList();
		
		for(int i = 0; i < nameList.length; ++i){
			TextureManager.instance.addTexture(typeList[i], nameList[i]);
		}
	}
}
