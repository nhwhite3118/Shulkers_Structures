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

        ShulkersStructuresConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
            builder.push("Feature Options");

            builder.push("Structures");

            towerCanSpawn = subscriber.subscribe(builder.comment("\r\n Whether or not to spawn towers in extreme hills" + "\r\n Default value is true")
                    .translation("shulkersstructures.config.feature.structures.towerCanSpawn").define("towerCanSpawn", true));

            towerSpawnrate = subscriber.subscribe(builder
                    .comment("\r\n How often towers will attempt to spawn per chunk in valid biomes."
                            + "\r\n The chance of a towers generating at a chunk is 1/spawnrate."
                            + "\r\n 10 to practically always have one in render distance, 1000 for extremely rare towers")
                    .translation("nhwhite3118.config.structure.endStructures.towerSpawnrate").defineInRange("towerSpawnrate", 20, 10, 1000));
            builder.pop();

            builder.pop();
        }
    }
}