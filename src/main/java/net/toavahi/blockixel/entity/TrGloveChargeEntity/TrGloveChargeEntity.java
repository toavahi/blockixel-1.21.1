package net.toavahi.blockixel.entity.TrGloveChargeEntity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.WardenAngerManager;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.toavahi.blockixel.entity.ModEntities;

import java.util.Arrays;

public class TrGloveChargeEntity extends ExplosiveProjectileEntity {
    int bonus[][] = new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 1}, {1, 0, 0}, {1, 1, 0}, {0, 1, 1}, {1, 0, 1}, {-1, 0, 0}, {0, -1, 0},
            {0, 0, -1}, {-1, -1, 0}, {-1, 0, -1}, {0, -1, -1}, {1, 0, -1}, {-1, 0, 1}, {0, 1, -1}, {-1, 1, 0}, {1, 1, 1},
            {-1, 1, 1}, {-1, 1, -1}, {1, 1, -1}, {-1, -1, -1}, {-1, -1, 1}, {0, -1, 1}, {1, -1, 1}, {0, -1, 1}, {1, -1, -1}};

    public TrGloveChargeEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public TrGloveChargeEntity(World world, double x, double y, double z) {
        super(ModEntities.CHARGE_ENTITY, x, y, z, world);
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        World world = this.getWorld();
        BlockPos pos = blockHitResult.getBlockPos();
        if(!world.isClient()){
            int counter = 0;
            for(int[] plus : bonus) {
                if(world.getBlockState(new BlockPos(pos.getX() + plus[0], pos.getY() + plus[1], pos.getZ() + plus[2])) == Blocks.STONE.getDefaultState()) {
                    world.breakBlock(new BlockPos(pos.getX() + plus[0], pos.getY() + plus[1], pos.getZ() + plus[2]), false);
                    world.setBlockState(new BlockPos(pos.getX() + plus[0], pos.getY() + plus[1], pos.getZ() + plus[2]), Blocks.GRAVEL.getDefaultState());
                    counter++;
                }
                if(counter > 3 || Arrays.equals(plus, bonus[26])){
                    world.playSound(null, pos, SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.BLOCKS);
                    break;
                }
            }
            /*List<WardenEntity> entities = world.getEntitiesByClass(WardenEntity.class, this.getBoundingBox().expand(5), target -> target != null);
            for (WardenEntity target : entities) {
                target.setPositionTarget(pos, 20);
                //doesn't work, maybe gets used later
            }*/
        }
    }
}
