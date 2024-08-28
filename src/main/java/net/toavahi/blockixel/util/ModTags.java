package net.toavahi.blockixel.util;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.toavahi.blockixel.Blockixel;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> ORE_TAG = createTag("ore_tag");

        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Blockixel.MOD_ID, name));
        }
    }
    public static class Structures{
        public static final TagKey<Structure> SCULK_TEMPLE = createTag("sculk_temple");

        private static TagKey<Structure> createTag(String name){
            return TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(Blockixel.MOD_ID, name));
        }
    }
}
