package the_fireplace.grandexchange;

import net.minecraft.entity.passive.EntityTurtle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;

@Mod(TurtleShellDrop.MODID)
public final class TurtleShellDrop {
    public static final String MODID = "turtleshelldrop";
    public static final Random rand = new Random();

    public TurtleShellDrop() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, cfg.SERVER_SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverConfig);
    }

    @SubscribeEvent
    public void libingDeath(LivingDeathEvent event) {
        if(!event.getEntityLiving().getEntityWorld().isRemote())
            if(event.getEntityLiving() instanceof EntityTurtle)
                if(cfg.dropWhenKilledByPlayer || !(event.getSource().getTrueSource() instanceof EntityPlayer))
                    if(cfg.babyTurtleDrops || !event.getEntityLiving().isChild())
                        if(rand.nextDouble() <= cfg.shellDropChance)
                            event.getEntity().entityDropItem(new ItemStack(cfg.dropScute ? Items.SCUTE : Items.TURTLE_HELMET));
    }

    public void serverConfig(ModConfig.ModConfigEvent event) {
        if (event.getConfig().getType() == ModConfig.Type.SERVER)
            cfg.load();
    }

    public static class cfg {
        public static final ServerConfig SERVER;
        public static final ForgeConfigSpec SERVER_SPEC;
        static {
            final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
            SERVER_SPEC = specPair.getRight();
            SERVER = specPair.getLeft();
        }
        public static double shellDropChance;
        public static boolean dropWhenKilledByPlayer;
        public static boolean dropScute;
        public static boolean babyTurtleDrops;

        public static void load() {
            shellDropChance = SERVER.shellDropChance.get();
            dropWhenKilledByPlayer = SERVER.dropWhenKilledByPlayer.get();
            dropScute = SERVER.dropScute.get();
            babyTurtleDrops = SERVER.babyTurtleDrops.get();
        }

        public static class ServerConfig {
            public ForgeConfigSpec.DoubleValue shellDropChance;
            public ForgeConfigSpec.BooleanValue dropWhenKilledByPlayer;
            public ForgeConfigSpec.BooleanValue dropScute;
            public ForgeConfigSpec.BooleanValue babyTurtleDrops;

            ServerConfig(ForgeConfigSpec.Builder builder) {
                builder.push("general");
                shellDropChance = builder
                        .comment("Chance that a turtle shell will drop when a turtle is killed.")
                        .translation("Shell Drop Chance")
                        .defineInRange("shellDropChance", 0.5D, 0, 1);
                dropWhenKilledByPlayer = builder
                        .comment("Whether or not the shell drops when the turtle is killed directly by the player.")
                        .translation("Shell Drop When Killed")
                        .define("dropWhenKilledByPlayer", false);
                dropScute = builder
                        .comment("Drop Scutes instead of Shells.")
                        .translation("Drop Scutes")
                        .define("dropScute", true);
                babyTurtleDrops = builder
                        .comment("Allow drops from dead baby turtles.")
                        .translation("Baby Turtle Drops")
                        .define("babyTurtleDrops", false);
                builder.pop();
            }
        }
    }

}
