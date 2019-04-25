package tschallacka.mods.whoneedsbooks.client.gui.provider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tschallacka.mods.whoneedsbooks.ItemLibrary;
import tschallacka.mods.whoneedsbooks.client.gui.GuiContainerFactory;
import tschallacka.mods.whoneedsbooks.client.gui.GuiType;
import tschallacka.mods.whoneedsbooks.client.gui.render.GuiSpellBook;

public class SpellBookGuiProvider implements GuiContainerFactory {

	@Override
	public boolean matches(int ID, EntityPlayer player, Entity entity, TileEntity tileEntity, World world, int x, int y, int z) {
		if(player != null) {
			ItemStack stack = player.getHeldItemMainhand();
			if(ItemLibrary.VariantGroups.SPELLBOOKS.getItems().contains(stack.getItem())) {
				return ID == GuiType.SPELLBOOK_GUI.getID();
			}
		}
		return false;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, Entity entity,TileEntity tileEntity, World world, int x, int y, int z) {
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, Entity entity,TileEntity tileEntity, World world, int x, int y, int z) {
		return new GuiSpellBook(player.getHeldItemMainhand());
	}

}
