package com.nhwhite3118.structures;

import java.util.Arrays;
import java.util.HashMap;
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
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

public class Structures {
    public static Feature<NoFeatureConfig> SWAMP_HUT = new SwampHut(NoFeatureConfig.field_236558_a_);
    public static Structure<NoFeatureConfig> TOWER = new TowerStructure(NoFeatureConfig.field_236558_a_);
    public static Structure<NoFeatureConfig> BARN = new BarnStructure(NoFeatureConfig.field_236558_a_);
    public static Structure<NoFeatureConfig> YAKHCHAL = new YakhchalStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType FOR_REGISTERING_TOWER = com.nhwhite3118.structures.tower.TowerPieces.Piece::new;
    public static IStructurePieceType FOR_REGISTERING_BARN = com.nhwhite3118.structures.barn.BarnPieces.Piece::new;
    public static IStructurePieceType FOR_REGISTERING_YAKHCHAL = com.nhwhite3118.structures.yakhchal.YakhchalPieces.Piece::new;
    public static IStructurePieceType FOR_REGISTERING_SIMPLE_STRUCTURES = com.nhwhite3118.structures.simplestructure.SimpleStructurePieces.Piece::new;
    public static SimpleStructure[] SIMPLE_STRUCTURES = {
            new SimpleStructure(NoFeatureConfig.field_236558_a_, ShulkersStructures.Config.redSandstoneWellSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":red_sandstone_well"), 0, 543842548, "redsandstonewell", new Biome[] {},
                    new Category[] { Category.DESERT }, Decoration.SURFACE_STRUCTURES),

