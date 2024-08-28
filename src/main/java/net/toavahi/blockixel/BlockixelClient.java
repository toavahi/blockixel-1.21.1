package net.toavahi.blockixel;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.item.CompassItem;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.entity.ModEntities;
import net.toavahi.blockixel.entity.TrGloveChargeEntity.TrGloveChargeEntityRenderer;
import net.toavahi.blockixel.event.KeyInputHandler;
import net.toavahi.blockixel.item.AmShield.AmShieldRenderer;
import net.toavahi.blockixel.item.ModItems;
import net.toavahi.blockixel.item.AmShield.AmShieldModel;
import net.toavahi.blockixel.item.SculkBundleItem;

public class BlockixelClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.AM_SHIELD, new AmShieldRenderer(AmShieldModel.getTexturedModelData().createModel()));
        KeyInputHandler.register();
        ModelPredicateProviderRegistry.register(ModItems.SCULK_BUNDLE, Identifier.ofVanilla("filled"), (itemStack, clientWorld, livingEntity, i) -> SculkBundleItem.getAmountFilled(itemStack));
        EntityRendererRegistry.register(ModEntities.CHARGE_ENTITY, TrGloveChargeEntityRenderer::new);
        ModelPredicateProviderRegistry.register(ModItems.STR_COMPASS, Identifier.ofVanilla("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> {
            LodestoneTrackerComponent lodestoneTrackerComponent = stack.get(DataComponentTypes.LODESTONE_TRACKER);
            return lodestoneTrackerComponent != null ? lodestoneTrackerComponent.target().orElse(null) : CompassItem.createSpawnPos(world);
        }));
    }
}
