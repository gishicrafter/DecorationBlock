package decoblock.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiTabButton extends GuiButton {

	public boolean selected = false;
	
	public GuiTabButton(int par1, int par2, int par3, int par4,
			String par6Str) {
		super(par1, par2, par3, par4, 14, par6Str);
	}

	public GuiTabButton(int par1, int par2, int par3,
			String par6Str) {
		super(par1, par2, par3, 64, 14, par6Str);
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(this.drawButton){
			boolean isHovering = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
			int hoverState = this.getHoverState(isHovering);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture(GuiHandler.GUI_ITEMS));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int halfWidth = width/2;
			int renderHeight = height;
			if(selected){
				renderHeight += 3;
			}
			int x = 0;
			int y = 0;
			if(isHovering) y += 17;
			drawTexturedModalRect(xPosition, yPosition, x, y, width - halfWidth, renderHeight);
			drawTexturedModalRect(xPosition + width - halfWidth, yPosition, 256 - halfWidth, y, halfWidth, renderHeight);
			mouseDragged(par1Minecraft, par2, par3);
            int textColor = 14737632;

            if (!this.enabled)
            {
            	textColor = -6250336;
            }
            else if (this.field_82253_i)
            {
            	textColor = 16777120;
            }
            FontRenderer var4 = par1Minecraft.fontRenderer;
			this.drawCenteredString(var4, this.displayString, this.xPosition + halfWidth, this.yPosition + (this.height - 8) / 2, textColor);
		}
	}

}
