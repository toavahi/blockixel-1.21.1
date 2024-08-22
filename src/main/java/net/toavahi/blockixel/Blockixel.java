package net.toavahi.blockixel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.toavahi.blockixel.event.ClickPayloadHandler;
import net.toavahi.blockixel.event.PlayerTickHandler;
import net.toavahi.blockixel.item.ModItems;

import net.toavahi.blockixel.networking.ClickPayload;
import net.toavahi.blockixel.networking.ModMessages;
import net.toavahi.blockixel.util.IEntityDataSaver;
import net.toavahi.blockixel.util.PlayerMovement;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class Blockixel implements ModInitializer {
	public static final String MOD_ID = "blockixel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
		ModMessages.registerPayloads();
		ServerPlayNetworking.registerGlobalReceiver(ClickPayload.ID, new ClickPayloadHandler());
		LOGGER.info("Happy playing with my mod day! :)");
	}
}