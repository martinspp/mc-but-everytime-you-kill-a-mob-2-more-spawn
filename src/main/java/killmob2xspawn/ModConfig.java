package killmob2xspawn;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "killmob2xspawn")
public class ModConfig implements ConfigData {
    @Comment("disable at your own risk")
    public boolean mustBeKilledByPlayer = true;
    @Comment("How far to spawn around the mob that died, Y axis gets +2")
    public int spawnRange = 2;

    public int spawnedEntitiesPerKill = 2;

    public int distanceToActivate = 20;
}
