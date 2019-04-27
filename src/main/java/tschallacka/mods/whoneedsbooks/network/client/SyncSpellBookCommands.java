package tschallacka.mods.whoneedsbooks.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.relauncher.Side;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;
import tschallacka.mods.whoneedsbooks.item.SpellBook;
import tschallacka.mods.whoneedsbooks.network.AbstractMessage.AbstractServerMessage;
import tschallacka.mods.whoneedsbooks.util.SpellBookInteractor;

public class SyncSpellBookCommands extends AbstractServerMessage<SyncSpellBookCommands>
{
	protected String command1, command2, command3, command4, command5;
	protected int timeout1, timeout2, timeout3, timeout4, timeout5;
	protected int cooldownDuration;
	protected boolean debugMode;
	// The basic, no-argument constructor MUST be included to use the new automated handling
	public SyncSpellBookCommands() {}
	
	// if there are any class fields, be sure to provide a constructor that allows
	// for them to be initialized, and use that constructor when sending the packet
	public SyncSpellBookCommands(ItemStack stack) 
	{
		SpellBookInteractor spellBook;
		if(stack.getItem() instanceof SpellBook) {
        	spellBook = ((SpellBook)stack.getItem()).getInteractor();
        }
        else {
        	throw new RuntimeException("Tried to create spellbook update packet with a non spellbook itemstack. Sorry for the crash. Class of item provided = "+stack.getItem().getClass().getCanonicalName());
        }
		command1 = spellBook.getCommandForType(stack, spellBook.COMMAND1);
		command2 = spellBook.getCommandForType(stack, spellBook.COMMAND2);
		command3 = spellBook.getCommandForType(stack, spellBook.COMMAND3);
		command4 = spellBook.getCommandForType(stack, spellBook.COMMAND4);
		command5 = spellBook.getCommandForType(stack, spellBook.COMMAND5);
		timeout1 = spellBook.getTimeoutForType(stack,  spellBook.TIMEOUT1);
		timeout2 = spellBook.getTimeoutForType(stack,  spellBook.TIMEOUT2);
		timeout3 = spellBook.getTimeoutForType(stack,  spellBook.TIMEOUT3);
		timeout4 = spellBook.getTimeoutForType(stack,  spellBook.TIMEOUT4);
		timeout5 = spellBook.getTimeoutForType(stack,  spellBook.TIMEOUT5);
		cooldownDuration = spellBook.getCooldownDuration(stack);
		debugMode = spellBook.getDebugMode(stack);
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException 
	{
		int maxLength = 32500;
		this.command1 = buffer.readString(maxLength);
		this.command2 = buffer.readString(maxLength);
		this.command3 = buffer.readString(maxLength);
		this.command4 = buffer.readString(maxLength);
		this.command5 = buffer.readString(maxLength);
		this.timeout1 = buffer.readInt();
		this.timeout2 = buffer.readInt();
		this.timeout3 = buffer.readInt();
		this.timeout4 = buffer.readInt();
		this.timeout5 = buffer.readInt();
		this.cooldownDuration = buffer.readInt();
		this.debugMode = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException 
	{
		
		buffer.writeString(command1);
		buffer.writeString(command2);
		buffer.writeString(command3);
		buffer.writeString(command4);
		buffer.writeString(command5);
		buffer.writeInt(timeout1);
		buffer.writeInt(timeout2);
		buffer.writeInt(timeout3);
		buffer.writeInt(timeout4);
		buffer.writeInt(timeout5);
		buffer.writeInt(cooldownDuration);
		buffer.writeBoolean(debugMode);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(side == Side.SERVER) {
			if(player.isCreative() && player.canUseCommandBlock()) {
				ItemStack stack = player.getHeldItem(player.getActiveHand());
				WhoNeedsBooks.log("Going to write stuff");
				WhoNeedsBooks.log(stack.getItem().getClass().getCanonicalName());
				if(stack.getItem() instanceof SpellBook) {
					WhoNeedsBooks.log("Going to write stuff");
					SpellBookInteractor spellBook = ((SpellBook)stack.getItem()).getInteractor();
					spellBook.setCommandForType(stack, spellBook.COMMAND1, command1);
					spellBook.setCommandForType(stack, spellBook.COMMAND2, command2);
					spellBook.setCommandForType(stack, spellBook.COMMAND3, command3);
					spellBook.setCommandForType(stack, spellBook.COMMAND4, command4);
					spellBook.setCommandForType(stack, spellBook.COMMAND5, command5);
					spellBook.setTimeoutForType(stack, spellBook.TIMEOUT1, timeout1);
					spellBook.setTimeoutForType(stack, spellBook.TIMEOUT2, timeout2);
					spellBook.setTimeoutForType(stack, spellBook.TIMEOUT3, timeout3);
					spellBook.setTimeoutForType(stack, spellBook.TIMEOUT4, timeout4);
					spellBook.setTimeoutForType(stack, spellBook.TIMEOUT5, timeout5);
					spellBook.setCooldownDuration(stack, cooldownDuration);
					spellBook.setDebugMode(stack, debugMode);
				}
			}
			else {
				WhoNeedsBooks.log("Something's fucky. " + player.getDisplayNameString() + " tried to set spellbook commands but isn't allowed to. HACKZ! Anyways, he was set on fire for 3600 seconds and transported to hell... magic has consequenses");
				player.changeDimension(DimensionType.NETHER.getId());
				player.setFire(3600);
				player.sendMessage(new TextComponentTranslation("tschallacka.whoneedsbooks.spell.setting.bounceback"));
			}
		}		
	}

}
