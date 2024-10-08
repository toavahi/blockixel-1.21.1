package net.toavahi.blockixel.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.toavahi.blockixel.block.ModBlocks;
import net.toavahi.blockixel.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.BRUSH_OBSI);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.OX_BOLT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SCULK_LATCH, Models.GENERATED);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.AM_MONOCLE);
        itemModelGenerator.register(ModItems.CHISEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.TREASURE_GLOVE, Models.GENERATED);
        itemModelGenerator.registerCompass(ModItems.STR_COMPASS);
        itemModelGenerator.register(ModItems.PRICKLY_PEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.ARCH_BOOK, Models.GENERATED);
    }
}
