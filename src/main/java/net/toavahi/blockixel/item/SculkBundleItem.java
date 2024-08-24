package net.toavahi.blockixel.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;


public class SculkBundleItem extends BundleItem {

    public SculkBundleItem(Item.Settings settings) {
        super(settings);
    }

    public static float getAmountFilled(ItemStack stack) {
        BundleContentsComponent bundleContentsComponent = stack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT);
        return bundleContentsComponent.getOccupancy().floatValue();
    }
    public static boolean addOnDeath(ItemStack bundle, ItemStack stack){
        boolean worked = false;
        if(getAmountFilled(bundle) < 1.0) {
            BundleContentsComponent bundleContentsComponent = bundle.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (bundleContentsComponent != null) {
                BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
                if (!stack.isEmpty()) {
                    builder.add(stack);
                    worked = true;
                }
                bundle.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
            }
        }
        return worked;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            BundleContentsComponent bundleContentsComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (bundleContentsComponent == null) {
                return false;
            } else {
                BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
                if (!otherStack.isEmpty()) {
                    int i = builder.add(otherStack);
                    if (i > 0) {
                        player.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + player.getWorld().getRandom().nextFloat() * 0.4F);
                    }
                }
                stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType == ClickType.RIGHT) {
            BundleContentsComponent bundleContentsComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (bundleContentsComponent != null) {
                ItemStack itemStack = slot.getStack();
                BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
                if (!itemStack.isEmpty() && itemStack.getItem().canBeNested()) {
                    int i = builder.add(slot, player);
                    if (i > 0) {
                        player.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + player.getWorld().getRandom().nextFloat() * 0.4F);
                    }
                }
                stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
                return true;
            }
        }
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (dropAllBundledItems(itemStack, user)) {
            user.playSound(SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + user.getWorld().getRandom().nextFloat() * 0.4F);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    private static boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {
        BundleContentsComponent bundleContentsComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent != null && !bundleContentsComponent.isEmpty()) {
            stack.set(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT);
            if (player instanceof ServerPlayerEntity) {
                bundleContentsComponent.iterateCopy().forEach(stackx ->{
                    if(stackx.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(Blockixel.MOD_ID, "stack_1")))){
                        stackx.set(DataComponentTypes.MAX_STACK_SIZE, 1);
                    }
                    if(stackx.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(Blockixel.MOD_ID, "stack_16")))){
                        stackx.set(DataComponentTypes.MAX_STACK_SIZE, 16);
                    }
                    player.dropItem(stackx, true);
                });
            }
            return true;
        } else {
            return false;
        }
    }
}
