package net.toavahi.blockixel.item.LeadPack;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.toavahi.blockixel.item.AmArmorMaterial;
import net.toavahi.blockixel.util.ModDataComponents;

public class LeadPackItem extends ArmorItem implements Equipment {

    public LeadPackItem(Settings settings) {
        super(AmArmorMaterial.AMETHYST, Type.CHESTPLATE, settings);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getStackInHand(hand).get(ModDataComponents.PACK_SIZE) == 1 && user.getStackInHand(hand).get(ModDataComponents.PACK_INSIDE).get(0).isEmpty()){
            user.getStackInHand(hand).get(ModDataComponents.PACK_INSIDE).add(new ItemStack(Items.NETHERITE_CHESTPLATE));
        }
        return super.use(world, user, hand);
    }
}
