package net.toavahi.blockixel.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        for(Item item : Registries.ITEM){
            if(item.getDefaultStack().get(DataComponentTypes.MAX_STACK_SIZE) != null){
                switch(item.getDefaultStack().get(DataComponentTypes.MAX_STACK_SIZE)){
                    case 1 -> getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(Blockixel.MOD_ID, "stack_1"))).add(item);
                    case 16 -> getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(Blockixel.MOD_ID, "stack_16"))).add(item);
                }
            }
        }
    }
}
