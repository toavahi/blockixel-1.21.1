package net.toavahi.blockixel;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.toavahi.blockixel.event.KeyInputHandler;
import net.toavahi.blockixel.item.AmShield.AmShieldRenderer;
import net.toavahi.blockixel.item.ModItems;
import net.toavahi.blockixel.item.AmShield.AmShieldModel;

public class BlockixelClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.AM_SHIELD, new AmShieldRenderer(AmShieldModel.getTexturedModelData().createModel()));
        KeyInputHandler.register();
    }
}
