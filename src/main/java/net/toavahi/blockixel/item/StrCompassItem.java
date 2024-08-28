package net.toavahi.blockixel.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.toavahi.blockixel.util.ModTags;

import java.util.Optional;

public class StrCompassItem extends CompassItem {
    public StrCompassItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            BlockPos setPos = ((ServerWorld) world).locateStructure(ModTags.Structures.SCULK_TEMPLE, user.getBlockPos(), 100, false);
            if(setPos != null) {
                // this might be used later TagKey<Structure> tag = ModTags.Structures.SCULK_TEMPLE;
                LodestoneTrackerComponent track = new LodestoneTrackerComponent(Optional.of(GlobalPos.create(world.getRegistryKey(), setPos)), true);
                user.getStackInHand(hand).set(DataComponentTypes.LODESTONE_TRACKER, track);
                world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.BLOCKS);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            LodestoneTrackerComponent lodestoneTrackerComponent = stack.get(DataComponentTypes.LODESTONE_TRACKER);
            if (lodestoneTrackerComponent == null) {
                stack.set(DataComponentTypes.LODESTONE_TRACKER, new LodestoneTrackerComponent(Optional.empty(), true));
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }
}
