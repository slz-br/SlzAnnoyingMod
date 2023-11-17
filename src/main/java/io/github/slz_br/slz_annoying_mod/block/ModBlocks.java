package io.github.slz_br.slz_annoying_mod.block;

import io.github.slz_br.slz_annoying_mod.block.custom.MineBlock;
import io.github.slz_br.slz_annoying_mod.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static io.github.slz_br.slz_annoying_mod.SlzAnnoyingMod.MOD_ID;

public class ModBlocks {

    public static Block MINE = registerBlock("mine_block", new MineBlock(FabricBlockSettings.create().breakInstantly().collidable(true).sounds(ModSounds.SOUND_BLOCK_SOUND)));
    public static Item MINE_ITEM = registerBlockItem("mine", MINE);

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerAll() {
        System.out.println("registando");
    }

}
