package net.toavahi.blockixel.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.toavahi.blockixel.Blockixel;

public class ModSounds {

    public static final RegistryEntry<SoundEvent> AM_MONOCLE_ON = registerSoundEvent("am_monocle_on");

    public static RegistryEntry<SoundEvent> registerSoundEvent(String name){
        return Registry.registerReference(Registries.SOUND_EVENT, Identifier.of(Blockixel.MOD_ID, name), SoundEvent.of(Identifier.of(Blockixel.MOD_ID, name)));
    }

    public static void registerSounds(){
        Blockixel.LOGGER.info("register sounds " + Blockixel.MOD_ID);
    }
}
