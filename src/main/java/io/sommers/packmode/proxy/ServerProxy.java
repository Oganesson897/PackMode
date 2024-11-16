package io.sommers.packmode.proxy;

import io.sommers.packmode.PackMode;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Mod.EventBusSubscriber(Side.SERVER)
public class ServerProxy extends CommonProxy<EntityPlayerMP> {
    @Override
    public List<EntityPlayerMP> getPlayers() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
    }

    @SubscribeEvent
    public static void syncConfigValues(@NotNull ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(PackMode.MOD_ID)) {
            ConfigManager.sync(PackMode.MOD_ID, Config.Type.INSTANCE);
        }
    }

}
