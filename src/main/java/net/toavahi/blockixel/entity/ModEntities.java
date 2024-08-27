package net.toavahi.blockixel.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.toavahi.blockixel.entity.TrGloveChargeEntity.TrGloveChargeEntity;

public class ModEntities {
    public static final EntityType<TrGloveChargeEntity> CHARGE_ENTITY = register("charge_projectile",
            EntityType.Builder.<TrGloveChargeEntity>create(TrGloveChargeEntity::new, SpawnGroup.MISC)
            .dimensions(0.5F, 0.5F));

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, id, type.build(id));
    }
}
