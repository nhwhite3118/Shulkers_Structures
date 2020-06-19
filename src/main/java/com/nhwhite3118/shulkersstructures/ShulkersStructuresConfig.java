package com.nhwhite3118.shulkersstructures;

import com.nhwhite3118.shulkersstructures.utils.ConfigHelper;
import com.nhwhite3118.shulkersstructures.utils.ConfigHelper.ConfigValueListener;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ShulkersStructuresConfig {
    public static class ShulkersStructuresConfigValues {
        public ConfigValueListener<Boolean> structureCanSpawn;

        ShulkersStructuresConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
            builder.push("Feature Options");

            builder.push("EndStructures");

//            structureCanSpawn = subscriber.subscribe(builder
//                    .comment("\r\n Whether or not to spawn Shulker Factories - End City inspired structures with a shulker spawner"
//                            + "\r\n Default value is true")
//                    .translation("repurposedstructures.config.feature.endStructures.addshulkerfactories").define("shulkerFactorys", true));
//            builder.pop();

            builder.pop();
        }
    }
}