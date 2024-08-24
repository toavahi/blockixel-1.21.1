package net.toavahi.blockixel.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.item.ModItems;
import net.toavahi.blockixel.networking.ClickPayload;
import net.toavahi.blockixel.util.IEntityDataSaver;
import org.lwjgl.glfw.GLFW;

public class ClickPayloadHandler implements ServerPlayNetworking.PlayPayloadHandler<ClickPayload> {
    int time;
    @Override
    public void receive(ClickPayload payload, ServerPlayNetworking.Context context) {
        switch(payload.done()){
            case GLFW.GLFW_MOUSE_BUTTON_LEFT -> {
                ((IEntityDataSaver) context.player()).getPersistentData().putInt("click", 0);
                Blockixel.LOGGER.info("left_click");
            }
            case GLFW.GLFW_KEY_SPACE -> {
                ((IEntityDataSaver) context.player()).getPersistentData().putInt("click", 32);
                Blockixel.LOGGER.info("jump");
            }
        }
    }
}
