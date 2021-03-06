package com.nhwhite3118.structures;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.nhwhite3118.shulkersstructures.ShulkersStructures;
import com.nhwhite3118.shulkersstructures.utils.RegUtil;
import com.nhwhite3118.structures.barn.BarnStructure;
import com.nhwhite3118.structures.simplestructure.SimpleStructure;
import com.nhwhite3118.structures.swamphut.SwampHut;
import com.nhwhite3118.structures.tower.TowerStructure;
import com.nhwhite3118.structures.yakhchal.YakhchalStructure;

import net.minecraft.util.ResourceLocation;
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
    public static Structure<NoFeatureConfig> BARN = new BarnStructure(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> YAKHCHAL = new YakhchalStructure(NoFeatureConfig::deserialize);
    public static IStructurePieceType FOR_REGISTERING_TOWER = com.nhwhite3118.structures.tower.TowerPieces.Piece::new;
    public static IStructurePieceType FOR_REGISTERING_BARN = com.nhwhite3118.structures.barn.BarnPieces.Piece::new;
    public static IStructurePieceType FOR_REGISTERING_YAKHCHAL = com.nhwhite3118.structures.yakhchal.YakhchalPieces.Piece::new;
    public static IStructurePieceType FOR_REGISTERING_SIMPLE_STRUCTURES = com.nhwhite3118.structures.simplestructure.SimpleStructurePieces.Piece::new;
    public static SimpleStructure[] SIMPLE_STRUCTURES = {
            new SimpleStructure(NoFeatureConfig::deserialize, ShulkersStructures.Config.redSandstoneWellSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":red_sandstone_well"), 0, 543842548, "redsandstonewell", new Biome[] {},
                    new Category[] { Category.DESERT }, Decoration.SURFACE_STRUCTURES),

            new SimpleStructure(NoFeatureConfig::deserialize, ShulkersStructures.Config.drinkMeMushroomSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":drink_me_mushroom"), -3, 681652976, "drinkmemushroom",
                    new Biome[] { Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS }, new Category[] { Category.MUSHROOM }, Decoration.SURFACE_STRUCTURES),

            new SimpleStructure(NoFeatureConfig::deserialize, ShulkersStructures.Config.leakingNetherPortalRuinSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":leaking_nether_portal_ruin"), -4, 786268326, "leakingnetherportalruin",
                    new Biome[] { Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS },
                    new Category[] { Category.DESERT, Category.EXTREME_HILLS, Category.FOREST, Category.ICY, Category.JUNGLE, Category.SWAMP, Category.PLAINS,
                            Category.TAIGA, Category.MESA },
                    Decoration.SURFACE_STRUCTURES),

            new SimpleStructure(NoFeatureConfig::deserialize, ShulkersStructures.Config.abandonedEndHouseSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":abandoned_end_house"), -3, 524985498, "abandonedendhouse",
                    new Biome[] { Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS }, new Category[] {}, Decoration.SURFACE_STRUCTURES),

            new SimpleStructure(NoFeatureConfig::deserialize, ShulkersStructures.Config.dangerousDirtHutSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":dangerous_dirt_hut"), -2, 854815645, "dangerousdirthut", new Biome[] {},
                    new Category[] { Category.PLAINS, Category.EXTREME_HILLS, Category.FOREST, Category.MUSHROOM, Category.JUNGLE },
                    Decoration.SURFACE_STRUCTURES) };

    private static final Map<Biome, Boolean> TOWER_BIOMES = ImmutableMap.<Biome, Boolean>builder().put(Biomes.JUNGLE_HILLS, true).put(Biomes.DESERT_HILLS, true)
            .put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, true).put(Biomes.DARK_FOREST_HILLS, true).put(Biomes.DARK_FOREST, true).put(Biomes.MOUNTAINS, true)
            .put(Biomes.SNOWY_TAIGA_MOUNTAINS, true).put(Biomes.TAIGA_MOUNTAINS, true).put(Biomes.WOODED_MOUNTAINS, true).build();

    private static final Map<Category, Boolean> BARN_INVALID_BIOME_CATEGORIES = ImmutableMap.<Category, Boolean>builder().put(Category.THEEND, true)
            .put(Category.NETHER, true).put(Category.OCEAN, true).put(Category.NONE, true).build();

    public static void registerFeatures(Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        RegUtil.register(registry, Structures.SWAMP_HUT, "swamp_hut");
        RegUtil.register(registry, Structures.TOWER, "tower");
        RegUtil.register(registry, Structures.BARN, "barn");
        RegUtil.register(registry, Structures.YAKHCHAL, "yakhchal");

        for (SimpleStructure structure : SIMPLE_STRUCTURES) {
            RegUtil.register(registry, structure, structure.StructureName);
            // register(FOR_REGISTERING_SIMPLE_STRUCTURES, structure.StructureName);
        }
        register(FOR_REGISTERING_SIMPLE_STRUCTURES, "simplestructure");
        Structures.registerStructures();
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to you in the Console.
     */
    private static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }

    public static void registerStructures() {
        register(FOR_REGISTERING_TOWER, "SSTWR");
        register(FOR_REGISTERING_BARN, "SSBRN");
        register(FOR_REGISTERING_YAKHCHAL, "SSYKCHL");
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

    public static void generateBarn(Biome biome, String biomeNamespace, String biomePath) {
        biome.addFeature(Decoration.SURFACE_STRUCTURES,
                BARN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

        if (!BARN_INVALID_BIOME_CATEGORIES.containsKey(biome.getCategory())) {
            biome.addStructure(BARN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }

    public static void generateYakhchal(Biome biome, String biomeNamespace, String biomePath) {
        biome.addFeature(Decoration.SURFACE_STRUCTURES,
                YAKHCHAL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

        if (biome.getCategory() == Category.DESERT) {
            biome.addStructure(YAKHCHAL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }

    public static void generateSimpleStructures(Biome biome, String biomeNamespace, String biomePath) {
        for (SimpleStructure structure : SIMPLE_STRUCTURES) {
            biome.addFeature(structure.DECORATOR, structure.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            if (Arrays.asList(structure.VALID_BIOMES).contains(biome) || Arrays.asList(structure.VALID_BIOME_CATEGORIES).contains(biome.getCategory())) {
                biome.addStructure(structure.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
            }
        }

    }
}
