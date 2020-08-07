package com.nhwhite3118.structures.barn;

import org.apache.logging.log4j.Level;

import com.mojang.serialization.Codec;
import com.nhwhite3118.shulkersstructures.ShulkersStructures;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class BarnStructure extends Structure<NoFeatureConfig> {

    public BarnStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    /*
     * The structure name to show in the /locate command.
     * 
     * Make sure this matches what the resourcelocation of your structure will be because if you don't add the MODID: part, Minecraft will put minecraft: in
     * front of the name instead and we don't want that. We want our structure to have our mod's ID rather than Minecraft so people don't get confused.
     */
    @Override
    public String getStructureName() {
        return ShulkersStructures.MODID + ":barn";
    }

    /*
     * This is how the worldgen code will start the generation of our structure when it passes the checks.
     */
    @Override
    public Structure.IStartFactory getStartFactory() {
        return BarnStructure.Start::new;
    }

    /*
     * This is used so that if two structure's has the same spawn location algorithm, they will not end up in perfect sync as long as they have different seed
     * modifier.
     * 
     * So make this a big random number that is unique only to this structure.
     */
    protected int getSeedModifier() {
        return 231598735;
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
    public boolean func_230363_a_(ChunkGenerator chunkGen, BiomeProvider biomeManager, long p_230363_3_, SharedSeedRandom rand, int chunkPosX, int chunkPosZ,
            Biome biome, ChunkPos p_230363_9_, NoFeatureConfig p_230363_10_) {
        // Almost the inverse of the pillager outpost code; there has to be a village within 10 chunks, but not within 5 chunks
        int i = chunkPosX >> 4;
        int j = chunkPosZ >> 4;
        rand.setSeed((long) (i ^ j << 4) ^ p_230363_3_);
        rand.nextInt();
        for (int k = chunkPosX - 10; k <= chunkPosX + 10; ++k) {
            for (int l = chunkPosZ - 10; l <= chunkPosZ + 10; ++l) {
                // skip the center; we want to be on the outskirts
                if ((k > chunkPosX - 5 && k < chunkPosX + 5) && (l > chunkPosZ - 5 && l < chunkPosZ + 5)) {
                    continue;
                }
                ChunkPos chunkpos = Structure.field_236381_q_.func_236392_a_(chunkGen.func_235957_b_().func_236197_a_(Structure.field_236381_q_), p_230363_3_,
                        rand, k, l);
                if (k == chunkpos.x && l == chunkpos.z) {
                    return true;
                }
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
        public void func_230364_a_(ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, IFeatureConfig config) {
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
            BarnPieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand);

            // Sets the bounds of the structure.
            this.recalculateStructureSize();

            // I use to debug and quickly find out if the structure is spawning or not and
            // where it is.
            ShulkersStructures.LOGGER.log(Level.DEBUG, "Barn at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
        }

    }

}
