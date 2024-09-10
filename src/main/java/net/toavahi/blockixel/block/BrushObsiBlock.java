package net.toavahi.blockixel.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrushableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.toavahi.blockixel.block.blockEntity.BrushObsiBlockEntity;
import org.jetbrains.annotations.Nullable;

public class BrushObsiBlock extends BrushableBlock {

    public BrushObsiBlock(Block block, SoundEvent sound, SoundEvent soundFinish, Settings settings) {
        super(block, sound, soundFinish, settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BrushObsiBlockEntity(pos, state);
    }

}
