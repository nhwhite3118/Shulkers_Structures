package com.nhwhite3118.structures.yakhchal;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.nhwhite3118.shulkersstructures.ShulkersStructures;
import com.nhwhite3118.structures.GenerationInformation;
import com.nhwhite3118.structures.Structures;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class YakhchalPieces {
    private static final ResourceLocation YAKHCHAL = new ResourceLocation(ShulkersStructures.MODID + ":yakhchal");

    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.<ResourceLocation, BlockPos>builder().put(YAKHCHAL, new BlockPos(0, -6, 0))
            .build();

    public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
        pieceList.add(new YakhchalPieces.Piece(templateManager, YAKHCHAL, pos, rotation));

    }

    public static class Piece extends TemplateStructurePiece {
        private ResourceLocation resourceLocation;
        private Rotation rotation;

        public Piece(GenerationInformation generationInfo, ResourceLocation resourceLocationIn) {
            super(Structures.FOR_REGISTERING_YAKHCHAL, 0);
            this.resourceLocation = resourceLocationIn;
            BlockPos blockpos = YakhchalPieces.OFFSET.get(resourceLocation);
            this.templatePosition = generationInfo.position.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.rotation = generationInfo.rotation;
            this.setupPiece(generationInfo.templateManager);
        }

        public Piece(GenerationInformation generationInfo, ResourceLocation resourceLocationIn, BlockPos positionOverride) {
            super(Structures.FOR_REGISTERING_YAKHCHAL, 0);
            this.resourceLocation = resourceLocationIn;
            BlockPos blockpos = YakhchalPieces.OFFSET.get(resourceLocation);
            this.templatePosition = positionOverride.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.rotation = generationInfo.rotation;
            this.setupPiece(generationInfo.templateManager);
        }

        public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(Structures.FOR_REGISTERING_YAKHCHAL, tagCompound);
            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.setupPiece(templateManagerIn);
        }

        private void setupPiece(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
            if (template == null) {
                ShulkersStructures.LOGGER.info("Unable to find file at: " + this.resourceLocation.getPath());
            }
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
            this.setup(template, this.templatePosition, placementsettings);
        }

        public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
            super(Structures.FOR_REGISTERING_YAKHCHAL, 0);
            this.resourceLocation = resourceLocationIn;
            BlockPos blockpos = YakhchalPieces.OFFSET.get(resourceLocation);
            this.templatePosition = pos.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.rotation = rotationIn;
            this.setupPiece(templateManagerIn);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.resourceLocation.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
//            if (function.startsWith("Sentry")) {
//                ShulkerEntity shulkerentity = EntityType.SHULKER.create(worldIn.getWorld());
//                shulkerentity.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
//                shulkerentity.setAttachmentPos(pos);
//                worldIn.addEntity(shulkerentity);
//                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 1 | 2);
//            } else if ("chest".equals(function)) {
//                worldIn.setBlockState(pos, Blocks.SHULKER_BOX.getDefaultState(), 2);
//                TileEntity tileentity = worldIn.getTileEntity(pos);
//
//                // Just another check to make sure everything is going well before we try to set
//                // the chest.
//                if (tileentity instanceof ShulkerBoxTileEntity) {
//                    ((ShulkerBoxTileEntity) tileentity).setLootTable(FACTORY_LOOT, rand.nextLong());
//
//                }
//            }
        }

        @Override
        public boolean func_225577_a_(IWorld worldIn, ChunkGenerator<?> p_225577_2_, Random randomIn, MutableBoundingBox structureBoundingBoxIn,
                ChunkPos chunkPos) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
            BlockPos blockpos = YakhchalPieces.OFFSET.get(this.resourceLocation);
            this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(0 - blockpos.getX(), 0, 0 - blockpos.getZ())));

            return super.func_225577_a_(worldIn, p_225577_2_, randomIn, structureBoundingBoxIn, chunkPos);
        }
    }

}
