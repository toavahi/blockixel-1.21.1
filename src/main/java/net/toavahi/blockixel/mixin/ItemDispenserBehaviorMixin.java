package net.toavahi.blockixel.mixin;

import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.block.blockEntity.AmDispenserBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDispenserBehavior.class)
public class ItemDispenserBehaviorMixin {
    @Inject(method = {"spawnItem"}, at = {@At("HEAD")}, cancellable = true)
    private static void spawnItem(World world, ItemStack stack, int speed, Direction side,
            Position pos, CallbackInfo info){
        double y = pos.getY();
        if (side.getAxis() == Direction.Axis.Y) {
            y -= 0.125;
        } else {
            y -= 0.15625;
        }
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), y, pos.getZ(), stack);
        BlockPos blockPos = BlockPos.ofFloored(pos);
        switch(side){
            case Direction.UP -> blockPos = blockPos.add(0, -1, 0);
            case Direction.DOWN -> blockPos = blockPos.add(0, 1, 0);
            case Direction.EAST -> blockPos = blockPos.add(-1, 0, 0);
            case Direction.WEST -> blockPos = blockPos.add(1, 0, 0);
            case Direction.NORTH -> blockPos = blockPos.add(0, 0, 1);
            case Direction.SOUTH -> blockPos = blockPos.add(0, 0, -1);
        }
        if(world.getBlockEntity(blockPos) instanceof AmDispenserBlockEntity){
            itemEntity.setVelocity(side.getOffsetX() * 1.3, 0.1 * 1.3, side.getOffsetZ() * 2.0);
        }else{
            double g = world.random.nextDouble() * 0.1 + 0.2;
            itemEntity.setVelocity(
                    world.random.nextTriangular((double)side.getOffsetX() * g, 0.0172275 * (double)speed),
                    world.random.nextTriangular(0.2, 0.0172275 * (double)speed),
                    world.random.nextTriangular((double)side.getOffsetZ() * g, 0.0172275 * (double)speed));
        }
        world.spawnEntity(itemEntity);
        info.cancel();
    }
}