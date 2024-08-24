package net.toavahi.blockixel.item.TrGlove;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.toavahi.blockixel.item.AmShield.AmShieldItem;

public class TrGloveItem extends Item{
    int gem;
    public TrGloveItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if(!world.isClient() && world.getBlockState(context.getBlockPos()) == Blocks.STONE.getDefaultState()
                && context.getPlayer().getStackInHand(Hand.OFF_HAND).getItem() instanceof AmShieldItem
                && ((AmShieldItem) context.getPlayer().getStackInHand(Hand.OFF_HAND).getItem()).getCharge(context.getPlayer()) > 5){
            world.breakBlock(context.getBlockPos(), false);
            world.setBlockState(context.getBlockPos(), Blocks.GRAVEL.getDefaultState());
            world.playSound(null, context.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.BLOCKS);
        }
        return ActionResult.SUCCESS;
    }
}
