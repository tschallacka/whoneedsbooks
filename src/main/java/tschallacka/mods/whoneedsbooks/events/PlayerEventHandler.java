package tschallacka.mods.whoneedsbooks.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;
import tschallacka.mods.whoneedsbooks.entities.living.player.ExtendedPlayer;
import tschallacka.mods.whoneedsbooks.entities.living.player.ExtendedPlayerEventHandler;
import tschallacka.mods.whoneedsbooks.entities.living.player.MagicPlayer;
import tschallacka.mods.whoneedsbooks.entities.living.player.ExtendedPlayerProvider;

@Mod.EventBusSubscriber(modid = WhoNeedsBooks.MODID)
public class PlayerEventHandler
{
    @SubscribeEvent
    public static void onPlayerLogsIn(PlayerLoggedInEvent event)
    {
        EntityPlayer player = event.player;
        ExtendedPlayer magicPlayer = player.getCapability(ExtendedPlayerProvider.MAGIC_PLAYER, null);
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        
    }
    
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) 
    {
    	EntityPlayer player = event.player;
        ExtendedPlayer extendedPlayer = player.getCapability(ExtendedPlayerProvider.MAGIC_PLAYER, null);
        extendedPlayer.tick();
    }
    
    @SubscribeEvent
    public static void onPlayerFalls(LivingFallEvent event)
    {
        
    }
    
    
}