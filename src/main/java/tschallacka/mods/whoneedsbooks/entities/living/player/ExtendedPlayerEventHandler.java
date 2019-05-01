package tschallacka.mods.whoneedsbooks.entities.living.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;

/**
 * Capability handler
 * 
 * This class is responsible for attaching our capabilities
 */
@Mod.EventBusSubscriber(modid = WhoNeedsBooks.MODID)
public class ExtendedPlayerEventHandler
{
    public static final ResourceLocation MAGIC_PLAYER = new ResourceLocation(WhoNeedsBooks.MODID, "magic_player");
    
    @SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
    	
		if (event.getObject() instanceof EntityPlayer) {
			WhoNeedsBooks.log("============================= HELLO =================================");
			final MagicPlayer magicPlayer = new MagicPlayer();
			magicPlayer.setPlayer((EntityPlayer)event.getObject());
			event.addCapability(ExtendedPlayerEventHandler.MAGIC_PLAYER, new ExtendedPlayerProvider(magicPlayer));
		}
	}
    /**
     * Copy data from dead player to the new player
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        EntityPlayer player = event.getEntityPlayer();
        ExtendedPlayer new_soul = player.getCapability(ExtendedPlayerProvider.MAGIC_PLAYER, null);
        ExtendedPlayer old_soul = event.getOriginal().getCapability(ExtendedPlayerProvider.MAGIC_PLAYER, null);
        new_soul.setNBT(old_soul.getNBT());
    }
}