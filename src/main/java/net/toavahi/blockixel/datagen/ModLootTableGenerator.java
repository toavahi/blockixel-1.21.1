package net.toavahi.blockixel.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.toavahi.blockixel.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {
    public ModLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.AM_DISPENSER, ModBlocks.AM_DISPENSER);
        addDropWithSilkTouch(ModBlocks.SCULK_JAW);
        addDrop(ModBlocks.FANCY_CACTUS, ModBlocks.FANCY_CACTUS);
        addDrop(ModBlocks.AM_PLATE, ModBlocks.AM_PLATE);
    }

}
