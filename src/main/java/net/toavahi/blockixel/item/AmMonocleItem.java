package net.toavahi.blockixel.item;

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
import net.toavahi.blockixel.sound.ModSounds;
import net.toavahi.blockixel.util.ModDataComponents;
import net.toavahi.blockixel.util.ModTags;

import java.util.List;

public class AmMonocleItem extends ArmorItem implements Equipment {
    int scan_time = 0;
    int scan_tool_time = 0;
    boolean particle_approved = false;
    BlockPos particle_pos;

    public AmMonocleItem(Settings settings) {
        super(AmArmorMaterial.AMETHYST, Type.HELMET, settings);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.blockixel.am_monocle.tooltip").append(String.valueOf(scan_tool_time)));
    }

    @Override
    public RegistryEntry<SoundEvent> getEquipSound() {
        return ModSounds.AM_MONOCLE_ON;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && slot == EquipmentSlot.HEAD.getEntitySlotId()) {
            scan_time = stack.get(ModDataComponents.SCAN_TIME);
            int particle_time = 0;
            boolean time_update = false;
            if (scan_time > 20 * 180) {
                stack.set(ModDataComponents.SCAN_TIME, 0);
                scan_time = 0;
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
                    if (!block.equals(new int[]{0, 0, 0}) && small > Math.abs(block[0]) + Math.abs(block[1]) + Math.abs(block[2])
                            && Math.abs(block[0]) + Math.abs(block[1]) + Math.abs(block[2]) > 0) {
                        small = Math.abs(block[0]) + Math.abs(block[1]) + Math.abs(block[2]);
                        smallCounter = c;
                    }
                    c++;
                }
                if(!posList[smallCounter].equals(new int[]{0, 0, 0})) {
                    particle_pos = new BlockPos(entity.getBlockX() + posList[smallCounter][0], entity.getBlockY() + posList[smallCounter][1], entity.getBlockZ() + posList[smallCounter][2]);
                    particle_approved = true;
                    time_update = true;
                    world.playSound(null, particle_pos, SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.BLOCKS);
                }
            }
            stack.set(ModDataComponents.SCAN_TIME, scan_time + 1);
            if((180 * 20 - scan_time) % 20 == 0){
                scan_tool_time = (180 * 20 - scan_time) / 20;
            }
            if(particle_approved){
                if(time_update){
                    particle_time = (int) world.getTime();
                }
                if(particle(((ServerWorld) world), entity, particle_pos, particle_time)){
                    particle_approved = false;
                }
            }
        }
    }
    private boolean particle(ServerWorld world, Entity entity, BlockPos pos, int start){
        if(world.getTime() - start < 200 && ((world.getTime() - start) & 10) == 0){
            for (int i = 0; i < 10; ++i) {
                world.spawnParticles(ParticleTypes.HAPPY_VILLAGER,
                        ((pos.getX() - entity.getX()) / 10 * i) + entity.getX(),
                        ((pos.getY() - entity.getEyeY()) / 10 * i) + entity.getEyeY(),
                        ((pos.getZ() - entity.getZ()) / 10 * i) + entity.getZ(), 1,
                        0.0, 0.0, 0.0, 0.0);
            }
        }
        return world.getTime() - start > 200;
    }
}
