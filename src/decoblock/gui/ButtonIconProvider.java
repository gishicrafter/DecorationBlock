package decoblock.gui;

import java.util.EnumSet;

interface ButtonIconProvider
{
	Object[] getIconForButton(int id, EnumSet<ButtonState> state);
}