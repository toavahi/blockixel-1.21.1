package net.toavahi.blockixel.block.blockEntity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<AmDispenserBlockEntity> AM_DISPENSER = create(
            "am_dispenser", BlockEntityType.Builder.create(AmDispenserBlockEntity::new, ModBlocks.AM_DISPENSER).build(null));

    public static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Blockixel.MOD_ID, id), builder);
    }
    public static void registerModEntities(){}
}
