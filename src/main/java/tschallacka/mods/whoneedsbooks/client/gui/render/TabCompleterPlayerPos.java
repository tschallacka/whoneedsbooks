package tschallacka.mods.whoneedsbooks.client.gui.render;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.math.BlockPos;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;

public class TabCompleterPlayerPos extends TabCompleter 
{
	 public TabCompleterPlayerPos(GuiTextField textFieldIn, boolean hasTargetBlockIn) 
	 {
		super(textFieldIn, hasTargetBlockIn);
	}

	 @Nullable
     public BlockPos getTargetBlockPos()
     {
         return WhoNeedsBooks.proxy.getClientPlayer().getPosition();
     }
}
