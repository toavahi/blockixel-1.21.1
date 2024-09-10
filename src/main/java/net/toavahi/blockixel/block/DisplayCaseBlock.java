package net.toavahi.blockixel.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.toavahi.blockixel.block.blockEntity.DisplayCase.DisplayCaseBlockEntity;
import net.toavahi.blockixel.block.blockEntity.ModBlockEntities;
import net.toavahi.blockixel.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class DisplayCaseBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty AIR = BooleanProperty.of("air");
    public static final BooleanProperty BOOK = BooleanProperty.of("book");

    public DisplayCaseBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(BOOK, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AIR, BOOK);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1, 0, 1, 15, 15, 15);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DisplayCaseBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient() && state.getBlock() != newState.getBlock()) {
            if (world.getBlockEntity(pos) instanceof DisplayCaseBlockEntity) {
                ItemScatterer.spawn(world, pos, (DisplayCaseBlockEntity)world.getBlockEntity(pos));
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof DisplayCaseBlockEntity){
            if(!player.isSneaking()) {
                world.setBlockState(pos, state.with(BOOK, player.getStackInHand(player.getActiveHand()).getItem() == ModItems.ARCH_BOOK));
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS);
                ((DisplayCaseBlockEntity) world.getBlockEntity(pos)).addItem(player.getStackInHand(player.getActiveHand()));
            } else {
                world.setBlockState(pos, state.with(AIR, !state.get(AIR)));
                world.playSound(null, pos, SoundEvents.BLOCK_BARREL_OPEN, SoundCategory.BLOCKS);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.DISPLAY_CASE, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
