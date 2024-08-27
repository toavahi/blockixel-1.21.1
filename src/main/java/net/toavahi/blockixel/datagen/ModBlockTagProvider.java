package net.toavahi.blockixel.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(Blockixel.MOD_ID, "ore_tag")))
                .add(Blocks.REDSTONE_ORE).add(Blocks.DEEPSLATE_REDSTONE_ORE)
                .add(Blocks.DIAMOND_ORE).add(Blocks.DEEPSLATE_DIAMOND_ORE)
                .add(Blocks.IRON_ORE).add(Blocks.DEEPSLATE_IRON_ORE)
                .add(Blocks.LAPIS_ORE).add(Blocks.DEEPSLATE_LAPIS_ORE)
                .add(Blocks.EMERALD_ORE).add(Blocks.DEEPSLATE_EMERALD_ORE)
                .add(Blocks.COPPER_ORE).add(Blocks.DEEPSLATE_COPPER_ORE)
                .add(Blocks.NETHER_GOLD_ORE).add(Blocks.NETHER_QUARTZ_ORE)
                .add(Blocks.COAL_ORE).add(Blocks.DEEPSLATE_COAL_ORE)
                .add(Blocks.GOLD_ORE).add(Blocks.DEEPSLATE_GOLD_ORE)
                .add(Blocks.ANCIENT_DEBRIS);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.AM_DISPENSER);
    }
}
