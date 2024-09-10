package net.toavahi.blockixel.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.toavahi.blockixel.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        /*ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.OX_BOLT)
                .input(Items.COPPER_INGOT)
                //.criterion("has_glass", conditionsFromItem(Items.GLASS))
                .offerTo(exporter); example*/
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.TREASURE_GLOVE)
                .criterion("has_glove", InventoryChangedCriterion.Conditions.items(ModItems.TREASURE_GLOVE))
                .input('I', Items.IRON_INGOT)
                .input('#', Items.LEATHER)
                .input('X', Items.IRON_NUGGET)
                .pattern(" I ")
                .pattern("X#X")
                .pattern("X#X")
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL)
                .criterion("has_chisel", InventoryChangedCriterion.Conditions.items(ModItems.CHISEL))
                .input('A', Items.AMETHYST_SHARD)
                .input('C', Items.COPPER_INGOT)
                .input('S', Items.STICK)
                .pattern("A")
                .pattern("C")
                .pattern("S")
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AM_MONOCLE)
                .criterion("has_monocle", InventoryChangedCriterion.Conditions.items(ModItems.AM_MONOCLE))
                .input('C', Items.COPPER_INGOT)
                .input('A', Items.AMETHYST_SHARD)
                .pattern(" C ")
                .pattern("CAC")
                .pattern(" C ")
                .offerTo(exporter);
    }
}
