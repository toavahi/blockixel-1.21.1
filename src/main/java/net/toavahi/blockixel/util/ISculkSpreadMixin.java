package net.toavahi.blockixel.util;

import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public interface ISculkSpreadMixin {
    int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos,
               net.minecraft.util.math.random.Random random, SculkSpreadManager spreadManager, boolean convert);
}
