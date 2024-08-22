package net.toavahi.blockixel.item.AmShield;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;
import net.toavahi.blockixel.item.ModItems;
import net.toavahi.blockixel.util.IEntityDataSaver;

public class AmShieldHudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        //RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            client.getProfiler().swap("charge");
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            int x = width / 2;
            if (client.player != null && !client.player.isSpectator()) {
                ClientPlayerEntity player = client.player;
                if (player.getMainHandStack().getItem().equals(ModItems.AM_SHIELD) ||
                        player.getOffHandStack().getItem().equals(ModItems.AM_SHIELD)) {

                    int yshift = 53;
                    int maxAir = player.getMaxAir();
                    int playerAir = Math.min(player.getAir(), maxAir);
                    if (player.getAbilities().creativeMode) yshift -= 17;
                    if (player.isSubmergedIn(FluidTags.WATER) || playerAir < maxAir) yshift += 10;
                    LivingEntity livingEntity = this.getRiddenEntity();
                    if (livingEntity != null) {
                        int i = this.getHeartCount(livingEntity);
                        if (i > 10) {
                            yshift += 10;
                        }
                        if (player.getAbilities().creativeMode) yshift += 17;
                    }
                    if (!client.options.hudHidden) {
                        drawContext.drawTexture(Identifier.of(Blockixel.MOD_ID, "textures/item/am_shield_ui.png"), x + 10, height - yshift, 0, 0, 81, 13);
                        drawContext.drawTexture(Identifier.of(Blockixel.MOD_ID, "textures/item/am_shield_ui.png"), x + 10, height - yshift + 5, 0, 15, (int) (81f * ((((IEntityDataSaver) player).getPersistentData().getInt("charge")) / (20 * 60 * 20))), 5);
                    }
                }
            }
            client.getProfiler().pop();
        }
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        //RenderSystem.enableDepthTest();
    }

    private LivingEntity getRiddenEntity() {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            Entity entity = playerEntity.getVehicle();
            if (entity == null) {
                return null;
            }
            if (entity instanceof LivingEntity) {
                return (LivingEntity) entity;
            }
        }
        return null;
    }

    private int getHeartCount(LivingEntity entity) {
        if (entity == null || !entity.isLiving()) {
            return 0;
        }
        float f = entity.getMaxHealth();
        int i = (int) (f + 0.5f) / 2;
        if (i > 30) {
            i = 30;
        }
        return i;
    }

    private PlayerEntity getCameraPlayer() {
        if (!(MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity)) {
            return null;
        }
        return (PlayerEntity) MinecraftClient.getInstance().getCameraEntity();
    }
}
