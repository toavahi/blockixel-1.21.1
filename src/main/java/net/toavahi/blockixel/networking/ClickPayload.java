package net.toavahi.blockixel.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static net.toavahi.blockixel.Blockixel.MOD_ID;

public record ClickPayload(int done) implements CustomPayload {
    public static final CustomPayload.Id<ClickPayload> ID = new CustomPayload.Id<>(Identifier.of(MOD_ID, "click"));
    public static final PacketCodec<RegistryByteBuf, ClickPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, ClickPayload::done, ClickPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
