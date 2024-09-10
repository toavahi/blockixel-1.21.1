package net.toavahi.blockixel.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;

public class ModEffects {
    public static RegistryEntry<StatusEffect> ARCHEOLOGY;

    public static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect effect){
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Blockixel.MOD_ID, name), effect);
    }
    public static void registerEffects(){
        ARCHEOLOGY = registerStatusEffect("archeology_effect", new ArcheologyEffect(StatusEffectCategory.BENEFICIAL, 14055480));
    }
}
