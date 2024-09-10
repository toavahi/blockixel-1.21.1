package net.toavahi.blockixel.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.toavahi.blockixel.entity.TrGloveChargeEntity.TrGloveChargeEntity;
import net.toavahi.blockixel.item.AmShield.AmShieldItem;
import net.toavahi.blockixel.util.ModDataComponents;

import java.util.List;


public class TrGloveItem extends Item{

    public TrGloveItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.blockixel.tr_glove.tooltip_ch").append(String.valueOf(stack.get(ModDataComponents.SH_CHARGE))));
        tooltip.add(Text.translatable("item.blockixel.tr_glove.tooltip_it").append(String.valueOf(stack.get(ModDataComponents.GLOVE_CHARGE))));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            if (user.getStackInHand(Hand.OFF_HAND).getItem() instanceof AmShieldItem && user.isBlocking()
                    && user.getStackInHand(Hand.OFF_HAND).get(ModDataComponents.SH_CHARGE) >= 10 * 60 * 20) {
                user.getStackInHand(Hand.OFF_HAND).set(ModDataComponents.SH_CHARGE, user.getStackInHand(Hand.OFF_HAND).get(ModDataComponents.SH_CHARGE) - (10 * 20 * 60));
                user.getStackInHand(Hand.MAIN_HAND).set(ModDataComponents.SH_CHARGE, user.getStackInHand(Hand.MAIN_HAND).get(ModDataComponents.SH_CHARGE) + (10 * 20 * 60));
            }
            if(user.getStackInHand(Hand.MAIN_HAND).get(ModDataComponents.SH_CHARGE) >= 10 * 60 * 20){
                TrGloveChargeEntity chargeEntity = new TrGloveChargeEntity(world, user.getPos().getX(), user.getEyePos().getY(), user.getPos().getZ());
                chargeEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                world.spawnEntity(chargeEntity);
                user.getStackInHand(Hand.MAIN_HAND).set(ModDataComponents.SH_CHARGE, user.getStackInHand(Hand.MAIN_HAND).get(ModDataComponents.SH_CHARGE) - (10 * 60 * 20));
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if(clickType == ClickType.LEFT){
            if(otherStack.getItem() == Items.DIAMOND){
                otherStack.decrement(1);
                stack.set(ModDataComponents.GLOVE_ITEM, 1);
                stack.set(ModDataComponents.GLOVE_CHARGE, 5);
                return true;
            } else if(otherStack.getItem() == Items.GOLD_INGOT){
                otherStack.decrement(1);
                stack.set(ModDataComponents.GLOVE_ITEM, 2);
                stack.set(ModDataComponents.GLOVE_CHARGE, 3);
                return true;
            } else if(otherStack.getItem() == Items.EMERALD){
                otherStack.decrement(1);
                stack.set(ModDataComponents.GLOVE_ITEM, 3);
                stack.set(ModDataComponents.GLOVE_CHARGE, 4);
                return true;
            } else if(otherStack.getItem() == Items.AMETHYST_SHARD){
                otherStack.decrement(1);
                stack.set(ModDataComponents.GLOVE_ITEM, 4);
                stack.set(ModDataComponents.GLOVE_CHARGE, 4);
                return true;
            }
        }
        return false;
    }
}
