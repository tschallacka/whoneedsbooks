package tschallacka.mods.whoneedsbooks.entities.living.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ExtendedPlayerProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(ExtendedPlayer.class)
    public static final Capability<ExtendedPlayer> MAGIC_PLAYER = null;

    private ExtendedPlayer instance = MAGIC_PLAYER.getDefaultInstance();

    public ExtendedPlayerProvider(ExtendedPlayer magicPlayer) {
		this.instance = magicPlayer;
	}

	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == MAGIC_PLAYER; 
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == MAGIC_PLAYER ?MAGIC_PLAYER.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return MAGIC_PLAYER.getStorage().writeNBT(MAGIC_PLAYER, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
    	MAGIC_PLAYER.getStorage().readNBT(MAGIC_PLAYER, this.instance, null, nbt);
    }
    
}