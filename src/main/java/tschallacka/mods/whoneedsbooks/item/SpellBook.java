package tschallacka.mods.whoneedsbooks.item;

import net.minecraft.item.Item;
import tschallacka.mods.whoneedsbooks.item.type.SpellBookType;
import tschallacka.mods.whoneedsbooks.item.type.variant.ItemVariantGroup;

public class SpellBook extends Item {
	private final SpellBookType type;
	private final ItemVariantGroup<SpellBookType, SpellBook> variantGroup;
	
	public SpellBook(final SpellBookType type, final ItemVariantGroup<SpellBookType, SpellBook> variantGroup) {
		this.type = type;
		this.variantGroup = variantGroup;
	}

	
}