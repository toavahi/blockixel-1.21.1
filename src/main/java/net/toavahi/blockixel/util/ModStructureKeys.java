package net.toavahi.blockixel.util;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.toavahi.blockixel.Blockixel;

public interface ModStructureKeys {
    RegistryKey<Structure> AM_SH_BASE = of("am_sh_base");
    RegistryKey<Structure> ARCH_BASE = of("arch_base");
    RegistryKey<Structure> SCULK_TEMPLE = of("sculk_temple");

    RegistryKey<Registry<Structure>> STRUCTURE = RegistryKey.ofRegistry(Identifier.of(Blockixel.MOD_ID, "worldgen/structure"));

    private static RegistryKey<Structure> of(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, Identifier.of(Blockixel.MOD_ID, id));
    }
}
