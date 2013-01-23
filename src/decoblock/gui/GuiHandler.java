package decoblock.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import decoblock.TileEntityDecoration;

public class GuiHandler implements IGuiHandler {
	
	public static final int ID_GUI_DECORATION_CONFIG = 1;
	
	public static final String GUI_DECORATION_CONTAINER = "/decoblock/gui/decorationcontainer.png";
	public static final String GUI_ITEMS = "/decoblock/gui/gui_items.png";

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch(ID){
		case ID_GUI_DECORATION_CONFIG:
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityDecoration){
				return new ContainerDecorationConfig(player.inventory, (TileEntityDecoration)tileEntity);
			}
			break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch(ID){
		case ID_GUI_DECORATION_CONFIG:
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityDecoration){
				return new GuiDecorationConfig(player.inventory, (TileEntityDecoration)tileEntity);
			}
			break;
		}
		return null;
	}

}
