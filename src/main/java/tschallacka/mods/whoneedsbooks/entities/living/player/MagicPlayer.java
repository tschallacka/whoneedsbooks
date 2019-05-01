package tschallacka.mods.whoneedsbooks.entities.living.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import  net.minecraftforge.common.util.Constants.NBT;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;

public class MagicPlayer implements ExtendedPlayer {

	public static final String HELL_RAGE_ACTIVE = "1a";
	public static final String HELL_RAGE_TIME = "1t";
	
	protected int hell_rage_time = 0;
	protected boolean hell_rage_active = false;
	
	protected NBTTagCompound nbt;
	protected EntityPlayer player;

	@Override
	public NBTBase getNBT() 
	{
		NBTTagCompound data = this.getData(this.nbt);
		data.setBoolean(HELL_RAGE_ACTIVE, this.hell_rage_active);
		data.setInteger(HELL_RAGE_TIME, this.hell_rage_time);
		return data;
	}

	@Override
	public void setNBT(NBTBase base) 
	{
		if(base == null || base.getClass() == NBTTagCompound.class) {
			NBTTagCompound data = this.getData((NBTTagCompound)base);
			this.hell_rage_active = data.getBoolean(HELL_RAGE_ACTIVE);
			this.hell_rage_time = data.getInteger(HELL_RAGE_TIME);
			this.nbt = data;
		}
		else {
			throw new RuntimeException("Wrong NBT type provided to Magic User! "+base.getClass());
		}
	}
	
	/**
	 * Checks the NBTTagCompound object if all default values exist, if not creates them.
	 * @param NBTTagCompound base the object to check and augment
	 * @return NBTTagCompound save object with all possible values
	 */
	public NBTTagCompound getData(NBTTagCompound base) 
	{
		if(base == null) {
			base = new NBTTagCompound();
		}
		if(base.hasKey(HELL_RAGE_ACTIVE, NBT.TAG_BYTE))base.setBoolean(HELL_RAGE_ACTIVE, false);
		if(base.hasKey(HELL_RAGE_TIME, NBT.TAG_INT))base.setInteger(HELL_RAGE_TIME, 0);
		return base;
	}

	@Override
	public void setPlayer(EntityPlayer player) 
	{
		this.player = player;
	}
	
	public void tick() 
	{
		WhoNeedsBooks.log("tick");
	}

}
