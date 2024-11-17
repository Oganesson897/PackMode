package io.sommers.packmode.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import io.sommers.packmode.api.PackModeChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenExpansion("crafttweaker.events.IEventManager")
public class ExtendedEventManager {

    private static final EventList<PackModeChangedEvent> packModeChangedEventEventList = new EventList<>();

    @ZenMethod
    public static IEventHandle onPackModeChanged(IEventManager manager, IEventHandler<PackModeChangedEvent> event) {
        return packModeChangedEventEventList.add(event);
    }

    @SubscribeEvent()
    public static void onPackModeChanged(PackModeChangedEvent event) {
        if (packModeChangedEventEventList.hasHandlers()) {
            packModeChangedEventEventList.publish(event);
        }
    }

}
