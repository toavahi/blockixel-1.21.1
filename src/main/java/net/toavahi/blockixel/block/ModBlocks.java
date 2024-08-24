package net.toavahi.blockixel.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;

public class ModBlocks {
    public static final Block AM_PLATE = registerBlock("am_plate", new Block(AbstractBlock.Settings.copy(Blocks.MEDIUM_AMETHYST_BUD)));
    //public static final Block BIG_VASE = registerBlock("big_vase", new Block(AbstractBlock.Settings.copy()));
    //public static final Block MID_VASE = registerBlock("mid_vase", new Block(AbstractBlock.Settings.copy()));
    //public static final Block SMALL_VASE = registerBlock("small_vase", new Block(AbstractBlock.Settings.copy()));

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
