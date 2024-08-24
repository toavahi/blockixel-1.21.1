package net.toavahi.blockixel.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.toavahi.blockixel.item.SculkBundleItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class NoSculkBundleDespawnMixin {
    //taken from Artistry-mods
    @Shadow
    private int itemAge;

    @Shadow
    public abstract ItemStack getStack();

    @Shadow public abstract int getItemAge();

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;discard()V", ordinal = 1, shift = At.Shift.BEFORE), cancellable = true)
    public void onTick(CallbackInfo ci) {
        if (this.getStack().getItem() instanceof SculkBundleItem) {
            if (this.getItemAge() >= 7000) {
                ((ItemEntity) (Object) this).discard();
                ci.cancel();
            }
            this.itemAge = 0;
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "setDespawnImmediately")
    public void onSetDespawn(CallbackInfo ci) {
        this.itemAge = 7000;
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!this.getStack().isEmpty() && this.getStack().getItem() instanceof SculkBundleItem && (source.isIn(DamageTypeTags.IS_EXPLOSION) || source.isOf(DamageTypes.CACTUS))) {
            cir.setReturnValue(false);
        }
    }
}