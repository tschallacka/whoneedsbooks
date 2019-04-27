package tschallacka.mods.whoneedsbooks.client.gui.render;

import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ITabCompleter;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;
import tschallacka.mods.whoneedsbooks.item.SpellBook;
import tschallacka.mods.whoneedsbooks.item.type.SpellBookType;
import tschallacka.mods.whoneedsbooks.network.PacketDispatcher;
import tschallacka.mods.whoneedsbooks.network.client.SyncSpellBookCommands;
import tschallacka.mods.whoneedsbooks.util.SpellBookInteractor;

import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiSpellBook extends GuiScreen implements ITabCompleter
{
    /** Text field containing the command block's command. */
    private GuiTextField commandTextField1;
    private GuiTextField commandTextField2;
    private GuiTextField commandTextField3;
    private GuiTextField commandTextField4;
    private GuiTextField commandTextField5;
    private GuiTextField timeoutTextField1;
    private GuiTextField timeoutTextField2;
    private GuiTextField timeoutTextField3;
    private GuiTextField timeoutTextField4;
    private GuiTextField timeoutTextField5;
    private GuiTextField cooldownTextField;
    private GuiButton debugButton; 
    private final ItemStack stack;
    private final SpellBookInteractor spellBook;
    /** "Done" button for the GUI. */
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    private TabCompleter tabCompleter1;
    private TabCompleter tabCompleter2;
    private TabCompleter tabCompleter3;
    private TabCompleter tabCompleter4;
    private TabCompleter tabCompleter5;
    
    private ArrayList<GuiTextField> tabIndex = new ArrayList<GuiTextField>(11);
    
    private final int DONE = 0;
    private final int CANCEL = 1;
    private final int DEBUG = 2;

    public GuiSpellBook(ItemStack spellbook)
    {
        this.stack = spellbook;
        if(stack.getItem() instanceof SpellBook) {
        	spellBook = ((SpellBook)stack.getItem()).getInteractor();
        }
        else {
        	throw new RuntimeException("Tried to open SpellBook GUI with a non spellbook itemstack. Sorry for the crash. Class of item provided = "+spellbook.getItem().getClass().getCanonicalName());
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.commandTextField1.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.doneBtn = this.addButton(new GuiButton(DONE, this.width / 2 - 4 - 150, this.height / 4 + 130 + 12, 100, 20, I18n.format("gui.done")));
        this.cancelBtn = this.addButton(new GuiButton(CANCEL, this.width / 2 - 50, this.height / 4 + 130 + 12, 100, 20, I18n.format("gui.cancel")));
        this.debugButton = this.addButton(new GuiButton(DEBUG, this.width / 2 + 55, this.height / 4 + 130 + 12, 100, 20, this.getDebugButtonString(stack)));
        this.cooldownTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 175, 25, 50, 20);
        this.cooldownTextField.setMaxStringLength(9);
        this.cooldownTextField.setText(""+this.spellBook.getCooldownDuration(stack));
        this.tabIndex.add(cooldownTextField);
        
        this.commandTextField1 = new GuiTextField(2, this.fontRenderer, this.width / 2 - 175, 60, 300, 20);
        this.commandTextField1.setMaxStringLength(32500);
        this.commandTextField1.setFocused(true);
        this.commandTextField1.setText(this.spellBook.getCommandForType(stack, SpellBookInteractor.COMMAND1));
        this.tabIndex.add(commandTextField1);
        
        this.timeoutTextField1 = new GuiTextField(2, this.fontRenderer, this.width / 2 + 150, 60, 50, 20);
        this.timeoutTextField1.setMaxStringLength(9);
        this.timeoutTextField1.setText(""+this.spellBook.getTimeoutForType(stack, SpellBookInteractor.TIMEOUT1));
        this.tabIndex.add(timeoutTextField1);
        
        this.commandTextField2 = new GuiTextField(2, this.fontRenderer, this.width / 2 - 175, 85, 300, 20);
        this.commandTextField2.setMaxStringLength(32500);
        this.commandTextField2.setText(this.spellBook.getCommandForType(stack, SpellBookInteractor.COMMAND2));
        this.tabIndex.add(commandTextField2);
        
        this.timeoutTextField2 = new GuiTextField(2, this.fontRenderer, this.width / 2 + 150, 85, 50, 20);
        this.timeoutTextField2.setMaxStringLength(9);
        this.timeoutTextField2.setText(""+this.spellBook.getTimeoutForType(stack, SpellBookInteractor.TIMEOUT2));
        this.tabIndex.add(timeoutTextField2);
        
        this.commandTextField3 = new GuiTextField(2, this.fontRenderer, this.width / 2 - 175, 110, 300, 20);
        this.commandTextField3.setMaxStringLength(32500);
        this.commandTextField3.setText(this.spellBook.getCommandForType(stack, SpellBookInteractor.COMMAND3));
        this.tabIndex.add(commandTextField3);
        
        this.timeoutTextField3 = new GuiTextField(2, this.fontRenderer, this.width / 2 + 150, 110, 50, 20);
        this.timeoutTextField3.setMaxStringLength(9);
        this.timeoutTextField3.setText(""+this.spellBook.getTimeoutForType(stack, SpellBookInteractor.TIMEOUT3));
        this.tabIndex.add(timeoutTextField3);
        
        this.commandTextField4 = new GuiTextField(2, this.fontRenderer, this.width / 2 - 175, 135, 300, 20);
        this.commandTextField4.setMaxStringLength(32500);
        this.commandTextField4.setText(this.spellBook.getCommandForType(stack, SpellBookInteractor.COMMAND4));
        this.tabIndex.add(commandTextField4);
        
        this.timeoutTextField4 = new GuiTextField(2, this.fontRenderer, this.width / 2 + 150, 135, 50, 20);
        this.timeoutTextField4.setMaxStringLength(9);
        this.timeoutTextField4.setText(""+this.spellBook.getTimeoutForType(stack, SpellBookInteractor.TIMEOUT4));
        this.tabIndex.add(timeoutTextField4);
        
        this.commandTextField5 = new GuiTextField(2, this.fontRenderer, this.width / 2 - 175, 160, 300, 20);
        this.commandTextField5.setMaxStringLength(32500); 
        this.commandTextField5.setText(this.spellBook.getCommandForType(stack, SpellBookInteractor.COMMAND5));
        this.tabIndex.add(commandTextField5);
        
        this.timeoutTextField5 = new GuiTextField(2, this.fontRenderer, this.width / 2 + 150, 160, 50, 20);
        this.timeoutTextField5.setMaxStringLength(9); 
        this.timeoutTextField5.setText(""+this.spellBook.getTimeoutForType(stack, SpellBookInteractor.TIMEOUT5));
        this.tabIndex.add(timeoutTextField5);
        
        this.tabCompleter1 = new TabCompleterPlayerPos(this.commandTextField1, false);
        this.tabCompleter2 = new TabCompleterPlayerPos(this.commandTextField2, false);
        this.tabCompleter3 = new TabCompleterPlayerPos(this.commandTextField3, false);
        this.tabCompleter4 = new TabCompleterPlayerPos(this.commandTextField4, false);
        this.tabCompleter5 = new TabCompleterPlayerPos(this.commandTextField5, false);
        
        this.doneBtn.enabled = true;   
    }

    public void updateGui()
    {
        
        this.doneBtn.enabled = true;

    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {

            if (button.id == CANCEL)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (button.id == DONE)
            {
            	this.spellBook.setCommandForType(stack, SpellBookInteractor.COMMAND1, this.commandTextField1.getText());
            	this.spellBook.setCommandForType(stack, SpellBookInteractor.COMMAND2, this.commandTextField2.getText());
            	this.spellBook.setCommandForType(stack, SpellBookInteractor.COMMAND3, this.commandTextField3.getText());
            	this.spellBook.setCommandForType(stack, SpellBookInteractor.COMMAND4, this.commandTextField4.getText());
            	this.spellBook.setCommandForType(stack, SpellBookInteractor.COMMAND5, this.commandTextField5.getText());
            	this.spellBook.setTimeoutForType(stack, SpellBookInteractor.TIMEOUT1, getIntegerFromString(this.timeoutTextField1.getText()));
            	this.spellBook.setTimeoutForType(stack, SpellBookInteractor.TIMEOUT2, getIntegerFromString(this.timeoutTextField2.getText()));
            	this.spellBook.setTimeoutForType(stack, SpellBookInteractor.TIMEOUT3, getIntegerFromString(this.timeoutTextField3.getText()));
            	this.spellBook.setTimeoutForType(stack, SpellBookInteractor.TIMEOUT4, getIntegerFromString(this.timeoutTextField4.getText()));
            	this.spellBook.setTimeoutForType(stack, SpellBookInteractor.TIMEOUT5, getIntegerFromString(this.timeoutTextField5.getText()));
            	this.spellBook.setCooldownDuration(stack, getIntegerFromString(this.cooldownTextField.getText()));
            	PacketDispatcher.sendToServer(new SyncSpellBookCommands(this.stack));
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if(button.id == DEBUG) {
            	this.spellBook.toggleDebugMode(stack);
            	button.displayString = this.getDebugButtonString(stack);
            }
        }
    }
    
    protected String getDebugButtonString(ItemStack stack) 
    {
    	return this.spellBook.getDebugMode(stack) ? I18n.format("tschallacka.whoneedsbooks.spellbook.gui.disable.debug") : I18n.format("tschallacka.whoneedsbooks.spellbook.gui.enable.debug");
    }
    protected int getIntegerFromString(String input) {
    	if(input.isEmpty()) {
    		return 0;
    	}
    	if(input.length() > 9) {
    		return 0;
    	}
    	try {
    		int number = Integer.parseInt(input);
    		if(number < 0) {
    			return 0;
    		}
    		return number;
    	}
    	catch(NumberFormatException ex) {
    		return 0;
    	}
    }

    protected void changeTabIndex() 
    {
    	for(int c = 0; c < this.tabIndex.size(); c++) {
    		if(this.tabIndex.get(c).isFocused()) {
    			this.tabIndex.get(c).setFocused(false);
    			if(this.isShiftKeyDown()) {
    				if(c > 0) {
    					this.tabIndex.get(c-1).setFocused(true);
    				}
    				else {
    					this.tabIndex.get(this.tabIndex.size()-1).setFocused(true);
    				}
    			}
    			else {
    				if(c < this.tabIndex.size()-1) {
    					this.tabIndex.get(c+1).setFocused(true);
    				}
    				else {
    					this.tabIndex.get(0).setFocused(true);
    				}
    			}
    			break;
    		}
    	}
    }
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.tabCompleter1.resetRequested();
        this.tabCompleter2.resetRequested();
        this.tabCompleter3.resetRequested();
        this.tabCompleter4.resetRequested();
        this.tabCompleter5.resetRequested();

        if (keyCode == Keyboard.KEY_TAB)
        {
        	if(this.commandTextField1.isFocused()) {
        		this.tabCompleter1.complete();
        	}
        	if(this.commandTextField2.isFocused()) {
        		this.tabCompleter2.complete();
        	}
        	if(this.commandTextField3.isFocused()) {
        		this.tabCompleter3.complete();
        	}
        	if(this.commandTextField4.isFocused()) {
        		this.tabCompleter4.complete();
        	}
        	if(this.commandTextField5.isFocused()) {
        		this.tabCompleter5.complete();
        	}
        }
        if (keyCode == Keyboard.KEY_TAB && (
        		!this.commandTextField1.isFocused() &&
        		!this.commandTextField2.isFocused() &&
        		!this.commandTextField3.isFocused() &&
        		!this.commandTextField4.isFocused() &&
        		!this.commandTextField5.isFocused()
        		))
        {
        	this.changeTabIndex();
        }
        else
        {
            this.tabCompleter1.resetDidComplete();
            this.tabCompleter2.resetDidComplete();
            this.tabCompleter3.resetDidComplete();
            this.tabCompleter4.resetDidComplete();
            this.tabCompleter5.resetDidComplete();
        }

        this.commandTextField1.textboxKeyTyped(typedChar, keyCode);
        this.commandTextField2.textboxKeyTyped(typedChar, keyCode);
        this.commandTextField3.textboxKeyTyped(typedChar, keyCode);
        this.commandTextField4.textboxKeyTyped(typedChar, keyCode);
        this.commandTextField5.textboxKeyTyped(typedChar, keyCode);
        if((Keyboard.KEY_BACK == keyCode) || "1234567890".indexOf(typedChar) > -1) {
    		this.cooldownTextField.textboxKeyTyped(typedChar, keyCode);
	        this.timeoutTextField1.textboxKeyTyped(typedChar, keyCode);
	        this.timeoutTextField2.textboxKeyTyped(typedChar, keyCode);
	        this.timeoutTextField3.textboxKeyTyped(typedChar, keyCode);
	        this.timeoutTextField4.textboxKeyTyped(typedChar, keyCode);
	        this.timeoutTextField5.textboxKeyTyped(typedChar, keyCode);
        }
        if(Keyboard.KEY_ESCAPE == keyCode) {
        	this.actionPerformed(this.cancelBtn);
        }
        if(Keyboard.KEY_RETURN == keyCode) {
        	this.changeTabIndex();
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField1.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField2.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField3.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField4.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField5.mouseClicked(mouseX, mouseY, mouseButton);
        this.timeoutTextField1.mouseClicked(mouseX, mouseY, mouseButton);
        this.timeoutTextField2.mouseClicked(mouseX, mouseY, mouseButton);
        this.timeoutTextField3.mouseClicked(mouseX, mouseY, mouseButton);
        this.timeoutTextField4.mouseClicked(mouseX, mouseY, mouseButton);
        this.timeoutTextField5.mouseClicked(mouseX, mouseY, mouseButton);
        this.cooldownTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("tschallacka.whoneedsbooks.spellbook.gui.enter.command"), this.width / 2, 5, 0xffffff );
        this.drawCenteredString(this.fontRenderer, I18n.format("tschallacka.whoneedsbooks.spellbook.gui.enter.command.example"), this.width / 2, 15, 0xffffff);
        this.drawString(this.fontRenderer, I18n.format("tschallacka.whoneedsbooks.speelbook.gui.cooldown.title"), this.width / 2 - 120, 30, 0xffffff);
        this.drawString(this.fontRenderer, I18n.format("tschallacka.whoneedsbooks.spellbook.gui.command.title"), this.width / 2- 90, 50, 0xffffff);
        this.drawString(this.fontRenderer, I18n.format("tschallacka.whoneedsbooks.spellbook.gui.timemout.title"), this.width / 2 + 140, 50, 0xffffff);

        this.commandTextField1.drawTextBox();
        this.commandTextField2.drawTextBox();
        this.commandTextField3.drawTextBox();
        this.commandTextField4.drawTextBox();
        this.commandTextField5.drawTextBox();
        
        this.timeoutTextField1.drawTextBox();
        this.timeoutTextField2.drawTextBox();
        this.timeoutTextField3.drawTextBox();
        this.timeoutTextField4.drawTextBox();
        this.timeoutTextField5.drawTextBox();
        
        this.cooldownTextField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void updateCmdOutput()
    {

    }

    /**
     * Sets the list of tab completions, as long as they were previously requested.
     */
    public void setCompletions(String... newCompletions)
    {
        this.tabCompleter1.setCompletions(newCompletions);
        this.tabCompleter2.setCompletions(newCompletions);
        this.tabCompleter3.setCompletions(newCompletions);
        this.tabCompleter4.setCompletions(newCompletions);
        this.tabCompleter5.setCompletions(newCompletions);
    }
}