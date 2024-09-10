package net.toavahi.blockixel.mixin;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = {"<init>"}, at = {@At(value = "invoke", ordinal = 0,
                    target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V")})
    private void addTrGlove(CallbackInfo info) {
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Blockixel.MOD_ID, "tr_glove_3d")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Blockixel.MOD_ID, "tr_glove_3d_1")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Blockixel.MOD_ID, "tr_glove_3d_2")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Blockixel.MOD_ID, "tr_glove_3d_3")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Blockixel.MOD_ID, "tr_glove_3d_4")));
    }
}