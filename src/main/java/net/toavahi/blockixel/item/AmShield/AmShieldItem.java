package net.toavahi.blockixel.item.AmShield;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.util.IEntityDataSaver;
import net.toavahi.blockixel.util.PlayerMovement;


public class AmShieldItem extends ShieldItem implements Equipment {
    public AmShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public void usageTick(World world, LivingEntity player, ItemStack stack, int remainingUseTicks) {
        if(!world.isClient()) {
            //dash
            if(((IEntityDataSaver) player).getPersistentData().getInt("click") == 0
                    && ((IEntityDataSaver) player).getPersistentData().getInt("am_sh_charge") >= 20 /** 60 * 20*/){
                PlayerMovement.dash(((PlayerEntity) player));
                ((IEntityDataSaver) player).getPersistentData().putInt("am_sh_charge", 0);
                ((IEntityDataSaver) player).getPersistentData().putInt("click", -1);
            }
            //parry
            if (stack.getItem().getMaxUseTime(stack, player) - remainingUseTicks < 20 &&
                    ((IEntityDataSaver) player).getPersistentData().getBoolean("blocking")){
                ((IEntityDataSaver) player).getPersistentData().putBoolean("blocking", false);
                ((IEntityDataSaver) player).getPersistentData().putInt("am_sh_charge", 20 * 60 * 20);
                Blockixel.LOGGER.info("parry");
            }
            //jump
            if(((IEntityDataSaver) player).getPersistentData().getInt("click") == 32
                    && ((IEntityDataSaver) player).getPersistentData().getInt("am_sh_charge") >= 20 /** 60 * 10*/){
                ((IEntityDataSaver) player).getPersistentData().putInt("am_sh_charge", 0);
                ((IEntityDataSaver) player).getPersistentData().putInt("click", -1);
                PlayerMovement.jump(((PlayerEntity) player));
            }
        }
        super.usageTick(world, player, stack, remainingUseTicks);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient()) {
            user.sendMessage(Text.of("used"));
        }
        return ActionResult.SUCCESS;
    }
}
