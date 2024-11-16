package io.sommers.packmode.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import io.sommers.packmode.compat.Compat;
import net.minecraftforge.common.MinecraftForge;

public class CraftTweakerCompat extends Compat {
    @Override
    public void preInit() {
        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction("packmode", PackModePreprocessor::new);
        MinecraftForge.EVENT_BUS.register(ExtendedEventManager.class);
    }
}
