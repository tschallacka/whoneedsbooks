package tschallacka.mods.whoneedsbooks.client.gui;

public enum GuiType {
	SPELLBOOK_GUI
	;
	public int getID() {
		return this.ordinal();
	}
	public boolean is(int compare) {
		return compare == this.ordinal();
	}
}
