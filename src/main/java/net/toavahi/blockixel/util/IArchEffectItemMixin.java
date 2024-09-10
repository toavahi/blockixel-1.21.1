package net.toavahi.blockixel.util;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface IArchEffectItemMixin {
    void spawnItem(PlayerEntity player, CallbackInfo ci);
}
