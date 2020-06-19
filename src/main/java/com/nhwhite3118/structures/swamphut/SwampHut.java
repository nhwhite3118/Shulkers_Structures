package com.nhwhite3118.structures.swamphut;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.nhwhite3118.shulkersstructures.ShulkersStructures;

import net.minecraft.block.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public class SwampHut extends Feature<NoFeatureConfig> {
    private static ResourceLocation SWAMP_HUT_FILE = new ResourceLocation(ShulkersStructures.MODID + ":swamp_hut");

    public SwampHut(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) {
        Rotation rotation = Rotation.randomRotation(rand);
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
        BlockPos.Mutable blockpos$MutableOffsetVector = new BlockPos.Mutable();

        // I don't care anymore about compatibility, it's late and I want this working before I go to sleep
//        int y = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, position).getY() - 1;
        int y = 62;
        int yDiff = y - position.getY();

        boolean validPositionFound = false;

        // This is trying to do it the right way. I'll revisit it when I've had more sleep
//        for (int checkX = 40; checkX >= -40 && validPositionFound == false; checkX -= 2) {
//            for (int checkZ = 40; checkZ >= -40 && validPositionFound == false; checkZ -= 2) {
//                if (world.getBlockState(blockpos$Mutable.setPos(position.add(blockpos$MutableOffsetVector.setPos(checkX, yDiff, checkZ).rotate(rotation))))
//                        .getBlock() == Blocks.WATER) {
//                    if (world
//                            .getBlockState(
//                                    blockpos$Mutable.setPos(position.add(blockpos$MutableOffsetVector.setPos(checkX - 1, yDiff, checkZ).rotate(rotation))))
//                            .getBlock() != Blocks.WATER) {
//                        // we probably hit land. maybe a ship or tree. w/e
//                        if (outlineFits(world, blockpos$Mutable, rotation)) {
//                            validPositionFound = true;
//                        }
//                    }
//                }
//            }
//        }
        if (world.getBlockState(blockpos$Mutable.setPos(position.getX(), y, position.getZ())).getBlock() == Blocks.WATER) {
            if (world.getBlockState(new BlockPos(position.add(blockpos$MutableOffsetVector.setPos(9, yDiff, 14).rotate(rotation))))
                    .getBlock() == Blocks.WATER) {
                // we probably hit land. maybe a ship or tree. w/e
                // if (outlineFits(world, blockpos$Mutable, rotation)) {
                validPositionFound = true;
                // }
            }
        }
        // Checking all those blocks is expensive. We want to avoid ruining performance too badly
        if (validPositionFound == false) {
            return false;
        }
        blockpos$Mutable.setPos(blockpos$Mutable.down(4));

        TemplateManager templateManager = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.getTemplate(SWAMP_HUT_FILE);
        if (template == null) {
            ShulkersStructures.LOGGER.warn("Swamp Hut NBT file does not exist! Path provided is as follows: " + SWAMP_HUT_FILE.getPath());
            return false;
        }
        PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false).setChunk(null);
        template.addBlocksToWorld(world, blockpos$Mutable, placementsettings);

        return true;
    }

//    private boolean outlineFits(IWorld world, BlockPos positionCandidate, Rotation rotation) {
//        boolean valid = (world.getBlockState(positionCandidate.add(new BlockPos(9, 0, 0).rotate(rotation))).getBlock() != Blocks.WATER
//                || world.getBlockState(positionCandidate.add(new BlockPos(8, 0, 0).rotate(rotation))).getBlock() != Blocks.WATER)
//                && (world.getBlockState(positionCandidate.add(new BlockPos(9, 0, -14).rotate(rotation))).getBlock() == Blocks.WATER
//                        || world.getBlockState(positionCandidate.add(new BlockPos(8, 0, -13).rotate(rotation))).getBlock() == Blocks.WATER);
//        return valid;
//    }
}
