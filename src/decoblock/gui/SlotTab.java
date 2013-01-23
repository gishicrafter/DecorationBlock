package decoblock.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotTab extends Slot {

	protected int parentTabPage = 0;
	
	protected final int xPositionMemory;
	protected final int yPositionMemory;
	
	public static final int POSITION_OUTOFSCREEN = -9999;
	
	public SlotTab(IInventory par1iInventory, int par2, int par3, int par4, int tab) {
		super(par1iInventory, par2, par3, par4);

		parentTabPage = tab;
		
		xPositionMemory = xDisplayPosition;
		yPositionMemory = yDisplayPosition;
	}

	public int getParentTab()
	{
		return parentTabPage;
	}
	
	public void onTabChange(int page)
	{
		if(page == parentTabPage){
			xDisplayPosition = xPositionMemory;
			yDisplayPosition = yPositionMemory;
		}else{
			xDisplayPosition = POSITION_OUTOFSCREEN;
			yDisplayPosition = POSITION_OUTOFSCREEN;
		}
	}
}
