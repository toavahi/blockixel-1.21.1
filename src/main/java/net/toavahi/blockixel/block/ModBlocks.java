package net.toavahi.blockixel.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BrushableBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;

public class ModBlocks {
    public static final Block AM_PLATE = registerBlock("am_plate", new AmPlateBlock(AbstractBlock.Settings.copy(Blocks.MEDIUM_AMETHYST_BUD).nonOpaque()));
    public static final Block AM_DISPENSER = registerBlock("am_dispenser", new AmDispenserBlock(AbstractBlock.Settings.copy(Blocks.DISPENSER)));
    public static final Block SCULK_JAW = registerBlock("sculk_jaw", new SculkJawBlock(AbstractBlock.Settings.copy(Blocks.SCULK)));
    public static final Block FANCY_CACTUS = registerBlock("fancy_cactus", new FancyCactusBlock(AbstractBlock.Settings.copy(Blocks.CACTUS).nonOpaque()));
    public static final Block DISPLAY_CASE = registerBlock("display_case", new DisplayCaseBlock(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque()));
    public static final Block BRUSH_OBSI = registerBlock("brush_obsi", new BrushObsiBlock(Blocks.OBSIDIAN, SoundEvents.BLOCK_STONE_BREAK,
            SoundEvents.BLOCK_STONE_BREAK, AbstractBlock.Settings.copy(Blocks.OBSIDIAN)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Blockixel.MOD_ID, name), block);
    }
    public static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(Blockixel.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }
    public static void registerBlocks(){
        Blockixel.LOGGER.info("register blocks " + Blockixel.MOD_ID);
    }
}
