package net.toavahi.blockixel;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.toavahi.blockixel.datagen.ModModelProvider;
import net.toavahi.blockixel.datagen.ModLootTableGenerator;

public class BlockixelDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModLootTableGenerator::new);
	}
}
