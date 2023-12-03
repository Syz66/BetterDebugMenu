package me.zircta.legacydebug.utils;

import com.google.common.collect.Lists;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;

import java.awt.*;
import java.util.List;

/**
 * Utility class for retrieving debug information.
 */
public class DebugUtils {
    // Instance of Minecraft
    public static final Minecraft mc = Minecraft.getMinecraft();

    // Text color constant
    public static final int textColor = new Color(224, 224, 224).getRGB();

    /**
     * Retrieves various debug information related to the Minecraft client's left display.
     * @return A list of strings containing debug information for the left display.
     */
    public static List<String> getDebugInfoLeft() {
        // Getting the position of the player's view and relevant information
        BlockPos blockpos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
        Entity entity = mc.getRenderViewEntity();
        EnumFacing enumfacing = entity.getHorizontalFacing();
        Chunk chunk = mc.theWorld.getChunkFromBlockCoords(blockpos);

        // Constructing a list of debug information strings
        List<String> list = Lists.newArrayList(
                "Minecraft 1.8.9 (" + Minecraft.getDebugFPS() + " fps" + ", " + RenderChunk.renderChunksUpdated + " chunk updates)",
                mc.renderGlobal.getDebugInfoRenders(),
                mc.renderGlobal.getDebugInfoEntities(),
                "P: " + mc.effectRenderer.getStatistics() + ". T: " + mc.theWorld.getDebugLoadedEntities(),
                mc.theWorld.getProviderName(),
                "",
                String.format("x: %.5f (%d) // c: %d (%d)", mc.thePlayer.posX, MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posX) >> 4, MathHelper.floor_double(mc.thePlayer.posX) & 15),
                String.format("y: %.3f (feet pos, %.3f eyes pos)", mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posY + mc.thePlayer.getEyeHeight()),
                String.format("z: %.5f (%d) // c: %d (%d)", mc.thePlayer.posZ, MathHelper.floor_double(mc.thePlayer.posZ), MathHelper.floor_double(mc.thePlayer.posZ) >> 4, MathHelper.floor_double(mc.thePlayer.posZ) & 15),
                "f: " + (MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + " (" + enumfacing.toString().toUpperCase() + ") / " + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw),
                String.format("ws: %.3f, fs: %.3f, g: %b, fl: %.0f", mc.thePlayer.capabilities.getWalkSpeed(), mc.thePlayer.capabilities.getFlySpeed(), mc.thePlayer.onGround, mc.thePlayer.posY),
                String.format("lc: " + chunk.getLightSubtracted(blockpos, 0) + " b: " + chunk.getBiome(blockpos, mc.theWorld.getWorldChunkManager()).biomeName) + " bl: " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " sl: " + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " rl: " + chunk.getLightSubtracted(blockpos, 0)
        );

        // Checking for active shaders and adding shader info if applicable
        if (mc.entityRenderer != null && mc.entityRenderer.isShaderActive()) {
            list.add("shader: " + mc.entityRenderer.getShaderGroup().getShaderGroupName());
        }

        return list;
    }

    /**
     * Retrieves memory related debug information for the Minecraft client's right display.
     * @return A list of strings containing memory usage statistics and client information for the right display.
     */
    public static List<String> getDebugInfoRight() {
        // Memory usage statistics
        long maxMem = Runtime.getRuntime().maxMemory() / 1048576L;
        long totalMem = Runtime.getRuntime().totalMemory() / 1048576L;
        long freeMem = Runtime.getRuntime().freeMemory() / 1048576L;
        long usableMem = totalMem - freeMem;

        // Constructing a list of memory related debug information strings
        List<String> list = Lists.newArrayList(
                String.format("Used memory: % 2d%% (%03dMB) of %03dMB", usableMem * 100L / maxMem, usableMem, maxMem),
                String.format("Allocated memory: % 2d%% (%03dMB)", totalMem * 100L / maxMem, totalMem),
                ""
        );

        // Adding Minecraft version and client mod name(s) to the list
        list.add(mc.getVersion());
        list.addAll(List.of(ClientBrandRetriever.getClientModName().split(":")));

        return list;
    }
}
