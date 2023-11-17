package io.github.slz_br.slz_annoying_mod.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static io.github.slz_br.slz_annoying_mod.SlzAnnoyingMod.MOD_ID;

public class ModSounds {

    public static final SoundEvent SOUND_MINE_PLANTED = registerSound("mine_planted");
    public static final SoundEvent MINE_BECOMING_INVISIBLE = registerSound("mine_becoming_invisible");
    public static final SoundEvent MINE_ACTIVATED = registerSound("mine_activated");

    public static final BlockSoundGroup SOUND_BLOCK_SOUND = new BlockSoundGroup(1f, 1f,
            SoundEvents.BLOCK_METAL_BREAK, SoundEvents.BLOCK_METAL_STEP, SOUND_MINE_PLANTED, SoundEvents.BLOCK_METAL_HIT, SoundEvents.BLOCK_METAL_FALL);

    private static SoundEvent registerSound(String name) {
        Identifier id = new Identifier(MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        System.out.println("Registrando sons");
    }

}
