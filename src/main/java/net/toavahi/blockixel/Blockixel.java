package net.toavahi.blockixel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.toavahi.blockixel.block.AmDispenserBlock;
import net.toavahi.blockixel.block.ModBlocks;
import net.toavahi.blockixel.block.blockEntity.AmDispenserBlockEntity;
import net.toavahi.blockixel.block.blockEntity.ModBlockEntities;
import net.toavahi.blockixel.event.ClickPayloadHandler;
import net.toavahi.blockixel.event.PlayerDeathHandler;
import net.toavahi.blockixel.event.PlayerTickHandler;
import net.toavahi.blockixel.item.ModItems;

import net.toavahi.blockixel.networking.ClickPayload;
import net.toavahi.blockixel.networking.ModMessages;
import net.toavahi.blockixel.sound.ModSounds;
import net.toavahi.blockixel.util.ModDataComponents;
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
		ModDataComponents.registerComponents();
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if(!world.isClient() && !player.isSpectator() && player.getStackInHand(hand).getItem() == Items.AMETHYST_CLUSTER
					&& world.getBlockState(hitResult.getBlockPos()).getBlock() == Blocks.DISPENSER) {
				Direction dir = world.getBlockState(hitResult.getBlockPos()).get(DispenserBlock.FACING);
				int dirY = dir == Direction.UP ? -1 : 1;
				world.setBlockState(hitResult.getBlockPos().add(0, dirY, 0), Blocks.AMETHYST_CLUSTER.getDefaultState());
				world.setBlockState(hitResult.getBlockPos(), ModBlocks.AM_DISPENSER.getDefaultState().with(AmDispenserBlock.FACING, dir));
				player.getStackInHand(hand).decrement(player.isCreative() ? 0 : 1);
				world.playSound(null, hitResult.getBlockPos(), SoundEvents.BLOCK_AMETHYST_CLUSTER_PLACE, SoundCategory.BLOCKS);
			}
            return ActionResult.PASS;
        });
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if(!world.isClient() && !player.isSpectator() && state.getBlock() == Blocks.AMETHYST_CLUSTER
				&& world.getBlockState(pos.add(0, -1, 0)).getBlock() == ModBlocks.AM_DISPENSER){
				Direction dir = world.getBlockState(pos.add(0, -1, 0)).get(AmDispenserBlock.FACING);
				world.setBlockState(pos.add(0, -1, 0), Blocks.DISPENSER.getDefaultState().with(DispenserBlock.FACING, dir));
			}
		});
		ModBlockEntities.registerModEntities();
		LOGGER.info("Happy playing with my mod day!");
	}
}