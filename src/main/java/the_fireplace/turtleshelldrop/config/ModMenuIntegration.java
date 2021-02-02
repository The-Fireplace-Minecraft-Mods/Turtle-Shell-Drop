package the_fireplace.turtleshelldrop.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import the_fireplace.turtleshelldrop.TurtleShellDrop;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
	private static final ModConfig DEFAULT_CONFIG = new ModConfig();

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(new TranslatableText("text.config.turtleshelldrop.title"));

			buildConfigCategories(builder);

			builder.setSavingRunnable(() -> TurtleShellDrop.getConfig().save());
			return builder.build();
		};
	}

	private void buildConfigCategories(ConfigBuilder builder) {
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("text.config.turtleshelldrop.general"));
		addGeneralCategoryEntries(entryBuilder, general);
	}

	private void addGeneralCategoryEntries(ConfigEntryBuilder entryBuilder, ConfigCategory global) {
		global.addEntry(entryBuilder.startIntSlider(new TranslatableText("text.config.turtleshelldrop.option.shellDropChance"), (int)(TurtleShellDrop.getConfig().shellDropChance*1000), 0, 1000)
			.setDefaultValue((int)(DEFAULT_CONFIG.shellDropChance*1000))
			.setTextGetter(val -> Text.of(String.format("%.1f", val/10d)+"%"))
			.setTooltip(new TranslatableText("text.config.turtleshelldrop.option.shellDropChance.desc"))
			.setSaveConsumer(newValue -> TurtleShellDrop.getConfig().shellDropChance = ((double)newValue)/1000d)
			.build());
		global.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("text.config.turtleshelldrop.option.dropWhenKilledByPlayer"), TurtleShellDrop.getConfig().dropWhenKilledByPlayer)
			.setDefaultValue(DEFAULT_CONFIG.dropWhenKilledByPlayer)
			.setTooltip(new TranslatableText("text.config.turtleshelldrop.option.dropWhenKilledByPlayer.desc"))
			.setSaveConsumer(newValue -> TurtleShellDrop.getConfig().dropWhenKilledByPlayer = newValue)
			.build());
		global.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("text.config.turtleshelldrop.option.dropScutes"), TurtleShellDrop.getConfig().dropScutes)
			.setDefaultValue(DEFAULT_CONFIG.dropScutes)
			.setTooltip(new TranslatableText("text.config.turtleshelldrop.option.dropScutes.desc"))
			.setSaveConsumer(newValue -> TurtleShellDrop.getConfig().dropScutes = newValue)
			.build());
		global.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("text.config.turtleshelldrop.option.babyTurtleDrops"), TurtleShellDrop.getConfig().babyTurtleDrops)
			.setDefaultValue(DEFAULT_CONFIG.babyTurtleDrops)
			.setTooltip(new TranslatableText("text.config.turtleshelldrop.option.babyTurtleDrops.desc"))
			.setSaveConsumer(newValue -> TurtleShellDrop.getConfig().babyTurtleDrops = newValue)
			.build());
	}
}
