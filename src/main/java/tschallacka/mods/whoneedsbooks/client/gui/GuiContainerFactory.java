package tschallacka.mods.whoneedsbooks.client.gui;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface GuiContainerFactory 
{
	/**
	 * Return true if all the conditions match on this, and the gui
	 * should be opened for this set of variables
	 * @param ID The id
	 * @param player The player
	 * @param entity The entity, can be null if not for a specific entity
	 * @param tileEntity the Tile Entity, can be null
	 * @param world The world object
	 * @param x 
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean matches(int ID, 
							EntityPlayer player, 
							Entity entity, 
							TileEntity tileEntity,
							World world, 
							int x, 
							int y, 
							int z) ;
	
	public Object getServerGuiElement(int ID, 
										EntityPlayer player, 
										Entity entity, 
										TileEntity tileEntity, 
										World world, 
										int x, 
										int y, 
										int z);
	
	public Object getClientGuiElement(int ID, 
										EntityPlayer player, 
										Entity entity, 
										TileEntity tileEntity,
										World world, 
										int x, 
										int y, 
										int z);
		
}