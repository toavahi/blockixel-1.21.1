package net.toavahi.blockixel.block.blockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.toavahi.blockixel.item.ModItems;

public class BrushObsiBlockEntity extends BrushableBlockEntity {

    public BrushObsiBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.BRUSH_OBSI;
    }

    @Override
    public boolean supports(BlockState state) {
        return ModBlockEntities.BRUSH_OBSI.supports(state);
    }

    @Override
    public boolean brush(long worldTime, PlayerEntity player, Direction hitDirection) {
        if(player.getStackInHand(player.getActiveHand()).getItem() == ModItems.CHISEL) {
            return super.brush(worldTime, player, hitDirection);
        } else {
            return false;
        }
    }
}
