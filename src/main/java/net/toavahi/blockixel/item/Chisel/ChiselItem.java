package net.toavahi.blockixel.item.Chisel;

import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.ActionResult;

public class ChiselItem extends ToolItem {
    public ChiselItem(Settings settings) {
        super(ToolMaterials.DIAMOND, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        return ActionResult.SUCCESS;
    }
}
