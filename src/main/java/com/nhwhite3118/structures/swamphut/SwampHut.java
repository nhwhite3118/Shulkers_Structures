package com.nhwhite3118.structures.swamphut;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.nhwhite3118.shulkersstructures.ShulkersStructures;

import net.minecraft.block.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public class SwampHut extends Feature<NoFeatureConfig> {
    private static ResourceLocation SWAMP_HUT_FILE = new ResourceLocation(ShulkersStructures.MODID + ":swamp_hut");

    public SwampHut(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader world, StructureManager structureManager, ChunkGenerator changedBlock, Random rand, BlockPos position,
            NoFeatureConfig p_212245_5_) {
        if (rand.nextInt(150) != 0) {
            return false;
        }
        Rotation rotation = Rotation.randomRotation(rand);
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
        BlockPos.Mutable blockpos$MutableOffsetVector = new BlockPos.Mutable();

        int y = 62;
        int yDiff = y - position.getY();

        boolean validPositionFound = false;
        if (world.getBlockState(blockpos$Mutable.setPos(position.getX(), y, position.getZ())).getBlock() == Blocks.WATER) {
            if (world.getBlockState(new BlockPos(position.add(blockpos$MutableOffsetVector.setPos(9, yDiff, 14).rotate(rotation))))
                    .getBlock() == Blocks.WATER) {
                validPositionFound = true;
            }
        }
        if (validPositionFound == false) {
            return false;
        }
        blockpos$Mutable.setPos(blockpos$Mutable.down(4));

        TemplateManager templateManager = ((ServerWorld) world.getWorld()).getStructureTemplateManager();
        Template template = templateManager.getTemplate(SWAMP_HUT_FILE);
        if (template == null) {
            ShulkersStructures.LOGGER.warn("Swamp Hut NBT file does not exist! Path provided is as follows: " + SWAMP_HUT_FILE.getPath());
            return false;
        }
        PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(rotation).setIgnoreEntities(false).setChunk(null);
        template.func_237144_a_(world, blockpos$Mutable, placementsettings, rand);

        return true;
    }
}
