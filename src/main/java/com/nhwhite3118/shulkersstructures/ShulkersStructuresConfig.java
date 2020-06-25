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
        public ConfigValueListener<Integer> drinkMeMushroomSpawnrate;
        public ConfigValueListener<Integer> redSandstoneWellSpawnrate;
        public ConfigValueListener<Integer> leakingNetherPortalRuinSpawnrate;
        public ConfigValueListener<Integer> abandonedEndHouseSpawnrate;
        public ConfigValueListener<Integer> dangerousDirtHutSpawnrate;

        ShulkersStructuresConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
            builder.push("Feature Options");

            builder.push("Structures");

            towerCanSpawn = subscriber.subscribe(builder.comment("\r\n Whether or not to spawn towers in hills and mountains" + "\r\n Default value is true")
                    .translation("shulkersstructures.config.feature.structures.towerCanSpawn").define("towerCanSpawn", true));

            towerSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often towers will attempt to spawn per chunk in valid biomes."
                            + "\r\n The config value is the max distance in chunks between spawn attempts, but not all spawn attempts will succeed."
                            + "\r\n 10 to practically always have one in render distance, 10000 for extremely rare towers")
                    .translation("nhwhite3118.config.structure.endStructures.towerSpawnrate").defineInRange("towerSpawnrate", 20, 10, 10000));

            barnCanSpawn = subscriber.subscribe(builder.comment("\r\n Whether or not to spawn barns near villages" + "\r\n Default value is true")
                    .translation("shulkersstructures.config.feature.structures.barnCanSpawn").define("barnCanSpawn", true));

            barnSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often barns will attempt to spawn per chunk in valid biomes."
                            + "\r\n The config value is the max distance in chunks between spawn attempts, but not all spawn attempts will succeed."
                            + "\r\n The barn is around a chunk in length, so lowering this too much can make barns more likely to collide."
                            + "\r\n 5 to practically always have several near each village, 10000 for extremely rare barns")
                    .translation("nhwhite3118.config.structure.endStructures.barnSpawnrate").defineInRange("barnSpawnrate", 10, 5, 10000));

            yakhchalCanSpawn = subscriber.subscribe(builder
                    .comment("\r\n Whether or not to spawn yakhchals, ancient buildings designed to use wind to keep ice frozen, in desert biomes"
                            + "\r\n Default value is true")
                    .translation("shulkersstructures.config.feature.structures.yakhchalCanSpawn").define("yakhchalCanSpawn", true));

            yakhchalSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often yakhchals will attempt to spawn per chunk in valid biomes."
                            + "\r\n The config value is the max distance in chunks between spawn attempts, but not all spawn attempts will succeed."
                            + "\r\n 10 to practically always have one in render distance, 10000 for extremely rare yakhchals")
                    .translation("nhwhite3118.config.feature.structures.yakhchalSpawnrate").defineInRange("yakhchalSpawnrate", 23, 10, 10000));

            drinkMeMushroomSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often small mushrooms with a vase and a sign saying 'Drink Me' will attempt to spawn per chunk in valid biomes."
                            + "\r\n The config value is the max distance in chunks between spawn attempts, but not all spawn attempts will succeed."
                            + "\r\n 10 to practically always have one in render distance, 10000 for extremely rare drink me mushroom")
                    .translation("nhwhite3118.config.feature.structures.drinkMeMushroomSpawnrate").defineInRange("drinkMeMushroomSpawnrate", 20, 10, 10000));

            redSandstoneWellSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often red sandstone wells will attempt to spawn per chunk in valid biomes."
                            + "\r\n The config value is the max distance in chunks between spawn attempts, but not all spawn attempts will succeed."
                            + "\r\n 10 to practically always have one in render distance, 10000 for extremely rare red sandstone wells")
                    .translation("nhwhite3118.config.feature.structures.redSandstoneWellSpawnrate").defineInRange("redSandstoneWellSpawnrate", 20, 10, 10000));

            leakingNetherPortalRuinSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often ruined nether portals surrounded by nether blocks will attempt to spawn per chunk in valid biomes."
                            + "\r\n The config value is the max distance in chunks between spawn attempts, but not all spawn attempts will succeed."
                            + "\r\n 10 to practically always have one in render distance, 10000 for extremely rare red sandstone wells")
                    .translation("nhwhite3118.config.feature.structures.leakingNetherPortalRuinSpawnrate")
                    .defineInRange("leakingNetherPortalRuinSpawnrate", 40, 10, 10000));

            abandonedEndHouseSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often small ruined buildings made of end stone will generate in the end."
                            + "\r\n The config value is the max distance in chunks between spawn attempts, but not all spawn attempts will succeed."
                            + "\r\n 10 to practically always have one in render distance, 10000 for extremely rare abandoned end houses")
                    .translation("nhwhite3118.config.feature.structures.abandonedEndHouseSpawnrate")
                    .defineInRange("abandonedEndHouseSpawnrate", 50, 10, 10000));

            dangerousDirtHutSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often coarse dirt huts will spawn in valid biomes."
                            + "\r\n The config value is the max distance in chunks between spawn attempts, but not all spawn attempts will succeed."
                            + "\r\n 10 to practically always have one in render distance, 10000 for extremely rare abandoned end houses")
                    .translation("nhwhite3118.config.feature.structures.dangerousDirtHutSpawnrate").defineInRange("dangerousDirtHutSpawnrate", 40, 10, 10000));
            builder.pop();

            builder.pop();
        }
    }
}