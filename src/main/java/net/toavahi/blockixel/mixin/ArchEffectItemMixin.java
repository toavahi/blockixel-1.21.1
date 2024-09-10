package net.toavahi.blockixel.mixin;

import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.toavahi.blockixel.effect.ModEffects;
import net.toavahi.blockixel.util.IArchEffectItemMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(BrushableBlockEntity.class)
public class ArchEffectItemMixin implements IArchEffectItemMixin {
    @Shadow
    private ItemStack item;

    @Inject(method = "spawnItem", at = @At("HEAD"))
    public void spawnItem(PlayerEntity player, CallbackInfo ci){
        BrushableBlockEntity obj = (BrushableBlockEntity) (Object) this;
        if (obj.getWorld() != null && obj.getWorld().getServer() != null) {
            obj.generateItem(player);
            if (!obj.getItem().isEmpty()) {
                double d = EntityType.ITEM.getWidth();
                double e = 1.0 - d;
                double f = d / 2.0;
                Direction direction = Objects.requireNonNullElse(obj.getHitDirection(), Direction.UP);
                BlockPos blockPos = obj.getPos().offset(direction, 1);
                double g = (double)blockPos.getX() + 0.5 * e + f;
                double h = (double)blockPos.getY() + 0.5 + (double)(EntityType.ITEM.getHeight() / 2.0F);
                double i = (double)blockPos.getZ() + 0.5 * e + f;
                ItemStack stack = obj.getItem().split(obj.getWorld().random.nextInt(21) + 10);
                if(player.hasStatusEffect(ModEffects.ARCHEOLOGY)) {
                    stack.setCount(stack.getCount() * 2);
                }
                ItemEntity itemEntity = new ItemEntity(obj.getWorld(), g, h, i, stack);
                itemEntity.setVelocity(Vec3d.ZERO);
                obj.getWorld().spawnEntity(itemEntity);
                this.item = ItemStack.EMPTY;
            }
        }
    }
}
