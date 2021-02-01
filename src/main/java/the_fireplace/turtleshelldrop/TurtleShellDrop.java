package the_fireplace.turtleshelldrop;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Random;

@SuppressWarnings("WeakerAccess")
public final class TurtleShellDrop implements ModInitializer {
    public static final String MODID = "turtleshelldrop";
    public static final Random rand = new Random();

    @Override
    public void onInitialize() {

    }

    public void livingDeath(LivingDeathEvent event) {
        if(!event.getEntityLiving().getEntityWorld().isRemote())
            if(event.getEntityLiving() instanceof TurtleEntity)
                if(cfg.dropWhenKilledByPlayer || !(event.getSource().getTrueSource() instanceof PlayerEntity))
                    if(cfg.babyTurtleDrops || !event.getEntityLiving().isChild())
                        if(rand.nextDouble() <= cfg.shellDropChance)
                            event.getEntity().entityDropItem(new ItemStack(cfg.dropScute ? Items.SCUTE : Items.TURTLE_HELMET));
    }
}
