package the_fireplace.turtleshelldrop.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
	private static final ModConfig DEFAULT_CONFIG = new ModConfig();

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {

		};
	}
}
