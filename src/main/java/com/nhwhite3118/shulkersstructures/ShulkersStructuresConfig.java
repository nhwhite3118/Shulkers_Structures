package com.nhwhite3118.shulkersstructures;

import com.nhwhite3118.shulkersstructures.utils.ConfigHelper;
import com.nhwhite3118.shulkersstructures.utils.ConfigHelper.ConfigValueListener;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ShulkersStructuresConfig {
    public static class ShulkersStructuresConfigValues {
        public ConfigValueListener<Boolean> towerCanSpawn;
        public ConfigValueListener<Integer> towerSpawnrate;
        public ConfigValueListener<Boolean> barnCanSpawn;
        public ConfigValueListener<Integer> barnSpawnrate;
        public ConfigValueListener<Boolean> yakhchalCanSpawn;
        public ConfigValueListener<Integer> yakhchalSpawnrate;

        ShulkersStructuresConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
            builder.push("Feature Options");

            builder.push("Structures");

            towerCanSpawn = subscriber.subscribe(builder.comment("\r\n Whether or not to spawn towers in hills and mountains" + "\r\n Default value is true")
                    .translation("shulkersstructures.config.feature.structures.towerCanSpawn").define("towerCanSpawn", true));

            towerSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often towers will attempt to spawn per chunk in valid biomes."
                            + "\r\n The chance of a tower generating at a chunk is 1/spawnrate."
                            + "\r\n 10 to practically always have one in render distance, 1000 for extremely rare towers")
                    .translation("nhwhite3118.config.structure.endStructures.towerSpawnrate").defineInRange("towerSpawnrate", 20, 10, 1000));

            barnCanSpawn = subscriber.subscribe(builder.comment("\r\n Whether or not to spawn barns near villages" + "\r\n Default value is true")
                    .translation("shulkersstructures.config.feature.structures.barnCanSpawn").define("barnCanSpawn", true));

            barnSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often barns will attempt to spawn per chunk in valid biomes."
                            + "\r\n The chance of a barn generating at a chunk is 1/spawnrate."
                            + "\r\n The barn is around a chunk in length, so lowering this too much can make barns more likely to collide."
                            + "\r\n 5 to practically always have several near each village, 1000 for extremely rare barns")
                    .translation("nhwhite3118.config.structure.endStructures.barnSpawnrate").defineInRange("barnSpawnrate", 10, 5, 1000));

            yakhchalCanSpawn = subscriber.subscribe(builder
                    .comment("\r\n Whether or not to spawn yakhchals, ancient buildings designed to use wind to keep ice frozen, in desert biomes"
                            + "\r\n Default value is true")
                    .translation("shulkersstructures.config.feature.structures.yakhchalCanSpawn").define("yakhchalCanSpawn", true));

            yakhchalSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often yakhchals will attempt to spawn per chunk in valid biomes."
                            + "\r\n The chance of a yakhchal generating at a chunk is 1/spawnrate."
                            + "\r\n 10 to practically always have one in render distance, 1000 for extremely rare yakhchals")
                    .translation("nhwhite3118.config.feature.structures.yakhchalSpawnrate").defineInRange("yakhchalSpawnrate", 23, 10, 1000));
            builder.pop();

            builder.pop();
        }
    }
}