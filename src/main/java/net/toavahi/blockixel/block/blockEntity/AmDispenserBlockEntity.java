package net.toavahi.blockixel.block.blockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class AmDispenserBlockEntity extends DispenserBlockEntity {
    public AmDispenserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AM_DISPENSER, pos, state);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("block.blockixel.am_dispenser.tooltip");
    }
}
