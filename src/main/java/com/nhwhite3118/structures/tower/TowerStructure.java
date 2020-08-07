package com.nhwhite3118.structures.tower;

import org.apache.logging.log4j.Level;

import com.mojang.serialization.Codec;
import com.nhwhite3118.shulkersstructures.ShulkersStructures;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class TowerStructure extends Structure<NoFeatureConfig> {

    public TowerStructure(Codec<NoFeatureConfig> codec) {
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
        return ShulkersStructures.MODID + ":tower";
    }

    /*
     * This is how the worldgen code will start the generation of our structure when it passes the checks.
     */
    @Override
    public Structure.IStartFactory getStartFactory() {
        return TowerStructure.Start::new;
    }

    /*
     * This is used so that if two structure's has the same spawn location algorithm, they will not end up in perfect sync as long as they have different seed
     * modifier.
     * 
     * So make this a big random number that is unique only to this structure.
     */
    protected int getSeedModifier() {
        return 345346547;
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
            TowerPieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand);

            // Sets the bounds of the structure.
            this.recalculateStructureSize();

            // I use to debug and quickly find out if the structure is spawning or not and
            // where it is.
            ShulkersStructures.LOGGER.log(Level.DEBUG, "Tower at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
        }

    }

}
