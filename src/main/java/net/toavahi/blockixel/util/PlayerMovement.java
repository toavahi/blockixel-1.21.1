package net.toavahi.blockixel.util;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class PlayerMovement {
    public static void dash(PlayerEntity player){
        if(!player.getWorld().isClient()) {
            float yaw = player.getYaw();
            float pitch = player.getPitch();
            float x = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            float y = /*-MathHelper.sin(pitch * 0.017453292F);*/ 0.6F;
            float z = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            x *= 4.5F / MathHelper.sqrt(x * x + y * y + z * z);
            //y *= 4.5F / MathHelper.sqrt(x * x + y * y + z * z);
            z *= 4.5F / MathHelper.sqrt(x * x + y * y + z * z);
            player.addVelocity(x, y, z);
            player.velocityModified = true;
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_BREEZE_JUMP, SoundCategory.HOSTILE);
            player.sendMessage(Text.of(x + " | " + y + " | " + z));
        }
    }
    public static void jump(PlayerEntity player){
        player.addVelocity(0.0, 0.42, 0.0);
        player.velocityModified = true;
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_BREEZE_JUMP, SoundCategory.HOSTILE);
    }
}
