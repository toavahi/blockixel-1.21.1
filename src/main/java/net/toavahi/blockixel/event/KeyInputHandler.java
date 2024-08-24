package net.toavahi.blockixel.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.toavahi.blockixel.item.AmShield.AmShieldItem;
import net.toavahi.blockixel.networking.ClickPayload;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY = "key.category.blockixel";
    public static KeyBinding rMouseButton;
    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.player != null) {
                if (client.options.attackKey.isPressed() && client.player.isBlocking()
                        && (client.player.getStackInHand(Hand.OFF_HAND).getItem() instanceof AmShieldItem ||
                        client.player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof AmShieldItem)) {
                    ClientPlayNetworking.send(new ClickPayload(GLFW.GLFW_MOUSE_BUTTON_LEFT));
                }
                if (client.options.jumpKey.wasPressed() && (client.player.getStackInHand(Hand.OFF_HAND).getItem() instanceof AmShieldItem ||
                        client.player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof AmShieldItem)
                        && client.player.isBlocking() && !client.player.isOnGround()) {
                    ClientPlayNetworking.send(new ClickPayload(GLFW.GLFW_KEY_SPACE));
                }
            }
        });
    }
    public static void register(){
        //rMouseButton = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.blockixel.rc", InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT, KEY_CATEGORY));
        registerKeyInputs();
    }
}
