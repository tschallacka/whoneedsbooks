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
/*The MIT License (MIT)

Test Mod 3 - Copyright (c) 2015-2017 Choonster

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/