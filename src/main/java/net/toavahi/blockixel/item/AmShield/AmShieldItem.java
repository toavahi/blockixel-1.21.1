package net.toavahi.blockixel.item.AmShield;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.util.IEntityDataSaver;
import net.toavahi.blockixel.util.PlayerMovement;

import java.util.List;


public class AmShieldItem extends ShieldItem implements Equipment {
    int sh_charge = 0;

    public AmShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public void usageTick(World world, LivingEntity player, ItemStack stack, int remainingUseTicks) {
        if(!world.isClient()) {
            //dash
            if(((IEntityDataSaver) player).getPersistentData().getInt("click") == 0
                    && ((IEntityDataSaver) player).getPersistentData().getInt("charge") >= 20 /** 60 * 20*/){
                PlayerMovement.dash(((PlayerEntity) player));
                ((IEntityDataSaver) player).getPersistentData().putInt("charge", (((IEntityDataSaver) player).getPersistentData().getInt("charge") - 10 /** 60 * 20*/));
                ((IEntityDataSaver) player).getPersistentData().putInt("click", -1);
            }
            //parry
            if (stack.getItem().getMaxUseTime(stack, player) - remainingUseTicks < 20 &&
                    ((IEntityDataSaver) player).getPersistentData().getBoolean("blocking")){
                ((IEntityDataSaver) player).getPersistentData().putBoolean("blocking", false);
                ((IEntityDataSaver) player).getPersistentData().putInt("charge", 20 /** 60 * 20*/);
                Blockixel.LOGGER.info("parry");
            }
            //jump
            if(((IEntityDataSaver) player).getPersistentData().getInt("click") == 32
                    && ((IEntityDataSaver) player).getPersistentData().getInt("charge") >= 20 /** 60 * 10*/){
                ((IEntityDataSaver) player).getPersistentData().putInt("charge", ((IEntityDataSaver) player).getPersistentData().getInt("charge") - 10 /** 60 * 20*/);
                ((IEntityDataSaver) player).getPersistentData().putInt("click", -1);
                PlayerMovement.jump(((PlayerEntity) player));
            }
        }
        super.usageTick(world, player, stack, remainingUseTicks);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.blockixel.am_shield.tooltip").append(String.valueOf(sh_charge)));
        Blockixel.LOGGER.info(String.valueOf(sh_charge));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int a = 0;
        PlayerEntity player = ((PlayerEntity) entity);
        int charge = ((IEntityDataSaver) player).getPersistentData().getInt("charge");
        if(charge < 20 * 60 * 20) {
            if (player.isSprinting()) {
                a = 4;
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
            charge += a;
            if(charge > 20 * 60 * 20){
                charge = 20 * 60 * 20;
            }
            ((IEntityDataSaver) player).getPersistentData().putInt("charge", charge);
            sh_charge = charge;
        }
    }

    public int getCharge(PlayerEntity player) {
        return ((IEntityDataSaver) player).getPersistentData().getInt("charge");
    }
    public void setCharge(PlayerEntity player, int charge){
        ((IEntityDataSaver) player).getPersistentData().putInt("charge", charge);
    }
}
