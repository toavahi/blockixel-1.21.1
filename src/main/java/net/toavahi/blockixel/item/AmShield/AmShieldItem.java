package net.toavahi.blockixel.item.AmShield;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.item.TrGlove.TrGloveItem;
import net.toavahi.blockixel.util.IEntityDataSaver;
import net.toavahi.blockixel.util.ModDataComponents;
import net.toavahi.blockixel.util.PlayerMovement;

import java.util.List;


public class AmShieldItem extends ShieldItem implements Equipment {
    public int sh_charge = 0;

    public AmShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public void usageTick(World world, LivingEntity player, ItemStack stack, int remainingUseTicks) {
        if(!world.isClient()) {
            //dash
            if(((IEntityDataSaver) player).getPersistentData().getInt("click") == 0
                    && stack.get(ModDataComponents.SH_CHARGE) >= 10 * 60 * 20){
                //inject trGlove charge
                if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof TrGloveItem
                    && player.getStackInHand(Hand.MAIN_HAND).get(ModDataComponents.SH_CHARGE) <= 10 * 60 * 20){
                    player.getStackInHand(Hand.MAIN_HAND).set(ModDataComponents.SH_CHARGE, player.getStackInHand(Hand.MAIN_HAND).get(ModDataComponents.SH_CHARGE) + (10 * 60 * 20));
                } else {
                    PlayerMovement.dash(((PlayerEntity) player));
                }
                sh_charge -= 10 * 60 * 20;
                ((IEntityDataSaver) player).getPersistentData().putInt("click", -1);
            }
            //parry
            if (stack.getItem().getMaxUseTime(stack, player) - remainingUseTicks < 20 &&
                    ((IEntityDataSaver) player).getPersistentData().getBoolean("blocking")){
                ((IEntityDataSaver) player).getPersistentData().putBoolean("blocking", false);
                sh_charge = 20 * 60 * 20;
                Blockixel.LOGGER.info("parry");
            }
            //jump
            if(((IEntityDataSaver) player).getPersistentData().getInt("click") == 32
                    && stack.get(ModDataComponents.SH_CHARGE) >= 10 * 60 * 20){
                sh_charge -= 10 * 60 * 20;
                ((IEntityDataSaver) player).getPersistentData().putInt("click", -1);
                PlayerMovement.jump(((PlayerEntity) player));
            }
        }
        super.usageTick(world, player, stack, remainingUseTicks);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.blockixel.am_shield.tooltip").append(String.valueOf(stack.get(ModDataComponents.SH_CHARGE))));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int a = 0;
        PlayerEntity player = ((PlayerEntity) entity);
        if(sh_charge < 20 * 60 * 20) {
            if (player.isSprinting()) {
                a = 4;
            } else if(player.isSneaking()){
                a = 1;
            } else if (player.isSwimming()) {
                a = 3;
            } else if (player.isCrawling()) {
                a = 1;
            } else if (player.isUsingRiptide()) {
                a = 5;
            } else if (((IEntityDataSaver) player).getPersistentData().getDouble("prevPosX") != player.getX()
                    || ((IEntityDataSaver) player).getPersistentData().getDouble("prevPosY") != player.getY()
                    || ((IEntityDataSaver) player).getPersistentData().getDouble("prevPosZ") != player.getZ()) {
                a = 2;
                ((IEntityDataSaver) player).getPersistentData().putDouble("prevPosX", player.getX());
                ((IEntityDataSaver) player).getPersistentData().putDouble("prevPosY", player.getY());
                ((IEntityDataSaver) player).getPersistentData().putDouble("prevPosZ", player.getZ());
            }
            sh_charge += a;
        }
        stack.set(ModDataComponents.SH_CHARGE, sh_charge + stack.get(ModDataComponents.SH_CHARGE));
        if(stack.get(ModDataComponents.SH_CHARGE) > 20 * 60 * 20){
            stack.set(ModDataComponents.SH_CHARGE, 20 * 60 * 20);
        }
        sh_charge = 0;
    }
}
