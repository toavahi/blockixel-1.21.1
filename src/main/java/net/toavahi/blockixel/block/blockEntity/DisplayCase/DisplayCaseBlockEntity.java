package net.toavahi.blockixel.block.blockEntity.DisplayCase;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.toavahi.blockixel.effect.ModEffects;
import net.toavahi.blockixel.block.DisplayCaseBlock;
import net.toavahi.blockixel.block.blockEntity.ModBlockEntities;
import net.toavahi.blockixel.item.ModItems;
import net.toavahi.blockixel.util.ImplementedInventory;

import java.util.List;

public class DisplayCaseBlockEntity extends BlockEntity implements ImplementedInventory {
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public int degree;
    int tick;

    public DisplayCaseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DISPLAY_CASE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.inventory, true, registryLookup);
        return nbtCompound;
    }

    @Override
    public void markDirty() {
        if(world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        tick = tick > 240000 ? tick + 1 : 0;
        if (state.get(DisplayCaseBlock.AIR)) {
            degree = degree < 360 ? degree + 1 : 0;
        }
        if(world != null && tick % 40 == 0 && world.getBlockEntity(pos) != null) {
            if (((DisplayCaseBlockEntity) world.getBlockEntity(pos)).inventory.getFirst().getItem() == ModItems.ARCH_BOOK) {
                List<PlayerEntity> target = world.getEntitiesByClass(PlayerEntity.class, new Box(pos.getX() + 10, pos.getY() + 10, pos.getZ() + 10,
                        pos.getX() - 10, pos.getY() - 10, pos.getZ() - 10), targets -> targets != null);
                for (PlayerEntity player : target) {
                    player.addStatusEffect(new StatusEffectInstance(ModEffects.ARCHEOLOGY, 40));
                }
            }
        }
    }

    public void addItem(ItemStack stack) {
        if(world != null) {
            ItemStack itemStack = this.inventory.getFirst();
            if (itemStack.isEmpty()) {
                this.inventory.set(0, stack.split(1));
            } else {
                ItemScatterer.spawn(world, pos, this.inventory);
                this.inventory.set(0, ItemStack.EMPTY);
            }
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Emitter.of(this.getCachedState()));
        }
        this.markDirty();
    }
}
