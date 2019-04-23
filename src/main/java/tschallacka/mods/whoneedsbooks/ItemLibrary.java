package tschallacka.mods.whoneedsbooks;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import tschallacka.mods.whoneedsbooks.item.SpellBook;
import tschallacka.mods.whoneedsbooks.item.type.SpellBookType;
import tschallacka.mods.whoneedsbooks.item.type.variant.ItemVariantGroup;
import tschallacka.mods.whoneedsbooks.item.type.variant.VariantGroup;

public class ItemLibrary {
	public static class VariantGroups {
		public static final ItemVariantGroup<SpellBookType, SpellBook> SPELLBOOKS = ItemVariantGroup.Builder.<SpellBookType, SpellBook>create()
				.groupName("spellbook")
				.suffix()
				.variants(SpellBookType.values())
				.itemFactory(SpellBook::new)
				.build();
	}
	
	@Mod.EventBusSubscriber(modid = WhoNeedsBooks.MODID)
	public static class RegistrationHandler {
		public static final Set<Item> ITEMS = new HashSet<>();
		
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();
			registerVariantGroup(registry, VariantGroups.SPELLBOOKS);
		}
		private static void registerVariantGroup(final IForgeRegistry<Item> registry, final VariantGroup<?, ?> variantGroup) {
			variantGroup.registerItems(registry);
			ITEMS.addAll(variantGroup.getItems());
		}
	}
}
