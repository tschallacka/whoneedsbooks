package tschallacka.mods.whoneedsbooks.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschallacka.mods.whoneedsbooks.item.type.SpellBookType;
import tschallacka.mods.whoneedsbooks.item.type.variant.ItemVariantGroup;

public class SpellBook extends Item 
{
	private final SpellBookType type;
	private final ItemVariantGroup<SpellBookType, SpellBook> variantGroup;
	
	public SpellBook(final SpellBookType type, final ItemVariantGroup<SpellBookType, SpellBook> variantGroup) 
	{
		this.type = type;
		this.variantGroup = variantGroup;
		super.setMaxStackSize(1);
	}

	
	/**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		NBTTagCompound nbt = this.getNBT(stack);
		int cooldown = nbt.getInteger("cooldown");
		if(cooldown > 0) {
			nbt.setInteger("cooldown", cooldown - 1);
		}
	    
    }
	
	/**
	 * How much of the cooldown time is remaining
	 */
	private final String COOLDOWN = "cooldown";
	/**
	 * How much cooldown to account for after use
	 */
	private final String COOLDOWN_TICKS = "cooldown_ticks";
	
	protected NBTTagCompound getNBT(ItemStack stack) 
	{
		NBTTagCompound nbt;
	    if (stack.hasTagCompound())
	    {
	        nbt = stack.getTagCompound();
	        /**
	         * Set defaults in case somebody spawned a book in the world with a command
	         * lacking some default values.
	         */
	        if(!nbt.hasKey(COOLDOWN)) nbt.setInteger(COOLDOWN, 0);
	        if(!nbt.hasKey(COOLDOWN_TICKS)) nbt.setInteger(COOLDOWN_TICKS, 0);
	    }
	    else
	    {
	        nbt = new NBTTagCompound();
	        nbt.setInteger(COOLDOWN, 0);
	        nbt.setInteger(COOLDOWN_TICKS, 0);
	        stack.setTagCompound(nbt);
	    }
	    return nbt;
	}
	
	protected int getCooldown(ItemStack stack) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		int cooldown_ticks = nbt.getInteger(COOLDOWN_TICKS);
		/**
		 * If cooldown ticks are set return that value for cooldown.
		 */
		if(cooldown_ticks > 0) return cooldown_ticks;
		/**
		 * If not, return metadata index * minute ticks;
		 */
		return stack.getMetadata() + 1 * 20 * 60;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound nbt = this.getNBT(stack);
		int current = nbt.getInteger(COOLDOWN);
		if(current <= 0) {
			int cooldown = this.getCooldown(stack);
			nbt.setInteger(COOLDOWN, cooldown);		
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		}
		else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		}
	}
	
	/**
     * allows items to add custom lines of information to the mouseover description
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		int cooldown = nbt.getInteger(COOLDOWN);
	    if (cooldown > 0)
	    {
	    	int seconds = (cooldown / 20) % 60;
	    	int minutes = cooldown / 20 / 60;
	    	tooltip.add("Remaining recharge time " + minutes + ":" + seconds );
	    }
	    else {
	    	tooltip.add("Vibrating with mysterious energy");
	    }
	}
}