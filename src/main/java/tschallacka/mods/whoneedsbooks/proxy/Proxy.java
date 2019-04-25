package tschallacka.mods.whoneedsbooks.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

/**
 * 
 * @author choonster as state of https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/6b3101e27b03f73b63124e01b9d1786b1320fb83/src/main/java/choonster/testmod3/proxy/IProxy.java
 * See CONTRIBUTORS.md for a list of contributors
 */

public interface Proxy {
	void preInit(FMLPreInitializationEvent e);

	void init(FMLInitializationEvent e);

	void postInit(FMLPostInitializationEvent e);

	/**
	 * Perform a right click on the client side.
	 *
	 * @throws WrongSideException If called on the dedicated server.
	 */
	void doClientRightClick();

	/**
	 * Get the client player.
	 *
	 * @return The client player
	 * @throws WrongSideException If called on the dedicated server.
	 */
	@Nullable
	EntityPlayer getClientPlayer();

	/**
	 * Get the client {@link World}.
	 *
	 * @return The client World
	 * @throws WrongSideException If called on the dedicated server.
	 */
	@Nullable
	World getClientWorld();

	/**
	 * Get the {@link IThreadListener} for the {@link MessageContext}'s {@link Side}.
	 *
	 * @param context The message context
	 * @return The thread listener
	 */
	IThreadListener getThreadListener(MessageContext context);

	/**
	 * Get the {@link EntityPlayer} from the {@link MessageContext}.
	 *
	 * @param context The message context
	 * @return The player
	 */
	EntityPlayer getPlayer(MessageContext context);

	/**
	 * Returns the current thread based on side during message handling,
	 * used for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx);
	
	/**
	 * Thrown when a proxy method is called from the wrong side.
	 */
	class WrongSideException extends RuntimeException {
		public WrongSideException(final String message) {
			super(message);
		}

		public WrongSideException(final String message, final Throwable cause) {
			super(message, cause);
		}
	}
}