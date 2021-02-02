package the_fireplace.turtleshelldrop;

import net.fabricmc.api.ModInitializer;
import the_fireplace.turtleshelldrop.config.ModConfig;

@SuppressWarnings("WeakerAccess")
public final class TurtleShellDrop implements ModInitializer {
    public static final String MODID = "turtleshelldrop";

    private static ModConfig config;
    public static ModConfig getConfig() {
        return config;
    }

    @Override
    public void onInitialize() {
        config = ModConfig.load();
        config.save();
    }
}
