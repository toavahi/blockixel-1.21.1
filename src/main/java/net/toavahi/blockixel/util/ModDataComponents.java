package net.toavahi.blockixel.util;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;

import java.util.List;

public class ModDataComponents {
    public static final ComponentType<Integer> SH_CHARGE = register("sh_charge", ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build());
    public static final ComponentType<Integer> SCAN_TIME = register("scan_time", ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build());
    public static final ComponentType<List<ItemStack>> PACK_INSIDE = register("pack_inside", ComponentType.<List<ItemStack>>builder().codec(ItemStack.CODEC.listOf()).packetCodec(ItemStack.LIST_PACKET_CODEC).build());
    public static final ComponentType<Integer> PACK_SIZE = register("pack_size", ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build());

    private static <T> ComponentType<T> register(String name, ComponentType<T> builder){
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Blockixel.MOD_ID, name), builder);
    }

    public static void registerComponents(){}
}
