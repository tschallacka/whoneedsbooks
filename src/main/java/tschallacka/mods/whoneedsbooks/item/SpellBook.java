package tschallacka.mods.whoneedsbooks.item;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
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
import tschallacka.mods.whoneedsbooks.util.CommandRunner;
import tschallacka.mods.whoneedsbooks.util.SpellBookInteractor;


public class SpellBook extends Item 
{
	private final SpellBookType type;
	private final ItemVariantGroup<SpellBookType, SpellBook> variantGroup;
	private final SpellBookInteractor interactor;
	public LinkedBlockingQueue<CommandRunner> runners = new LinkedBlockingQueue <CommandRunner>();
	
	public SpellBook(final SpellBookType type, final ItemVariantGroup<SpellBookType, SpellBook> variantGroup) 
	{
		this.type = type;
		this.variantGroup = variantGroup;
		this.interactor = new SpellBookInteractor();
		super.setMaxStackSize(1);
	}

	public SpellBookInteractor getInteractor() 
	{
		return this.interactor;
	}
	
	public String getCommand(ItemStack stack, String commandType) 
	{
		return this.interactor.getCommandForType(stack, commandType);
	}
	
	public int getTimeout(ItemStack stack, String timeoutType) 
	{
		return this.interactor.getTimeoutForType(stack, timeoutType);
	}
	
	/**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		int cooldown = this.interactor.getCooldown(stack);
		if(cooldown > 0) {
			this.interactor.setCooldown(stack, cooldown - 1);
		}
		if(!runners.isEmpty()) {
			Iterator<CommandRunner> it = runners.iterator();
			while(it.hasNext()) {
				CommandRunner spell = it.next();
				spell.run();
				if(spell.hasRun()) {
					it.remove();
				}
			}
		}
    }
		
	public int getCooldownDuration(ItemStack stack) 
	{
		
		int cooldown_ticks = this.interactor.getCooldownDuration(stack);
		/**
		 * If cooldown ticks are set return that value for cooldown.
		 */
		if(cooldown_ticks > 0) return cooldown_ticks;
		/**
		 * If not, return metadata index * minute ticks;
		 */
		return (stack.getMetadata() + 1) * 20 * 60;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(!(player.isCreative() && player.canUseCommandBlock())) {
			int current = this.interactor.getCooldown(stack);
			if(current <= 0) {
				int cooldown = this.getCooldownDuration(stack);
				this.interactor.setCooldown(stack, cooldown);
				if(!world.isRemote) {
					CommandRunner runner = new CommandRunner(player, stack, this.interactor);
					runner.run();
					if(!runner.hasRun()) {
						this.runners.add(runner);
					}
					
				}
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
		
		int cooldown = this.interactor.getCooldown(stack);
	    if (cooldown > 0)
	    {
	    	tooltip.add(I18n.format("tschallacka.whoneedsbooks.spellbook.tooltip.cooldown.time") + StringUtils.ticksToElapsedTime(cooldown) );
	    }
	    else {
	    	tooltip.add(I18n.format("tschallacka.whoneedsbooks.spellbook.tooltip.cooldown.complete"));
	    }
	}
}