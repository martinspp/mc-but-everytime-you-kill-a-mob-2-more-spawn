package killmob2xspawn.mixin;

import killmob2xspawn.killmob2xspawn;
import killmob2xspawn.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.util.Random;

import static net.minecraft.world.Heightmap.Type.MOTION_BLOCKING;

@Mixin(LivingEntity.class)
public abstract class LivingEntityInjector extends Entity {
    @Shadow public abstract void kill();
    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    int range = config.spawnRange;

    protected LivingEntityInjector(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {
        if(!world.isClient && this.distanceTo(MinecraftClient.getInstance().player) <= config.distanceToActivate){
            if(!config.mustBeKilledByPlayer || source.getAttacker() != null && source.getAttacker().getClass() == ServerPlayerEntity.class) {

                for (int i = 0; i < config.spawnedEntitiesPerKill; i++) {
                    int random_x = RandomUtils.nextInt(0, range * 2);
                    int random_z = RandomUtils.nextInt(0, range * 2);
                    EntityType<?> entityType = killmob2xspawn.entityTypes.get(RandomUtils.nextInt(0, killmob2xspawn.entityTypes.size()));
                    BlockPos blockPos = new BlockPos(this.getPos().x + (random_x - range), world.getTopY(MOTION_BLOCKING, random_z,random_x), this.getZ() + (random_z - range));
                    entityType.spawn((ServerWorld) this.world,
                            null,
                            null,
                            MinecraftClient.getInstance().player,
                            blockPos,
                            SpawnReason.SPAWN_EGG,
                            true,
                            false);
                }
            }
        }
    }
}
