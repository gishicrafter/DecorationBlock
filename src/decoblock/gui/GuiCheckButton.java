package decoblock.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;
import java.util.EnumSet;

public class GuiCheckButton extends GuiButton
{
	protected ButtonIconProvider iconProvider;
	
	
	protected boolean pressed;
	public boolean checked;
	
	public GuiCheckButton(int par1, int par2, int par3, String par4Str, ButtonIconProvider provider)
	{
		this(par1, par2, par3, 16, 16, par4Str, provider);
	}

	public GuiCheckButton(int par1, int par2, int par3, int par4, int par5, String par6Str, ButtonIconProvider provider)
	{
		super(par1, par2, par3, par4, par5, par6Str);
		iconProvider = provider;
	}

	/**
	 * Draws this button to the screen.
	*/
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if (this.drawButton)
		{
			boolean var5 = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			int var6 = this.getHoverState(var5);
			
			EnumSet<ButtonState> state = EnumSet.noneOf(ButtonState.class);
			if(pressed){
				state.add(ButtonState.PRESSED);
			}
			switch(var6){
			case 0: // disabled
				break;
			case 1: // enabled
				state.add(ButtonState.ENABLED);
				break;
			case 2: // enabled, hover
				state.add(ButtonState.ENABLED);
				state.add(ButtonState.HOVERING);
				break;
			}
			if(checked){
				state.add(ButtonState.CHECKED);
			}
			Object[] icon = iconProvider.getIconForButton(id, state);
			
			String textureFile = (String)(icon[0]);
			int x = (Integer)(icon[1]);
			int y = (Integer)(icon[2]);
			
			//FontRenderer var4 = par1Minecraft.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture(textureFile));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(xPosition, yPosition, x, y, width, height);
			mouseDragged(par1Minecraft, par2, par3);

		}
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {}

	/**
	 * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
	 */
	@Override
	public void mouseReleased(int par1, int par2) 
	{
		pressed = false;
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
	 * e).
	 */
	@Override
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
	{
		pressed = super.mousePressed(par1Minecraft, par2, par3);
		if(pressed) checked = !checked;
		return pressed;
    }
}
