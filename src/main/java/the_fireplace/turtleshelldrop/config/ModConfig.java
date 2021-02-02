package the_fireplace.turtleshelldrop.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import the_fireplace.turtleshelldrop.TurtleShellDrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
	private static final File configDir = new File("config");
	private static final File baseConfigFile = new File(configDir, TurtleShellDrop.MODID+".json5");

	public double shellDropChance = 0.5;
	public boolean dropWhenKilledByPlayer = false;
	public boolean dropScutes = true;
	public boolean babyTurtleDrops = false;

	public void save() {
		try {
			FileWriter fw = new FileWriter(baseConfigFile);
			fw.write(Jankson.builder().build().toJson(this).toJson(JsonGrammar.JSON5));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("ConstantConditions")
	public static ModConfig load() {
		JsonObject obj;
		ModConfig conf = new ModConfig();
		try {
			obj = Jankson.builder().build().load(baseConfigFile);
		} catch(FileNotFoundException e) {
			return conf;
		} catch (IOException | SyntaxError | NullPointerException e) {
			e.printStackTrace();
			return conf;
		}
		if(obj.containsKey("shellDropChance"))
			conf.shellDropChance = bound(obj.get(Double.class, "shellDropChance"), 0, 1);
		if(obj.containsKey("dropWhenKilledByPlayer"))
			conf.dropWhenKilledByPlayer = obj.get(Boolean.class, "dropWhenKilledByPlayer");
		if(obj.containsKey("dropScutes"))
			conf.dropScutes = obj.get(Boolean.class, "dropScutes");
		if(obj.containsKey("babyTurtleDrops"))
			conf.babyTurtleDrops = obj.get(Boolean.class, "babyTurtleDrops");

		return conf;
	}

	static double bound(double d, double min, double max) {
		return Math.min(Math.max(d, min), max);
	}
}
