package tschallacka.mods.whoneedsbooks.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;

public class CommandRunner 
{
	protected EntityPlayer player;

	protected int timer = 0;
	
	protected boolean debug;
	
	protected LinkedBlockingQueue<SpellCommandGroup> runners = new LinkedBlockingQueue <SpellCommandGroup>();
	
	public CommandRunner(EntityPlayer p, ItemStack itemstack, SpellBookInteractor bookInterActor) {
		this.player = p;
		this.debug = bookInterActor.getDebugMode(itemstack);
		this.processCommands(itemstack, bookInterActor);
	}
	
	protected void processCommands(ItemStack stack, SpellBookInteractor actor) 
	{
		this.handleCommand(actor.COMMAND1, actor.TIMEOUT1, stack, actor);
		this.handleCommand(actor.COMMAND2, actor.TIMEOUT2, stack, actor);
		this.handleCommand(actor.COMMAND3, actor.TIMEOUT3, stack, actor);
		this.handleCommand(actor.COMMAND4, actor.TIMEOUT4, stack, actor);
		this.handleCommand(actor.COMMAND5, actor.TIMEOUT5, stack, actor);
	}
	
	protected void handleCommand(String commandType, String timeoutType, ItemStack stack, SpellBookInteractor actor)
	{
		String command = actor.getCommandForType(stack, commandType);
		if(!command.trim().isEmpty()) {
			this.processCommand(command, actor.getTimeoutForType(stack, timeoutType));
		}
	}
	
	protected void processCommand(String commandlist, int delay) 
	{
		SpellCommandGroup group = new SpellCommandGroup(delay, player);
		group.setDebug(debug);
		String[] commands = commandlist.split("!c:");
		for(String commmand : commands) {
			group.addCommand(commmand);
		}
		this.runners.add(group);
	}
	
	public boolean hasRun() 
	{
		return runners.isEmpty();
	}
	
	public void run() 
	{
		Iterator<SpellCommandGroup> it = runners.iterator();
		while(it.hasNext()) {
			SpellCommandGroup spell = it.next();
			if(!spell.hasRun()) {
				spell.run();
			}
			else {
				it.remove();
			}
		}
	}
	
	class SpellCommandGroup implements ICommandSender
	{
		protected int delay;
		public final EntityPlayer player;
		protected LinkedBlockingQueue <String> commands;
		protected boolean debug; 
		
		public SpellCommandGroup(int delay, EntityPlayer p) 
		{
			this.delay = delay;
			this.player = p;
			commands = new LinkedBlockingQueue<String>();
		}
		
		public void setDebug(boolean debug) {
			this.debug = debug;
		}

		public void addCommand(String command) 
		{
			if(!command.trim().isEmpty()) {
				commands.add(command);
			}
		}
		
		public void run() {
			delay--;
			if(delay > 0) {
				return;
			}
			runCommands();
			
		}

		public boolean hasRun() 
		{
			return delay < 0;
		}
		
		protected void runCommands() {
			if(!this.getEntityWorld().isRemote) {
				while(true) {
					String command = commands.poll();
					if(command == null) {
						break;
					}
					this.runCommand(command);
				}
			}
		}
		
		protected void runCommand(String command) 
		{
			MinecraftServer minecraftserver = this.getServer();
			try
            {
                minecraftserver.getCommandManager().executeCommand(this, command);
            }
            catch (Throwable throwable)
            {
                WhoNeedsBooks.log("Error executing command "+command);
                WhoNeedsBooks.log("Error: "+throwable.getMessage());
            }
		}
		@Override 
		public BlockPos getPosition()
		{
			return player.getPosition();
		}
		
		@Override 
		public Vec3d getPositionVector() 
		{
			return player.getPositionVector();
		}
		
		@Override
		public Entity getCommandSenderEntity() 
		{
			return player;
		}
		
		@Override
		public boolean sendCommandFeedback() 
		{
			return debug;
		}
		
		@Override
		public String getName() {
			return player.getName();
		}

		@Override
		public boolean canUseCommand(int permLevel, String commandName) {
			return true;
		}

		@Override
		public World getEntityWorld() {
			return player.getEntityWorld();
		}

		@Override
		public MinecraftServer getServer() {
			return player.getServer();
		}
		
		@Override
	    public void sendMessage(ITextComponent component) {
			player.sendMessage(component);
	    }
	}
}
