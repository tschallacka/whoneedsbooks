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
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;
import tschallacka.mods.whoneedsbooks.client.gui.GuiType;
import tschallacka.mods.whoneedsbooks.item.type.SpellBookType;
import tschallacka.mods.whoneedsbooks.item.type.variant.ItemVariantGroup;

public class SpellBook extends Item 
{
	private final SpellBookType type;
	private final ItemVariantGroup<SpellBookType, SpellBook> variantGroup;
	
	/**
	 * How much of the cooldown time is remaining
	 */
	public final static String COOLDOWN = "cooldown";
	/**
	 * How much cooldown to account for after use
	 */
	public final static String COOLDOWN_TICKS = "cooldown_ticks";
	
	/**
	 * First command line
	 */
	public final static String COMMAND1 = "c1";
	/**
	 * First command timeout before activation
	 */
	public final static String TIMEOUT1 = "t1";
	/**
	 * Second command line
	 */
	public final static String COMMAND2 = "c2";
	/**
	 * Second command timeout before activation
	 */
	public final static String TIMEOUT2 = "t2";
	/**
	 * Third command line
	 */
	public final static String COMMAND3 = "c3";
	/**
	 * Third command timeout before activation
	 */
	public final static String TIMEOUT3 = "t3";
	/**
	 * Fourth command line
	 */
	public final static String COMMAND4 = "c4";
	/**
	 * Fourth command timeout before activation
	 */
	public final static String TIMEOUT4 = "t4";
	/**
	 * Fifth command line
	 */
	public final static String COMMAND5 = "c5";
	/**
	 * Fifth command timeout before activation
	 */
	public final static String TIMEOUT5 = "t5";
	
	public SpellBook(final SpellBookType type, final ItemVariantGroup<SpellBookType, SpellBook> variantGroup) 
	{
		this.type = type;
		this.variantGroup = variantGroup;
		super.setMaxStackSize(1);
	}

	/**
	 * Get the command string for the provided slot
	 * @param stack
	 * @param commandType SpellBook.COMMAND1
	 * @return the command
	 */
	public String getCommandForType(ItemStack stack, String commandType) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		return nbt.getString(commandType);
	}
	/**
	 * Get the timeout in ticks for the provided slot
	 * @param stack
	 * @param commandType SpellBook.TIMEOUT1
	 * @return the timeout in ticks
	 */
	public int getTimeoutForType(ItemStack stack, String timeoutType) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		return nbt.getInteger(timeoutType);
	}
	
	/**
	 * Get the command string for the provided slot
	 * @param stack
	 * @param commandType SpellBook.COMMAND1
	 * @return the command
	 */
	public void setCommandForType(ItemStack stack, String commandType, String command) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		nbt.setString(commandType, command);
	}
	/**
	 * Get the timeout in ticks for the provided slot
	 * @param stack
	 * @param commandType SpellBook.TIMEOUT1
	 * @return the timeout in ticks
	 */
	public void setTimeoutForType(ItemStack stack, String timeoutType, int timeout) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		nbt.setInteger(timeoutType, timeout);
	}
	
	public String getCommand(ItemStack stack, String commandType) 
	{
		return this.getCommandForType(stack, commandType);
	}
	
	public int getTimeout(ItemStack stack, String timeoutType) 
	{
		return this.getTimeoutForType(stack, timeoutType);
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
	
	
	
	
	public NBTTagCompound getNBT(ItemStack stack) 
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
	        if(!nbt.hasKey(COMMAND1)) nbt.setString(COMMAND1, "");
	        if(!nbt.hasKey(COMMAND2)) nbt.setString(COMMAND2, "");
	        if(!nbt.hasKey(COMMAND3)) nbt.setString(COMMAND3, "");
	        if(!nbt.hasKey(COMMAND4)) nbt.setString(COMMAND4, "");
	        if(!nbt.hasKey(COMMAND5)) nbt.setString(COMMAND5, "");
	        if(!nbt.hasKey(TIMEOUT1)) nbt.setInteger(TIMEOUT1, 0);
	        if(!nbt.hasKey(TIMEOUT2)) nbt.setInteger(TIMEOUT2, 1);
	        if(!nbt.hasKey(TIMEOUT3)) nbt.setInteger(TIMEOUT3, 2);
	        if(!nbt.hasKey(TIMEOUT4)) nbt.setInteger(TIMEOUT4, 3);
	        if(!nbt.hasKey(TIMEOUT5)) nbt.setInteger(TIMEOUT5, 4);
	    }
	    else
	    {
	        nbt = new NBTTagCompound();
	        stack.setTagCompound(nbt);
	        nbt = getNBT(stack);
	    }
	    return nbt;
	}
	
	public int getCooldownDuration(ItemStack stack) 
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
	
	public void setCoolDown(ItemStack stack, int cooldown) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		nbt.setInteger(COOLDOWN, cooldown);		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(!player.isCreative()) {
			NBTTagCompound nbt = this.getNBT(stack);
			int current = nbt.getInteger(COOLDOWN);
			if(current <= 0) {
				int cooldown = this.getCooldownDuration(stack);
				this.setCoolDown(stack, cooldown);	
				return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
			}
			else {
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
			}
		}
		else {
			player.openGui(WhoNeedsBooks.MODID, GuiType.SPELLBOOK_GUI.getID(), world, 0, 0, 0);
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
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
	    	tooltip.add("Remaining recharge time " + StringUtils.ticksToElapsedTime(cooldown) );
	    }
	    else {
	    	tooltip.add("Vibrating with mysterious energy");
	    }
	}
}