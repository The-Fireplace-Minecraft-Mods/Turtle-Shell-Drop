package the_fireplace.turtleshelldrop.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import the_fireplace.turtleshelldrop.TurtleShellDrop;
import the_fireplace.turtleshelldrop.config.ModConfig;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	private static final Random RANDOM = new Random();

	@Inject(at = @At("RETURN"), method="dropEquipment")
	public void dropTutleShell(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
		if ((Object)this instanceof TurtleEntity && !this.world.isClient()) {
			ModConfig cfg = TurtleShellDrop.getConfig();
			if (cfg.dropWhenKilledByPlayer || !(source.getAttacker() instanceof PlayerEntity)) {
				if (cfg.babyTurtleDrops || !this.isBaby()) {
					if (RANDOM.nextDouble() <= cfg.shellDropChance) {
						this.dropStack(new ItemStack(cfg.dropScutes ? Items.SCUTE : Items.TURTLE_HELMET));
					}
				}
			}
		}
	}
}
