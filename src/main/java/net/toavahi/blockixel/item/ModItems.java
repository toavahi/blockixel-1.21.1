package net.toavahi.blockixel.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.item.AmMonocle.AmMonocleItem;
import net.toavahi.blockixel.item.AmShield.AmShieldItem;
import net.toavahi.blockixel.item.Chisel.ChiselItem;
import net.toavahi.blockixel.item.TrGlove.TrGloveItem;

public class ModItems {
    public static final Item AM_SHIELD = registerItem("am_shield", new AmShieldItem(new Item.Settings().maxDamage(336)));
    public static final Item OX_BOLT = registerItem("ox_bolt", new Item(new Item.Settings()));
    public static final Item SCULK_LATCH = registerItem("sculk_latch", new Item(new Item.Settings()));
    public static final Item TREASURE_GLOVE = registerItem("tr_glove", new TrGloveItem(new Item.Settings().maxCount(1)));
    public static final Item AM_MONOCLE = registerItem("am_monocle",
            new AmMonocleItem(new Item.Settings().maxCount(1)));
    public static final Item CHISEL = registerItem("chisel", new ChiselItem(new Item.Settings()));
    public static final Item SCULK_BUNDLE = registerItem("sculk_bundle", new SculkBundleItem(new Item.Settings().maxCount(1)
            .component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT).fireproof()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Blockixel.MOD_ID, name), item);
    }

    private static void addItemToCombatTab(FabricItemGroupEntries entries) {
        entries.add(AM_SHIELD);
    }
    private static void addItemToIngredientTab(FabricItemGroupEntries entries){
        entries.add(OX_BOLT);
        entries.add(SCULK_LATCH);
    }
    private static void addItemToToolTab(FabricItemGroupEntries entries){
        entries.add(AM_MONOCLE);
        entries.add(TREASURE_GLOVE);
        entries.add(CHISEL);
        entries.add(SCULK_BUNDLE);
    }
    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemToCombatTab);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemToIngredientTab);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemToToolTab);
    }
}
