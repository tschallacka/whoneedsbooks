package tschallacka.mods.whoneedsbooks.util;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;

/**
 * Utility methods for Forge registries.
 *
 * @author Choonster as state of https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1bd29159fd0c3bb470bfe6a5e026ce65ea567b41/src/main/java/choonster/testmod3/util/RegistryUtil.java
 * See CONTRIBUTORS.md for a list of contributors
 */
public class RegistryUtil {
	/**
	 * Get an entry from the provided registry, using <code>testmod3</code> as the mod ID.
	 *
	 * @param registry The registry
	 * @param name     The name of the entry
	 * @param <T>      The registry type
	 * @return The registry entry
	 * @throws NullPointerException When the entry doesn't exist
	 */
	public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(final IForgeRegistry<T> registry, final String name) {
		return getRegistryEntry(registry, WhoNeedsBooks.MODID, name);
	}

	/**
	 * Get an entry from the provided registry.
	 *
	 * @param registry The registry
	 * @param modid    The mod ID of the entry
	 * @param name     The name of the entry
	 * @param <T>      The registry type
	 * @return The registry entry
	 * @throws NullPointerException When the entry doesn't exist
	 */
	public static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(final IForgeRegistry<T> registry, final String modid, final String name) {
		final ResourceLocation key = new ResourceLocation(modid, name);
		final T registryEntry = registry.getValue(key);
		return Preconditions.checkNotNull(registryEntry, "%s doesn't exist in registry %s", key, RegistryManager.ACTIVE.getName(registry));
	}

	/**
	 * Set the registry name of {@code block} to {@code blockName} and the translation key to the full registry name.
	 *
	 * @param block     The block
	 * @param blockName The block's name
	 * @param <BLOCK>   The block type
	 * @return The block
	 */
	public static <BLOCK extends Block> BLOCK setBlockName(final BLOCK block, final String blockName) {
		block.setRegistryName(WhoNeedsBooks.MODID, blockName);
		final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName());
		block.setUnlocalizedName(registryName.toString());
		return block;
	}

	/**
	 * Sets the block's creative tab to the TestMod3 creative tab if it hasn't already been set.
	 *
	 * @param block   The block
	 * @param <BLOCK> The block type
	 * @return The block
	 */
	public static <BLOCK extends Block> BLOCK setDefaultCreativeTab(final BLOCK block) {
		if (block.getCreativeTabToDisplayOn() == null) {
			block.setCreativeTab(WhoNeedsBooks.creativeTab);
		}

		return block;
	}

	/**
	 * Set the registry name of {@code item} to {@code itemName} and the translation key to the full registry name.
	 *
	 * @param item     The item
	 * @param itemName The item's name
	 */
	public static <ITEM extends Item> ITEM setItemName(final ITEM item, final String itemName) {
		item.setRegistryName(WhoNeedsBooks.MODID, itemName);
		final ResourceLocation registryName = Preconditions.checkNotNull(item.getRegistryName());
		item.setUnlocalizedName(registryName.toString());
		return item;
	}

	/**
	 * Sets the item's creative tab to the TestMod3 creative tab if it hasn't already been set.
	 *
	 * @param item   The item
	 * @param <ITEM> The item type
	 * @return The item
	 */
	public static <ITEM extends Item> ITEM setDefaultCreativeTab(final ITEM item) {
		if (item.getCreativeTab() == null) {
			item.setCreativeTab(WhoNeedsBooks.creativeTab);
		}
		return item;
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