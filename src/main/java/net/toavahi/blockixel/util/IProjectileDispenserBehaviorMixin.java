package net.toavahi.blockixel.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface IProjectileDispenserBehaviorMixin {
    ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack);
}
