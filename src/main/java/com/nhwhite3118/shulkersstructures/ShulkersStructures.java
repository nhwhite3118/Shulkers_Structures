package com.nhwhite3118.shulkersstructures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nhwhite3118.shulkersstructures.ShulkersStructuresConfig.ShulkersStructuresConfigValues;
import com.nhwhite3118.shulkersstructures.utils.ConfigHelper;
import com.nhwhite3118.structures.Structures;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("shulkersstructures")
public class ShulkersStructures {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "shulkersstructures";
    public static ShulkersStructuresConfigValues Config = null;
    public static final ENVIRONMENTS ENVIRONMENT = ENVIRONMENTS.PRODUCTION;

    public enum ENVIRONMENTS {
        DEBUG, PRODUCTION
    };

    public ShulkersStructures() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        Config = ConfigHelper.register(ModConfig.Type.COMMON, ShulkersStructuresConfig.ShulkersStructuresConfigValues::new);
    }

    public void setup(final FMLCommonSetupEvent event) {
        ShulkersStructures.addFeaturesAndStructuresToBiomes();
    }

    private static void addFeaturesAndStructuresToBiomes() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            String biomeNamespace = biome.getRegistryName().getNamespace();
            String biomePath = biome.getRegistryName().getPath();

            Structures.generateSwampHut(biome, biomeNamespace, biomePath);
            Structures.generateTower(biome, biomeNamespace, biomePath);
            Structures.generateBarn(biome, biomeNamespace, biomePath);
            Structures.generateYakhchal(biome, biomeNamespace, biomePath);
        }
    }
}
