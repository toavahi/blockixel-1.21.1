package net.toavahi.blockixel.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.block.blockEntity.AmDispenserBlockEntity;
import net.toavahi.blockixel.block.blockEntity.ModBlockEntities;


public class AmDispenserBlock extends DispenserBlock {
    public AmDispenserBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AmDispenserBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public MapCodec<? extends DispenserBlock> getCodec() {
        return null;
    }

    protected void dispense(ServerWorld world, BlockState state, BlockPos pos) {
        AmDispenserBlockEntity dispenserBlockEntity = world.getBlockEntity(pos, ModBlockEntities.AM_DISPENSER).orElse(null);
        if (dispenserBlockEntity == null) {
            Blockixel.LOGGER.warn("Ignoring dispensing attempt for Dispenser without matching block entity at {}", pos);
        } else {
            BlockPointer blockPointer = new BlockPointer(world, pos, state, dispenserBlockEntity);
            int i = dispenserBlockEntity.chooseNonEmptySlot(world.random);
            if (i < 0) {
                world.syncWorldEvent(WorldEvents.DISPENSER_FAILS, pos, 0);
                world.emitGameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Emitter.of(dispenserBlockEntity.getCachedState()));
            } else {
                ItemStack itemStack = dispenserBlockEntity.getStack(i);
                DispenserBehavior dispenserBehavior = this.getBehaviorForItem(world, itemStack);
                if (dispenserBehavior != DispenserBehavior.NOOP) {
                    dispenserBlockEntity.setStack(i, dispenserBehavior.dispense(blockPointer, itemStack));
                }
            }
        }
    }

    protected DispenserBehavior getBehaviorForItem(World world, ItemStack stack) {
        return !stack.isItemEnabled(world.getEnabledFeatures()) ? new ItemDispenserBehavior() : BEHAVIORS.get(stack.getItem());
    }
}
