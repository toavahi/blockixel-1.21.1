package net.toavahi.blockixel.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.item.AmShield.AmShieldItem;

public class ModItems {
    public static final Item AM_SHIELD = registerItem("am_shield", new AmShieldItem(new Item.Settings().maxDamage(336)));
    public static final Item OX_BOLT = registerItem("ox_bolt", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Blockixel.MOD_ID, name), item);
    }

    private static void addItemToIngredientTab(FabricItemGroupEntries entries) {
        entries.add(AM_SHIELD);
    }

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemToIngredientTab);
    }
}
