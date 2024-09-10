package net.toavahi.blockixel.block.blockEntity.DisplayCase;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.toavahi.blockixel.item.ModItems;

public class DisplayCaseBlockEntityRenderer implements BlockEntityRenderer<DisplayCaseBlockEntity> {

    public DisplayCaseBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(DisplayCaseBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.scale(0.75f, 0.75f, 0.75f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.degree));
        MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getItems().getFirst().getItem() == ModItems.ARCH_BOOK ? ItemStack.EMPTY : entity.getItems().getFirst(), ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
