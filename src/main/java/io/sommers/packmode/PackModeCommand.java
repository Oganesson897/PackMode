package io.sommers.packmode;

import io.sommers.packmode.api.PackModeAPI;
import io.sommers.packmode.network.PackModeChangeMessage;
import joptsimple.internal.Strings;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PackModeCommand extends CommandBase {

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public String getName() {
        return "packmode";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/packmode <name> Sets the Packmode. Current valid packmodes are " +
                Strings.join(PackModeAPI.getInstance().getPackModes(), " , ");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            String newPackMode = args[0].trim();
            if (PackModeAPI.getInstance().isValidPackMode(newPackMode)) {
                PackModeNetwork.sendTo(new PackModeChangeMessage(PMConfig.getOrdinal(newPackMode)), getCommandSenderAsPlayer(sender));
                sender.sendMessage(new TextComponentString("PackMode is now " + newPackMode + ". Please restart to " +
                        "enjoy the new PackMode."));
                if (server.getPlayerList().getOppedPlayers().getPermissionLevel(getCommandSenderAsPlayer(sender).getGameProfile()) == server.getOpPermissionLevel()) {
                    PackModeAPI.getInstance().setNextRestartPackMode(newPackMode);
                }
            } else {
                throw new CommandException("PackMode " + newPackMode + " is not in the list of valid PackModes.");
            }
        } else if (args.length == 0){
            sender.sendMessage(new TextComponentString("Current PackMode is " + PackModeAPI.getInstance().getCurrentPackMode() +
                ". PackMode next Restart will be " + PackModeAPI.getInstance().getNextRestartPackMode() + ". Valid " +
                " PackModes are " + Strings.join(PackModeAPI.getInstance().getPackModes(), " , ")));
        } else {
            throw new CommandException("Incorrect number of parameters requires 0 or 1, found " + args.length);
        }

    }
}
