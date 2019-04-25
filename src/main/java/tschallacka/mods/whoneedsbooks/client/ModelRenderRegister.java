package tschallacka.mods.whoneedsbooks.client;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import tschallacka.mods.whoneedsbooks.ItemLibrary;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tschallacka.mods.whoneedsbooks.WhoNeedsBooks;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = WhoNeedsBooks.MODID)
public class ModelRenderRegister 
{
	public static final ModelRenderRegister INSTANCE = new ModelRenderRegister();
	
	/**
	 * The {@link Item}s that have had models registered so far.
	 */
	private final Set<Item> itemsRegistered = new HashSet<>();
	
	
	private ModelRenderRegister() {
	}
	
	
	/**
	 * Register this mod's {@link Fluid}, {@link Block} and {@link Item} models.
	 *
	 * @param event The event
	 */
	@SubscribeEvent
	public static void registerAllModels(final ModelRegistryEvent event) {
		INSTANCE.registerItemModels();
	}
	
	
	
	private void registerItemModels() {
		ItemLibrary.RegistrationHandler.ITEMS.stream().filter(item -> !itemsRegistered.contains(item)).forEach(this::registerItemModel);
	}
	/**
	 * Register a single model for an {@link Item}.
	 * <p>
	 * Uses the registry name as the domain/path and {@code "inventory"} as the variant.
	 *
	 * @param item The Item
	 */
	private void registerItemModel(final Item item) {
		final ResourceLocation registryName = Objects.requireNonNull(item.getRegistryName());
		registerItemModel(item, registryName.toString());
	}

	/**
	 * Register a single model for an {@link Item}.
	 * <p>
	 * Uses {@code modelLocation} as the domain/path and {@link "inventory"} as the variant.
	 *
	 * @param item          The Item
	 * @param modelLocation The model location
	 */
	private void registerItemModel(final Item item, final String modelLocation) {
		final ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
		ModelBakery.registerItemVariants(item, fullModelLocation); // Ensure the custom model is loaded and prevent the default model from being loaded
		registerItemModel(item, stack -> fullModelLocation);
	}

	/**
	 * Register an {@link ItemMeshDefinition} for an {@link Item}.
	 *
	 * @param item           The Item
	 * @param meshDefinition The ItemMeshDefinition
	 */
	private void registerItemModel(final Item item, final ItemMeshDefinition meshDefinition) {
		itemsRegistered.add(item);
		ModelLoader.setCustomMeshDefinition(item, meshDefinition);
	}

	/**
	 * Register a model for a metadata value an {@link Item}.
	 * <p>
	 * Uses the registry name as the domain/path and {@code variant} as the variant.
	 *
	 * @param item     The Item
	 * @param metadata The metadata
	 * @param variant  The variant
	 */
	private void registerItemModelForMeta(final Item item, final int metadata, final String variant) {
		itemsRegistered.add(item);
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
	}
}
