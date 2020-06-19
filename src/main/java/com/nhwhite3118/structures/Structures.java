package com.nhwhite3118.structures;

import java.util.Locale;

import com.nhwhite3118.shulkersstructures.utils.RegUtil;
import com.nhwhite3118.structures.swamphut.SwampHut;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

public class Structures {
    public static Feature<NoFeatureConfig> SWAMP_HUT = new SwampHut(NoFeatureConfig::deserialize);
    // public static IStructurePieceType FOR_REGISTERING_SWAMP_HUT = com.nhwhite3118.cobbler.structures.TestStructurePieces.Piece::new;

    public static void registerFeatures(Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        RegUtil.register(registry, Structures.SWAMP_HUT, "swamp_hut");
        Structures.registerStructures();
    }

    public static void registerStructures() {
        // register(FOR_REGISTERING_SWAMP_HUT, "SWMPHT");
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to you in the Console.
     */
    private static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }

    public static void generateSwampHut(Biome biome, String biomeNamespace, String biomePath) {
        if (biome.getCategory() == Category.SWAMP) {
            biome.addFeature(Decoration.SURFACE_STRUCTURES, SWAMP_HUT.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG)
                    .func_227228_a_(Placement.END_ISLAND.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }
}
