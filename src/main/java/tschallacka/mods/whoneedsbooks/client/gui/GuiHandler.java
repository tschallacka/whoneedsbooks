package tschallacka.mods.whoneedsbooks.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschallacka.mods.whoneedsbooks.client.gui.provider.SpellBookGuiProvider;

public class GuiHandler implements IGuiHandler
{
	List<GuiContainerFactory> containerFactories;
	
	public GuiHandler() {
		initContainerFactories();
	}
	private void initContainerFactories() {
		containerFactories = new ArrayList<GuiContainerFactory>();
		containerFactories.add(new SpellBookGuiProvider());
		
		
	}
	/**
	 * Called in RedsgreensVillager.interact
	 */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, 
    									World world, int x, int y, int z)   {
    	
    	Entity entity = world.getEntityByID(x);
    	TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
    	for(GuiContainerFactory factory : containerFactories) {
    		if(factory.matches(ID, player, entity,tileEntity, world, x, y, z)) {
    			return factory.getServerGuiElement(ID, player, entity,tileEntity, world, x, y, z);
    		}
    	}
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, 
    									World world, int x, int y, int z)  {
    	
    	TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z)); 
    	Entity entity = world.getEntityByID(x);
    	
    	for(GuiContainerFactory factory : containerFactories) {
    		if(factory.matches(ID, player, entity, tileEntity, world, x, y, z)) {
    			return factory.getClientGuiElement(ID, player, entity, tileEntity, world, x, y, z);
    		}
    	}
        return null;
    }
}