package net.toavahi.blockixel.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.toavahi.blockixel.item.GrapplePack.GrapplePackItem;
import net.toavahi.blockixel.util.ModDataComponents;

public class OxBoltItem extends Item {
    public OxBoltItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof ArmorStandEntity  && entity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof GrapplePackItem){
            ItemStack pack = entity.getEquippedStack(EquipmentSlot.CHEST);
            if(pack.get(ModDataComponents.PACK_SIZE) < 2){
                pack.set(ModDataComponents.PACK_SIZE, pack.get(ModDataComponents.PACK_SIZE) + 1);
                user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS);
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
