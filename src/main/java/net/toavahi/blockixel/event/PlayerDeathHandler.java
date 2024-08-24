package net.toavahi.blockixel.event;

import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerDeathHandler implements net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.CopyFrom {
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {

    }
}
