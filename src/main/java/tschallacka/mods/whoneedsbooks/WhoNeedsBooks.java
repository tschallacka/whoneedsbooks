package tschallacka.mods.whoneedsbooks;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import tschallacka.mods.whoneedsbooks.creative.CreativeTab;
import tschallacka.mods.whoneedsbooks.proxy.Proxy;

import org.apache.logging.log4j.Logger;

@Mod(modid = WhoNeedsBooks.MODID, name = WhoNeedsBooks.NAME, version = WhoNeedsBooks.VERSION)
public class WhoNeedsBooks
{
    public static final String MODID = "whoneedsbooks";
    public static final String NAME = "Who Needs Books";
    public static final String VERSION = "1.0";
    public static final CreativeTab creativeTab = new CreativeTab();
    		
    public static SimpleNetworkWrapper network;
       
    private static Logger logger;
    
    @SidedProxy(clientSide = "tschallacka.mods.whoneedsbooks.proxy.ProxyClient", serverSide = "tschallacka.mods.whoneedsbooks.proxy.ProxyServer")
    public static Proxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    	
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) 
    {
    	proxy.postInit(e);
    }
    
    public void log(String str) {
    	logger.info(str);
    }
}
