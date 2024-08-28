package net.toavahi.blockixel.block;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.toavahi.blockixel.block.blockEntity.ModBlockEntities;
import net.toavahi.blockixel.block.blockEntity.SculkJawBlockEntity;
import org.jetbrains.annotations.Nullable;


public class SculkJawBlock extends BlockWithEntity implements BlockEntityProvider {
    public static IntProperty ACTIVATE = IntProperty.of("activate", 0, 2);

    public SculkJawBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(ACTIVATE, 0));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATE);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SculkJawBlockEntity(pos, state);
    }

    @Override
    protected BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.SCULK_SENSOR;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.SCULK_JAW, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
