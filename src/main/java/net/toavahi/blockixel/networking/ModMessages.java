package net.toavahi.blockixel.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModMessages {
    public static void registerPayloads(){
        PayloadTypeRegistry.playC2S().register(ClickPayload.ID, ClickPayload.CODEC);
    }
}
