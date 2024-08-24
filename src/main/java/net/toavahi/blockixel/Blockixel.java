package net.toavahi.blockixel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.toavahi.blockixel.block.ModBlocks;
import net.toavahi.blockixel.event.ClickPayloadHandler;
import net.toavahi.blockixel.event.PlayerDeathHandler;
import net.toavahi.blockixel.event.PlayerTickHandler;
import net.toavahi.blockixel.item.ModItems;

import net.toavahi.blockixel.networking.ClickPayload;
import net.toavahi.blockixel.networking.ModMessages;
import net.toavahi.blockixel.sound.ModSounds;
import net.toavahi.blockixel.util.ModLootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Blockixel implements ModInitializer {
	public static final String MOD_ID = "blockixel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
		ModMessages.registerPayloads();
		ServerPlayNetworking.registerGlobalReceiver(ClickPayload.ID, new ClickPayloadHandler());
		ModBlocks.registerBlocks();
		ModLootTableModifier.modifyLootTables();
		ModSounds.registerSounds();
		ServerPlayerEvents.COPY_FROM.register(new PlayerDeathHandler());
		//ModDataComponents.registerComponents();
		LOGGER.info("Happy playing with my mod day! ;)");
	}
}