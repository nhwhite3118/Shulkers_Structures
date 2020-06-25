package com.nhwhite3118.structures.simplestructure;

import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.Level;

import com.mojang.datafixers.Dynamic;
import com.nhwhite3118.shulkersstructures.ShulkersStructures;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SimpleStructure extends Structure<NoFeatureConfig> {

    private int SpawnRate;
    ResourceLocation resources[];
    private int yOffsets[];
    private int Seed;
    public String StructureName;
    public Biome[] VALID_BIOMES;
    public Category[] VALID_BIOME_CATEGORIES;
    public Decoration DECORATOR;

    public SimpleStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> config, int spawnRate, ResourceLocation resource, int yOffset, int seed,
            String structureName, Biome[] validBiomes, Category[] validCategories, Decoration decorator) {
        super(config);
        SpawnRate = spawnRate;
        resources = new ResourceLocation[] { resource };
        yOffsets = new int[] { yOffset };
        Seed = seed;
        StructureName = structureName;
        VALID_BIOMES = validBiomes;
        VALID_BIOME_CATEGORIES = validCategories;
        DECORATOR = decorator;
    }

    /*
     * Determines the valid chunks that this structure can spawn in.
     * 
     * This is using vanilla's default algorithm to determine chunks that this structure can spawn in.
     */
    @Override
    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
        int maxDistance = this.SpawnRate;
        int minDistance = (int) (maxDistance * 0.75f);

        int xTemp = x + maxDistance * spacingOffsetsX;
        int ztemp = z + maxDistance * spacingOffsetsZ;
        int xTemp2 = xTemp < 0 ? xTemp - maxDistance + 1 : xTemp;
        int zTemp2 = ztemp < 0 ? ztemp - maxDistance + 1 : ztemp;
        int validChunkX = xTemp2 / maxDistance;
        int validChunkZ = zTemp2 / maxDistance;

        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), validChunkX, validChunkZ, this.getSeedModifier());
        validChunkX = validChunkX * maxDistance;
        validChunkZ = validChunkZ * maxDistance;
        validChunkX = validChunkX + random.nextInt(maxDistance - minDistance);
        validChunkZ = validChunkZ + random.nextInt(maxDistance - minDistance);

        return new ChunkPos(validChunkX, validChunkZ);
    }

    /*
     * The structure name to show in the /locate command.
     * 
     * Make sure this matches what the resourcelocation of your structure will be because if you don't add the MODID: part, Minecraft will put minecraft: in
     * front of the name instead and we don't want that. We want our structure to have our mod's ID rather than Minecraft so people don't get confused.
     */
    @Override
    public String getStructureName() {
        return ShulkersStructures.MODID + ":" + this.StructureName;
    }

    /*
     * This seems to be unused but cannot be removed. Just return 0 is all you need to do.
     */
    @Override
    public int getSize() {
        return 0;
    }

    /*
     * This is how the worldgen code will start the generation of our structure when it passes the checks.
     */
    @Override
    public Structure.IStartFactory getStartFactory() {
        return SimpleStructure.Start::new;
    }

    /*
     * This is used so that if two structure's has the same spawn location algorithm, they will not end up in perfect sync as long as they have different seed
     * modifier.
     * 
     * So make this a big random number that is unique only to this structure.
     */
    protected int getSeedModifier() {
        return Seed;
    }

    /*
     * This is where all the checks will be done to determine if the structure can spawn here.
     * 
     * Notice how the biome is also passed in. While you could do manual checks on the biome to see if you can spawn here, that is highly discouraged. Instead,
     * you should do the biome check in the FMLCommonSetupEvent event (setup method in StructureTutorialMain) and add your structure to the biome with
     * .addFeature and .addStructure methods.
     * 
     * Instead, this method is best used for determining if the chunk position itself is valid, if certain other structures are too close or not, or some other
     * restrictive condition.
     *
     * For example, Pillager Outposts added a check to make sure it cannot spawn within 10 chunk of a Village. (Bedrock Edition seems to not have the same
     * check)
     */
    @Override
    public boolean func_225558_a_(BiomeManager biomeManager, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome) {
        ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);

        // Checks to see if current chunk is valid to spawn in.
        if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
            // Checks if the biome can spawn this structure.
            if (chunkGen.hasStructure(biome, this)) {
                return true;
            }
        }

        return false;
    }

    /*
     * Handles calling up the structure's pieces class and height that structure will spawn at.
     */
    public static class Start extends StructureStart {
        public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            // Check out vanilla's WoodlandMansionStructure for how they offset the x and z
            // so that they get the y value of the land at the mansion's entrance, no matter
            // which direction the mansion is rotated.
            //
            // However, for most purposes, getting the y value of land with the default x
            // and z is good enough.
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];

            // Turns the chunk coordinates into actual coordinates we can use. (Gets center
            // of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            // Finds the y value of the terrain at location.
            int surfaceY = generator.func_222531_c(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            if (surfaceY < 30) {
                return;
            }
            BlockPos blockpos = new BlockPos(x, surfaceY, z);

            // Now adds the structure pieces to this.components with all details such as
            // where each part goes
            // so that the structure can be added to the world by worldgen.
            SimpleStructure containingStructure = (SimpleStructure) this.getStructure();
            SimpleStructurePieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand, containingStructure.resources,
                    containingStructure.yOffsets);

            // Sets the bounds of the structure.
            this.recalculateStructureSize();

            // I use to debug and quickly find out if the structure is spawning or not and
            // where it is.
            ShulkersStructures.LOGGER.log(Level.DEBUG,
                    containingStructure.StructureName + " at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
        }

    }

}
