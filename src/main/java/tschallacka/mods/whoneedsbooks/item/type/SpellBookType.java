package tschallacka.mods.whoneedsbooks.item.type;

import net.minecraft.util.IStringSerializable;

public enum SpellBookType implements IStringSerializable {
	NOVICE(0, "novice"),
	ADEPT(1, "adept"),
	MASTERFUL(2, "masterful"),
	AURELIAN(3, "aurelian");

	// TODO: Remove in 1.13
	private final int meta;
	private final String name;

	SpellBookType(final int meta, final String name) {
		this.meta = meta;
		this.name = name;
	}

	// TODO: Remove in 1.13
	@Deprecated
	public int getMeta() {
		return meta;
	}

	@Override
	public String getName() {
		return name;
	}
}