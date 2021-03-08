package killmob2xspawn;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class killmob2xspawn implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "mbeykam2ms";
    public static final String MOD_NAME = "Minecraft But Everytime You Kill A Mob 2 More Spawn";
    public static ArrayList<EntityType> entityTypes;
    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        log(Level.INFO, "Initializing");
        entityTypes = new ArrayList<>();
        Registry.ENTITY_TYPE.forEach(entityType -> {
                if(entityType.isSummonable()){
                    entityTypes.add(entityType);
                    log(Level.INFO, "added: "+entityType.getTranslationKey());
                }
//config.includeAllEntities ||
        });
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}