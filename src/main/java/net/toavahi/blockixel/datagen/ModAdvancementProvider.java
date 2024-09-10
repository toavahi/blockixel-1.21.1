package net.toavahi.blockixel.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.item.ModItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry root = Advancement.Builder.create()
                .display(
                        ModItems.TREASURE_GLOVE,
                        Text.translatable("advancement.blockixel.login.title"),
                        Text.translatable("advancement.blockixel.login.desc"),
                        Identifier.of("textures/block/amethyst_block.png"),
                        AdvancementFrame.TASK,
                        true,
                        false,
                        false
                )
                .criterion("login", InventoryChangedCriterion.Conditions.items(Items.CRAFTING_TABLE))
                .build(consumer, Blockixel.MOD_ID + "/root");
    }

    private static RegistryEntry<Structure> of(RegistryWrapper.WrapperLookup registryLookup, RegistryKey<Structure> id) {
        return registryLookup.getWrapperOrThrow(RegistryKeys.STRUCTURE).getOrThrow(id);
    }
}
