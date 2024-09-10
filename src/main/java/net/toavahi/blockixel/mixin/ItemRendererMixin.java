package net.toavahi.blockixel.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.item.ModItems;
import net.toavahi.blockixel.util.ModDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useTrGloveModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(ModItems.TREASURE_GLOVE) && renderMode != ModelTransformationMode.GUI) {
            String name = "tr_glove_3d";
            switch(stack.get(ModDataComponents.GLOVE_ITEM)){
                case 0 -> name = "tr_glove_3d";
                case 1 -> name = "tr_glove_3d_1";
                case 2 -> name = "tr_glove_3d_2";
                case 3 -> name = "tr_glove_3d_3";
                case 4 -> name = "tr_glove_3d_4";
            }
            return ((ItemRendererAccessor) this).blockixel$getModels().getModelManager().getModel(new ModelIdentifier(Identifier.of(Blockixel.MOD_ID, name), "inventory"));
        }
        return value;
    }
}
