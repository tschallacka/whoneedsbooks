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

import javax.annotation.Nullable;
/**
 * 
 * @author choonster as state of https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/19e2054526035fda2b8631f7e9dd89803cbc2503/src/main/java/choonster/testmod3/proxy/DedicatedServerProxy.java
 * See CONTRIBUTORS.md for a list of contributors
 */
public class ProxyServer implements Proxy {
	

	@Override
	public void doClientRightClick() {
		throw new WrongSideException("Tried to perform client right click on the dedicated server");
	}

	@Nullable
	@Override
	public EntityPlayer getClientPlayer() {
		throw new WrongSideException("Tried to get the client player on the dedicated server");
	}

	@Nullable
	@Override
	public World getClientWorld() {
		throw new WrongSideException("Tried to get the client world on the dedicated server");
	}

	@Override
	public IThreadListener getThreadListener(final MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player.getServer();
		} else {
			throw new WrongSideException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
		}
	}

	@Override
	public EntityPlayer getPlayer(final MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player;
		} else {
			throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FMLInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		// TODO Auto-generated method stub
		
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