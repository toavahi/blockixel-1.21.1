package net.toavahi.blockixel.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.item.AmShield.AmShieldItem;
import net.toavahi.blockixel.util.IEntityDataSaver;

public class PlayerTickHandler implements ServerTickEvents.StartTick {
    int time;
    boolean checkClick;
    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player: server.getPlayerManager().getPlayerList()){
            World world = player.getWorld();
            BlockPos pos = player.getBlockPos();
            int charge = ((IEntityDataSaver) player).getPersistentData().getInt("am_sh_charge");
            if(player.getStackInHand(Hand.OFF_HAND).getItem() instanceof AmShieldItem ||
                    player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof AmShieldItem){
                ((IEntityDataSaver) player).getPersistentData().putInt("am_sh_charge", charge + getA(player, charge));
            }
            if(((IEntityDataSaver) player).getPersistentData().getInt("click") != -1){
                if(!checkClick){
                    checkClick = true;
                    time = (int) world.getTime();
                }
                if(world.getTime() - time > 1){
                    checkClick = false;
                    ((IEntityDataSaver) player).getPersistentData().putInt("click", -1);
                }
                Blockixel.LOGGER.info(String.valueOf(world.getTime() - time));
            }
        }
    }

    private static int getA(ServerPlayerEntity player, int charge) {
        int a = 0;
        if(charge < 20 * 60 * 20) {
            if (player.isSprinting()) {
                a = 4;
            } else if (player.isSwimming()) {
                a = 3;
            } else if (player.isCrawling()) {
                a = 1;
            } else if (player.isUsingRiptide()) {
                a = 5;
            } else if (player.isOnGround() && !player.isSprinting() && (player.forwardSpeed > 0.0f || player.sidewaysSpeed > 0.0f)) {
                a = 1;
            }
        }
        return a;
    }
}

