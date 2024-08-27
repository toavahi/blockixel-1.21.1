package net.toavahi.blockixel.event;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.toavahi.blockixel.util.IEntityDataSaver;

public class PlayerTickHandler implements ServerTickEvents.StartTick {
    int time;
    boolean checkClick;

    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player: server.getPlayerManager().getPlayerList()){
            World world = player.getWorld();
            BlockPos pos = player.getBlockPos();
            if(((IEntityDataSaver) player).getPersistentData().getInt("click") != -1){
                if(!checkClick){
                    checkClick = true;
                    time = (int) world.getTime();
                }
                if(world.getTime() - time > 1){
                    checkClick = false;
                    ((IEntityDataSaver) player).getPersistentData().putInt("click", -1);
                }
            }

        }
    }

}

