package tschallacka.mods.whoneedsbooks.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;

public class CreativeTab extends CreativeTabs {
	private final ItemStack bookIcon;

	public CreativeTab() {
		super(WhoNeedsBooks.MODID);
		bookIcon = new ItemStack(Items.KNOWLEDGE_BOOK);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(final NonNullList<ItemStack> items) {
		super.displayAllRelevantItems(items);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return bookIcon;
	}
}