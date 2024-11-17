package io.sommers.packmode.network;

import io.netty.buffer.ByteBuf;
import io.sommers.packmode.PMConfig;
import io.sommers.packmode.api.PackModeAPI;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PackModeChangeMessage implements IMessage {

    int newMode;

    public PackModeChangeMessage() {}

    public PackModeChangeMessage(int newMode) {
        this.newMode = newMode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        newMode = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(newMode);
    }

    public static class PackModeChangeMessageHandler implements IMessageHandler<PackModeChangeMessage, IMessage> {
        @Override
        public IMessage onMessage(PackModeChangeMessage message , MessageContext ctx) {
            int ordinal = message.newMode;
            if (ctx.side == Side.CLIENT) {
                PackModeAPI.getInstance().setNextRestartPackMode(PMConfig.byOrdinal(ordinal));
            }
            return null;
        }
    }
}
