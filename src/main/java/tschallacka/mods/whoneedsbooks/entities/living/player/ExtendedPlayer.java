package tschallacka.mods.whoneedsbooks.entities.living.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;

public interface ExtendedPlayer
{
	public void setPlayer(EntityPlayer player);
	
    public NBTBase getNBT();
    
    public void setNBT(NBTBase base);
    
    public void tick();
}