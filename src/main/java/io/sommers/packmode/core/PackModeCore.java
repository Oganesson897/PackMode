package io.sommers.packmode.core;

import io.sommers.packmode.PMConfig;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class PackModeCore implements IFMLLoadingPlugin {

    public static File cfg_override;

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return "io.sommers.packmode.core.PreLauncher";
    }

    @Override
    public void injectData(Map<String, Object> data) {
        File mcLocation = (File) data.get("mcLocation");
        cfg_override = new File(mcLocation, "config/packmode");
        PMConfig.init(new File(mcLocation, "config/packmode/packmode.cfg"));
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
