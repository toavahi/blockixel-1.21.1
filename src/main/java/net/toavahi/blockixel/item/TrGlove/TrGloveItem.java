package net.toavahi.blockixel.item.TrGlove;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
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
        tooltip.add(Text.of("Charge: " + stack.get(ModDataComponents.SH_CHARGE)));
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
}
