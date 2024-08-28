package net.toavahi.blockixel.block.blockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.toavahi.blockixel.block.SculkJawBlock;

import java.util.List;

public class SculkJawBlockEntity extends BlockEntity {
    int ticker;
    int prevTick;

    public SculkJawBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SCULK_JAW, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state){
        if(!world.isClient()){
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, new Box(pos.add(0, 1, 0)), target -> target != null);
            for(LivingEntity target : entities){
                target.teleport(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, false);
                if(world.getTime() % 60 == 0){
                    target.damage(target.getDamageSources().cramming(), 1);
                    ticker++;
                }
            }
            if(state.get(SculkJawBlock.ACTIVATE) < 2 && world.getTime() % 120 == 0 && ticker != prevTick){
                world.setBlockState(pos, state.with(SculkJawBlock.ACTIVATE, state.get(SculkJawBlock.ACTIVATE) + 1));
                prevTick = ticker;
            }
        }
    }
}
