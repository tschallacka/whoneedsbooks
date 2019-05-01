package tschallacka.mods.whoneedsbooks.entities.living.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ExtendedPlayerStorage  implements IStorage<ExtendedPlayer>
{
    @Override
    public NBTBase writeNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing side)
    {
        return instance.getNBT();
    }

    @Override
    public void readNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing side, NBTBase nbt)
    {
        instance.setNBT(nbt);
    }
}