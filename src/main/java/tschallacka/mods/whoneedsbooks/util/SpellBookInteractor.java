package tschallacka.mods.whoneedsbooks.util;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SpellBookInteractor {
	/**
	 * How much of the cooldown time is remaining
	 */
	public final static String COOLDOWN = "cooldown";
	/**
	 * How much cooldown to account for after use
	 */
	public final static String COOLDOWN_TICKS = "cooldown_ticks";
	
	public final static String DEBUG = "debug";
	
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
	
	public int getCooldown(ItemStack stack) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		return nbt.getInteger(COOLDOWN);
	}
	
	public boolean getDebugMode(ItemStack stack) 
	{
		return this.getNBT(stack).getBoolean(DEBUG);
	}
	
	public void toggleDebugMode(ItemStack stack) 
	{
		this.setDebugMode(stack, !this.getDebugMode(stack));
	}
	
	public void setDebugMode(ItemStack stack, boolean value) 
	{
		this.getNBT(stack).setBoolean(DEBUG, value);
	}
	public int getCooldownDuration(ItemStack stack) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		return nbt.getInteger(COOLDOWN_TICKS);
	}
	
	public void setCooldown(ItemStack stack, int updatedCooldown) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		nbt.setInteger(COOLDOWN, updatedCooldown);
	}
	
	public void setCooldownDuration(ItemStack stack, int ticks) 
	{
		NBTTagCompound nbt = this.getNBT(stack);
		nbt.setInteger(COOLDOWN_TICKS, ticks);
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
	        if(!nbt.hasKey(DEBUG)) nbt.setBoolean(DEBUG, false);
	    }
	    else
	    {
	        nbt = new NBTTagCompound();
	        stack.setTagCompound(nbt);
	        nbt = getNBT(stack);
	    }
	    return nbt;
	}
	
	
}
