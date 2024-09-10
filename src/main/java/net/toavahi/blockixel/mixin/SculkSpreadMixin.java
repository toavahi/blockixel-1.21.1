package net.toavahi.blockixel.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldAccess;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.util.math.random.*;

@Mixin(SculkBlock.class)
public class SculkSpreadMixin implements net.toavahi.blockixel.util.ISculkSpreadMixin {
    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos,
                      Random random, SculkSpreadManager spreadManager, boolean convert) {
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(spreadManager.getSpreadChance()) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
            if (!bl && shouldNotDecay(world, blockPos)) {
                int j = spreadManager.getExtraBlockChance();
                if (random.nextInt(j) < i) {
                    BlockPos blockPos2 = blockPos.up();
                    BlockState blockState = this.getExtraBlockState(world, blockPos2, random, spreadManager.isWorldGen());
                    world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                    world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return Math.max(0, i - j);
            } else {
                return random.nextInt(spreadManager.getDecayChance()) != 0 ? i : i - (bl ? 1 : getDecay(spreadManager, blockPos, catalystPos, i));
            }
        } else {
            return i;
        }
    }

    private static boolean shouldNotDecay(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        if (blockState.isAir() || blockState.isOf(Blocks.WATER) && blockState.getFluidState().isOf(Fluids.WATER)) {
            int i = 0;

            for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 2, 4))) {
                BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.isOf(Blocks.SCULK_SENSOR) || blockState2.isOf(Blocks.SCULK_SHRIEKER) || blockState2.isOf(ModBlocks.SCULK_JAW)) {
                    i++;
                }

                if (i > 2) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private BlockState getExtraBlockState(WorldAccess world, BlockPos pos, net.minecraft.util.math.random.Random random, boolean allowShrieker) {
        BlockState blockState;
        int rand = random.nextInt(11);
        if (rand == 0) {
            blockState = Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, Boolean.valueOf(allowShrieker));
        } else if(rand > 5) {
            blockState = Blocks.SCULK_SENSOR.getDefaultState();
        } else {
            blockState = ModBlocks.SCULK_JAW.getDefaultState();
        }
        Blockixel.LOGGER.info(String.valueOf(rand));
        return blockState.contains(Properties.WATERLOGGED) && !world.getFluidState(pos).isEmpty()
                ? blockState.with(Properties.WATERLOGGED, Boolean.valueOf(true))
                : blockState;
    }

    private static int getDecay(SculkSpreadManager spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
        int i = spreadManager.getMaxDistance();
        float f = MathHelper.square((float)Math.sqrt(cursorPos.getSquaredDistance(catalystPos)) - (float)i);
        int j = MathHelper.square(24 - i);
        float g = Math.min(1.0F, f / (float)j);
        return Math.max(1, (int)((float)charge * g * 0.5F));
    }
}