package com.nhwhite3118.structures;

import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.nhwhite3118.shulkersstructures.utils.RegUtil;
import com.nhwhite3118.structures.swamphut.SwampHut;
import com.nhwhite3118.structures.tower.TowerStructure;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

public class Structures {
    public static Feature<NoFeatureConfig> SWAMP_HUT = new SwampHut(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> TOWER = new TowerStructure(NoFeatureConfig::deserialize);
    public static IStructurePieceType FOR_REGISTERING_TOWER = com.nhwhite3118.structures.tower.TowerPieces.Piece::new;
    public static Biome[] tower_biomes = { Biomes.JUNGLE_HILLS, Biomes.DESERT_HILLS, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.DARK_FOREST_HILLS,
            Biomes.DARK_FOREST, Biomes.MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.TAIGA_MOUNTAINS, Biomes.WOODED_MOUNTAINS };
    private static final Map<Biome, Boolean> TOWER_BIOMES = ImmutableMap.<Biome, Boolean>builder().put(Biomes.JUNGLE_HILLS, true).put(Biomes.DESERT_HILLS, true)
            .put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, true).put(Biomes.DARK_FOREST_HILLS, true).put(Biomes.DARK_FOREST, true).put(Biomes.MOUNTAINS, true)
            .put(Biomes.SNOWY_TAIGA_MOUNTAINS, true).put(Biomes.TAIGA_MOUNTAINS, true).put(Biomes.WOODED_MOUNTAINS, true).build();

    public static void registerFeatures(Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        RegUtil.register(registry, Structures.SWAMP_HUT, "swamp_hut");
        RegUtil.register(registry, Structures.TOWER, "tower");
        Structures.registerStructures();
    }

    public static void registerStructures() {
        register(FOR_REGISTERING_TOWER, "SSTWR");
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to you in the Console.
     */
    private static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }

    public static void generateSwampHut(Biome biome, String biomeNamespace, String biomePath) {
        if (biome.getCategory() == Category.SWAMP) {
            biome.addFeature(Decoration.SURFACE_STRUCTURES, SWAMP_HUT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }

    public static void generateTower(Biome biome, String biomeNamespace, String biomePath) {
        biome.addFeature(Decoration.SURFACE_STRUCTURES,
                TOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

        if (biome.getCategory() == Category.EXTREME_HILLS || TOWER_BIOMES.containsKey(biome)) {
            biome.addStructure(TOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }
}
