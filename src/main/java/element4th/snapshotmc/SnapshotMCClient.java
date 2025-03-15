package element4th.snapshotmc;

import com.google.gson.JsonObject;
import element4th.snapshotmc.common.block.SnapshotBlocks;
import element4th.snapshotmc.common.item.SnapshotItems;
import nazario.liby.api.registry.auto.LibyEntrypoints;
import nazario.liby.api.registry.auto.LibyRegistryLoader;
import nazario.liby.api.registry.runtime.models.LibyJsonModel;
import nazario.liby.api.registry.runtime.models.LibyModel;
import nazario.liby.api.registry.runtime.models.LibyModelRegistry;
import nazario.liby.api.util.LibyModelHelper;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

public class SnapshotMCClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LibyRegistryLoader.load("element4th.snapshotmc", SnapshotMCMain.LOGGER, LibyEntrypoints.CLIENT);

        LibyModelRegistry.register(
                LibyModelHelper.createBlockItem(SnapshotBlocks.ROPE_BLOCK),
                createItemCustom(SnapshotItems.CHAINMAIL, SnapshotMCMain.id("item/chainmail")),
                createItemCustom(ModelIdentifier.ofInventoryVariant(SnapshotItems.CAPYBARA_SPAWN_EGG.liby$getId()), new Identifier[0], "minecraft:item/template_spawn_egg"),
                createItemCustom(SnapshotItems.BOOMERANG, SnapshotMCMain.id("item/boomerang"))
        );
    }

    public static LibyModel createItemCustom(ModelIdentifier id, Identifier[] spriteIdentifier, String parentModel) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", parentModel);
        JsonObject texturesObject = new JsonObject();

        for(int i = 0; i < spriteIdentifier.length; ++i) {
            texturesObject.addProperty("layer" + i, spriteIdentifier[i].toString());
        }

        model.add("textures", texturesObject);

        return new LibyJsonModel(id.id().withPrefixedPath("item/"), id.getVariant(), model, "item");
    }

    public static LibyModel createItemCustom(ItemConvertible convertible, Identifier... spriteIdentifier) {
        return createItemCustom(ModelIdentifier.ofInventoryVariant(convertible.asItem().liby$getId()), spriteIdentifier, "minecraft:item/generated");
    }
}