            new SimpleStructure(NoFeatureConfig.field_236558_a_, ShulkersStructures.Config.drinkMeMushroomSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":drink_me_mushroom"), -3, 681652976, "drinkmemushroom",
                    new Biome[] { Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS }, new Category[] { Category.MUSHROOM }, Decoration.SURFACE_STRUCTURES),

            new SimpleStructure(NoFeatureConfig.field_236558_a_, ShulkersStructures.Config.leakingNetherPortalRuinSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":leaking_nether_portal_ruin"), -4, 786268326, "leakingnetherportalruin",
                    new Biome[] { Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS },
                    new Category[] { Category.DESERT, Category.EXTREME_HILLS, Category.FOREST, Category.ICY, Category.JUNGLE, Category.SWAMP, Category.PLAINS,
                            Category.TAIGA, Category.MESA },
                    Decoration.SURFACE_STRUCTURES),

            new SimpleStructure(NoFeatureConfig.field_236558_a_, ShulkersStructures.Config.abandonedEndHouseSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":abandoned_end_house"), -3, 524985498, "abandonedendhouse",
                    new Biome[] { Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS }, new Category[] {}, Decoration.SURFACE_STRUCTURES),

            new SimpleStructure(NoFeatureConfig.field_236558_a_, ShulkersStructures.Config.dangerousDirtHutSpawnrate.get(),
                    new ResourceLocation(ShulkersStructures.MODID + ":dangerous_dirt_hut"), -2, 854815645, "dangerousdirthut", new Biome[] {},
                    new Category[] { Category.PLAINS, Category.EXTREME_HILLS, Category.FOREST, Category.MUSHROOM, Category.JUNGLE },
                    Decoration.SURFACE_STRUCTURES) };

    private static final Map<Biome, Boolean> TOWER_BIOMES = ImmutableMap.<Biome, Boolean>builder().put(Biomes.JUNGLE_HILLS, true).put(Biomes.DESERT_HILLS, true)
            .put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, true).put(Biomes.DARK_FOREST_HILLS, true).put(Biomes.DARK_FOREST, true).put(Biomes.MOUNTAINS, true)
            .put(Biomes.SNOWY_TAIGA_MOUNTAINS, true).put(Biomes.TAIGA_MOUNTAINS, true).put(Biomes.WOODED_MOUNTAINS, true).build();

    private static final Map<Category, Boolean> BARN_INVALID_BIOME_CATEGORIES = ImmutableMap.<Category, Boolean>builder().put(Category.THEEND, true)
            .put(Category.NETHER, true).put(Category.OCEAN, true).put(Category.NONE, true).build();

    /*
     * This is called when the forge event on the mod event bus for registering features is called. Adds structures to all the registries and maps which they
     * need to be in for them to work properly.
     */
    public static void registerStructures(Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        int barnSpawnRate = ShulkersStructures.Config.barnSpawnrate.get();
        int yakhchalSpawnRate = ShulkersStructures.Config.yakhchalSpawnrate.get();
        int towerSpawnrate = ShulkersStructures.Config.towerSpawnrate.get();

        RegUtil.register(registry, Structures.SWAMP_HUT, "swamp_hut");

        registerStructure(new ResourceLocation(ShulkersStructures.MODID, "barn"), BARN, GenerationStage.Decoration.SURFACE_STRUCTURES,
                new StructureSeparationSettings(barnSpawnRate, (int) (barnSpawnRate * 0.75f), 231598735));

        registerStructure(new ResourceLocation(ShulkersStructures.MODID, "tower"), TOWER, GenerationStage.Decoration.SURFACE_STRUCTURES,
                new StructureSeparationSettings(towerSpawnrate, (int) (towerSpawnrate * 0.75f), 345346547));

        registerStructure(new ResourceLocation(ShulkersStructures.MODID, "yakhchal"), YAKHCHAL, GenerationStage.Decoration.SURFACE_STRUCTURES,
                new StructureSeparationSettings(yakhchalSpawnRate, (int) (yakhchalSpawnRate * 0.75f), 735465519));

        for (SimpleStructure structure : SIMPLE_STRUCTURES) {
            registerStructure(new ResourceLocation(ShulkersStructures.MODID, structure.StructureName), structure, GenerationStage.Decoration.SURFACE_STRUCTURES,
                    new StructureSeparationSettings(structure.getSpawnRate(), (int) (structure.getSpawnRate() * 0.75), structure.getSeed()));
        }

        Structures.registerPieces();
    }

    /*
     * Adds the provided structure to the registry, and adds the separation settings. This is how the rarity of the structure is set.
     */
    public static <F extends Structure<NoFeatureConfig>> void registerStructure(ResourceLocation resourceLocation, F structure,
            GenerationStage.Decoration stage, StructureSeparationSettings StructureSeparationSettings) {
        structure.setRegistryName(resourceLocation);
        addToStructureInfoMaps(resourceLocation.toString(), structure, stage);
        FlatGenerationSettings.STRUCTURES.put(structure, structure.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG));

        Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(DimensionStructuresSettings.field_236191_b_);
        tempMap.put(structure, StructureSeparationSettings);
        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.copyOf(tempMap);

        DimensionSettings.Preset.field_236122_b_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236123_c_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236124_d_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236125_e_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236126_f_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236127_g_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
    }

    /*
     * The structture class keeps maps of all the structures and their generation stages. We need to add our structures into the maps along with the vanilla
     * structures or else it will cause errors
     */
    private static <F extends Structure<?>> F addToStructureInfoMaps(String name, F structure, GenerationStage.Decoration generationStage) {
        Structure.field_236365_a_.put(name.toLowerCase(Locale.ROOT), structure);
        Structure.field_236385_u_.put(structure, generationStage);
        return Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), structure);
    }

    public static void registerPieces() {
        register(FOR_REGISTERING_TOWER, "SSTWR");
        register(FOR_REGISTERING_BARN, "SSBRN");
        register(FOR_REGISTERING_YAKHCHAL, "SSYKCHL");
        register(FOR_REGISTERING_SIMPLE_STRUCTURES, "simplestructure");
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
        if (biome.getCategory() == Category.EXTREME_HILLS || TOWER_BIOMES.containsKey(biome)) {
            biome.func_235063_a_(TOWER.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }

    public static void generateBarn(Biome biome, String biomeNamespace, String biomePath) {
        if (!BARN_INVALID_BIOME_CATEGORIES.containsKey(biome.getCategory())) {
            biome.func_235063_a_(BARN.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }

    public static void generateYakhchal(Biome biome, String biomeNamespace, String biomePath) {
        if (biome.getCategory() == Category.DESERT) {
            biome.func_235063_a_(YAKHCHAL.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }

    public static void generateSimpleStructures(Biome biome, String biomeNamespace, String biomePath) {
        for (SimpleStructure structure : SIMPLE_STRUCTURES) {
            if (Arrays.asList(structure.VALID_BIOMES).contains(biome) || Arrays.asList(structure.VALID_BIOME_CATEGORIES).contains(biome.getCategory())) {
                biome.func_235063_a_(structure.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG));
            }
        }

    }
}
