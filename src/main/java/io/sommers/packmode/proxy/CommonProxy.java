package io.sommers.packmode.proxy;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class CommonProxy<T extends EntityPlayer> {
    public List<T> getPlayers() {
        return Lists.newArrayList();
    }
}
