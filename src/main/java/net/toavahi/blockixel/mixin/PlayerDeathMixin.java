package net.toavahi.blockixel.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.toavahi.blockixel.item.SculkBundleItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class PlayerDeathMixin{
    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(DamageSource source, CallbackInfo ci){
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        List<Integer> array = new ArrayList<>();
        for(int i = 0; i < player.getInventory().size(); ++i){
            ItemStack stack = player.getInventory().getStack(i);
            if(stack.getItem() instanceof SculkBundleItem){
                array.add(i);
            }
        }
        int b = 0;
        for(int i = player.getInventory().size() - 1; i > -1; --i) {
            if (b < array.size()) {
                ItemStack stack = player.getInventory().getStack(i);
                ItemStack bundle = player.getInventory().getStack(array.get(b));
                if (!(stack.getItem() instanceof SculkBundleItem)) {
                    if (stack.get(DataComponentTypes.MAX_STACK_SIZE) != null) {
                        stack.set(DataComponentTypes.MAX_STACK_SIZE, 64);
                    }
                    SculkBundleItem.addOnDeath(bundle, stack);
                    if ((SculkBundleItem.getAmountFilled(bundle) == 1.0 || i == 0) && inventoryCheck(player)) {
                        b++;
                        i = player.getInventory().size();
                    }
                }
            }
        }
    }

    private boolean inventoryCheck(ServerPlayerEntity player) {
        for(int i = player.getInventory().size() - 1; i > -1; --i){
            if(!(player.getInventory().getStack(i).getItem() instanceof SculkBundleItem)
                    && !(player.getInventory().getStack(i).getItem() instanceof AirBlockItem)){
                return true;
            }
        }
        return false;
    }
}