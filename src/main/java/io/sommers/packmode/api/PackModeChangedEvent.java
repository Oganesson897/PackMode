package io.sommers.packmode.api;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@Cancelable
@ZenRegister
@ZenClass("mods.packmode.PackModeChangedEvent")
public class PackModeChangedEvent extends Event {
    private final String nextRestartPackMode;

    PackModeChangedEvent(String nextRestartPackMode) {
        this.nextRestartPackMode = nextRestartPackMode;
    }

    @ZenGetter
    public String getNextRestartPackMode() {
        return this.nextRestartPackMode;
    }
}
