package io.sommers.packmode;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PackModeNetwork {

    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("packmode");

    private PackModeNetwork() {}

    public static void sendTo(IMessage msg, EntityPlayerMP player) {
        CHANNEL.sendTo(msg, player);
    }

}
