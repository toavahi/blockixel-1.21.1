package net.toavahi.blockixel.item.Chisel;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.block.blockEntity.BrushObsiBlockEntity;
import net.toavahi.blockixel.datagen.ModAdvancementProvider;
import net.toavahi.blockixel.item.ModItems;

public class ChiselItem extends ToolItem {
    public ChiselItem(Settings settings) {
        super(ToolMaterials.DIAMOND, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if(!world.isClient()) {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            if (world.getBlockEntity(context.getBlockPos()) instanceof BrushObsiBlockEntity obsiBlock) {
                obsiBlock.brush(world.getTime(), player, context.getSide());
                world.addBlockBreakParticles(context.getBlockPos(), world.getBlockState(context.getBlockPos()));
                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS);
                player.getStackInHand(player.getActiveHand()).damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand()));
            }
            Registry<Advancement> advancementRegistry = world.getRegistryManager().get(RegistryKeys.ADVANCEMENT);
            Advancement advancement = advancementRegistry.get(Identifier.ofVanilla("story/mine_stone"));
            AdvancementEntry advancementEntry = new AdvancementEntry(Identifier.ofVanilla("story/mine_stone"), advancement);
            if(world.getBlockState(context.getBlockPos()).getBlock() == Blocks.WEATHERED_COPPER /*&& player.getAdvancementTracker().getProgress(advancementEntry).isDone()*/){
                world.setBlockState(context.getBlockPos(), Blocks.WEATHERED_CUT_COPPER.getDefaultState());
                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_COPPER_BREAK, SoundCategory.BLOCKS);
                player.getStackInHand(player.getActiveHand()).damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand()));
                world.spawnEntity(new ItemEntity(world, context.getBlockPos().getX(), context.getBlockPos().getY() + 1,
                        context.getBlockPos().getZ(), new ItemStack(ModItems.OX_BOLT)));
            }
        }
        return ActionResult.SUCCESS;
    }
}
