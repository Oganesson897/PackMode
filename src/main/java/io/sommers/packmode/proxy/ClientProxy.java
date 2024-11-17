package io.sommers.packmode.proxy;

import com.google.common.collect.Lists;
import io.sommers.packmode.PMConfig;
import io.sommers.packmode.client.ConfigScreen;
import io.sommers.packmode.client.TipsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy<EntityPlayerSP> {
    public List<EntityPlayerSP> getPlayers() {
        return Lists.newArrayList(Minecraft.getMinecraft().player);
    }

    private static int optionsButtonId = 0;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu && !TipsScreen.wasOpened) {
            TipsScreen.wasOpened = true;
            List<String> warnings = new ArrayList<>();
            if (Objects.equals(PMConfig.getPackMode(), PMConfig.getAcceptedModes()[0]) && !PMConfig.disabledTipScreen()) {
                warnings.add("The current packmode is default option.");
                warnings.add("(The first packmode type in acceptedModes array)");
            }
            if (!warnings.isEmpty()) {
                event.setGui(new TipsScreen(warnings));
            }
        }
    }

    @SubscribeEvent
    public static void onInitGuiEvent(final GuiScreenEvent.InitGuiEvent event) {
        final GuiScreen gui = event.getGui();
        if (gui instanceof GuiOptions) {
            List<GuiButton> buttonList = event.getButtonList();
            boolean ok = true;
            do {
                optionsButtonId = new Random().nextInt(Integer.MAX_VALUE);
                for (final GuiButton button : buttonList) {
                    if (button.id == optionsButtonId) {
                        ok = false;
                        break;
                    }
                }
            } while (!ok);

            buttonList.add(new GuiButton(optionsButtonId, gui.width / 2 + 5, gui.height / 6  + 24 - 9, 150, 20, I18n.format("packmodemenu.options.pack_mode")));
            event.setButtonList(buttonList);
        }
    }

    @SubscribeEvent
    public static void onActionGuiEvent(final GuiScreenEvent.ActionPerformedEvent event) {
        if (event.getGui() instanceof GuiOptions) {
            if (event.getButton().id == optionsButtonId) {
                Minecraft.getMinecraft().displayGuiScreen(new ConfigScreen(Minecraft.getMinecraft().currentScreen));
            }
        }
    }
}
