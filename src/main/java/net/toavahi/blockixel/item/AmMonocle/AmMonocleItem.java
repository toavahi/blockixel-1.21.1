package net.toavahi.blockixel.item.AmMonocle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.sound.ModSounds;
import net.toavahi.blockixel.util.IEntityDataSaver;
import net.toavahi.blockixel.util.ModTags;

import java.util.List;

public class AmMonocleItem extends ArmorItem implements Equipment {

    public AmMonocleItem(Settings settings) {
        super(MonocleArmorMaterial.MONOCLE, Type.HELMET, settings);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.blockixel.am_monocle.tooltip").append(String.valueOf(0)));
    }

    @Override
    public RegistryEntry<SoundEvent> getEquipSound() {
        return ModSounds.AM_MONOCLE_ON;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && slot == EquipmentSlot.HEAD.getEntitySlotId()) {
            if (((IEntityDataSaver) entity).getPersistentData().getInt("scan_time") > 20 * 300) {
                ((IEntityDataSaver) entity).getPersistentData().putInt("scan_time", 0);
                int counter = 0;
                int[][] posList = new int[125][3];
                for (int x = -5; x < 5; ++x) {
                    for (int y = -5; y < 5; ++y) {
                        for (int z = -5; z < 5; ++z) {
                            BlockPos pos = new BlockPos(entity.getBlockX() + x, entity.getBlockY() + y, entity.getBlockZ() + z);
                            if (world.getBlockState(pos).isIn(ModTags.Blocks.ORE_TAG)) {
                                posList[counter] = new int[]{x, y, z};
                                counter++;
                            }
                        }
                    }
                }
                int c = 0;
                int small = 1000;
                int smallCounter = 0;
                for (int[] block : posList) {
                    if (small > Math.abs(block[0]) + Math.abs(block[1]) + Math.abs(block[2])
                            && Math.abs(block[0]) + Math.abs(block[1]) + Math.abs(block[2]) > 0) {
                        small = Math.abs(block[0]) + Math.abs(block[1]) + Math.abs(block[2]);
                        smallCounter = c;
                    }
                    c++;
                }
                BlockPos pos = new BlockPos(entity.getBlockX() + posList[smallCounter][0], entity.getBlockY() + posList[smallCounter][1], entity.getBlockZ() + posList[smallCounter][2]);
                int k = 0;
                for (int i = 0; i < 5; ++i) {
                    k = ((ServerWorld) world).spawnParticles(ParticleTypes.HAPPY_VILLAGER,
                            ((pos.getX() - entity.getX()) / 5 * i) + entity.getX(),
                            ((pos.getY() - entity.getY()) / 5 * i) + entity.getY(),
                            ((pos.getZ() - entity.getZ()) / 5 * i) + entity.getZ(), 1,
                            0.0, 0.0, 0.0, 0.0);
                }
                Blockixel.LOGGER.info(String.valueOf(k));
                entity.sendMessage(Text.of(String.valueOf(posList[0][0])));
                world.playSound(null, pos, SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.BLOCKS);
            }
        }
        ((IEntityDataSaver) entity).getPersistentData().putInt("scan_time", ((IEntityDataSaver) entity).getPersistentData().getInt("scan_time") + 1);
    }
}
