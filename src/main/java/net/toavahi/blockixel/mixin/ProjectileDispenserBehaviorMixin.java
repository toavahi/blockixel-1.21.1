package net.toavahi.blockixel.mixin;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.block.ModBlocks;
import net.toavahi.blockixel.block.blockEntity.AmDispenserBlockEntity;
import net.toavahi.blockixel.block.blockEntity.ModBlockEntities;
import net.toavahi.blockixel.util.IProjectileDispenserBehaviorMixin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ProjectileDispenserBehavior.class)
public class ProjectileDispenserBehaviorMixin implements IProjectileDispenserBehaviorMixin {
    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        World world = pointer.world();
        ProjectileItem projectile = null;
        ProjectileItem.Settings settings = null;
        if (stack.getItem() instanceof ProjectileItem) {
            projectile = (ProjectileItem) stack.getItem();
            settings = projectile.getProjectileSettings();
        }
        Direction direction = pointer.state().get(DispenserBlock.FACING);
        if(settings != null && projectile != null) {
            Position position = settings.positionFunction().getDispensePosition(pointer, direction);
            BlockPos blockPos = BlockPos.ofFloored(position);
            switch(direction){
                case Direction.UP -> blockPos = blockPos.add(0, -1, 0);
                case Direction.DOWN -> blockPos = blockPos.add(0, 1, 0);
                case Direction.EAST -> blockPos = blockPos.add(-1, 0, 0);
                case Direction.WEST -> blockPos = blockPos.add(1, 0, 0);
                case Direction.NORTH -> blockPos = blockPos.add(0, 0, 1);
                case Direction.SOUTH -> blockPos = blockPos.add(0, 0, -1);
            }
            float power = world.getBlockEntity(blockPos) instanceof AmDispenserBlockEntity ? 4.0F : 1.0F;
            ProjectileEntity projectileEntity = projectile.createEntity(world, position, stack, direction);
            projectile
                    .initializeProjectile(
                            projectileEntity,
                            direction.getOffsetX(),
                            direction.getOffsetY(),
                            direction.getOffsetZ(),
                            settings.power() * power,
                            settings.uncertainty()
                    );
            Blockixel.LOGGER.info("{}      {}", blockPos.toString(), world.getBlockEntity(blockPos) instanceof AmDispenserBlockEntity);
            world.spawnEntity(projectileEntity);
            stack.decrement(1);
        }
        return stack;
    }
}
