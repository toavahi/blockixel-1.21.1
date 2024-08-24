package net.toavahi.blockixel.mixin;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.toavahi.blockixel.item.ModItems;
import net.toavahi.blockixel.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class ShieldBrakeMixin {

    @Inject(method = "disableShield", at = @At("HEAD"))
    public void disableShield(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        player.getItemCooldownManager().set(ModItems.AM_SHIELD, 100);
        player.clearActiveItem();
        player.getWorld().sendEntityStatus(player, EntityStatuses.BREAK_SHIELD);
    }


    @Inject(method = "damageShield", at = @At("HEAD"))
    public void damageShield(float amount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!(player.getActiveItem().getItem() instanceof ShieldItem)) {
            return;
        }
        if (!player.getWorld().isClient) {
            player.incrementStat(Stats.USED.getOrCreateStat(player.getActiveItem().getItem()));
        }
        if (amount >= 3.0f) {
            ((IEntityDataSaver) player).getPersistentData().putBoolean("blocking", true);
            int i = 1 + MathHelper.floor(amount);
            Hand hand = player.getActiveHand();
            player.getActiveItem().damage(i, player, ((ShieldItem) player.getActiveItem().getItem()).getSlotType());
            if (player.getActiveItem().isEmpty()) {
                if (hand == Hand.MAIN_HAND) {
                    player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
                player.clearActiveItem();
                player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + player.getWorld().random.nextFloat() * 0.4f);
            }
        }
    }
}