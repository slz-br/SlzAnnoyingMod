package io.github.slz_br.slz_annoying_mod;

import net.fabricmc.api.ModInitializer;

import static io.github.slz_br.slz_annoying_mod.block.ModBlocks.registerAll;
import static io.github.slz_br.slz_annoying_mod.sound.ModSounds.registerSounds;

public class SlzAnnoyingMod implements ModInitializer {

    public final static String MOD_ID = "slz_annoying_mod";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        registerSounds();
        registerAll();
    }
}
