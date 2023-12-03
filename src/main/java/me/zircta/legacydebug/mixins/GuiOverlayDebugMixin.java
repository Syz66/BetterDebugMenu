package me.zircta.legacydebug.mixins;

import com.google.common.base.Strings;
import me.zircta.legacydebug.utils.DebugUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.*;

import java.util.List;

/**
 * Mixin class to modify the rendering of debug information in Minecraft's debug overlay.
 * Renders debug information on the left and right sides of the screen.
 */
@Mixin(GuiOverlayDebug.class)
public class GuiOverlayDebugMixin {
    @Mutable @Final @Shadow public FontRenderer fontRenderer;

    /**
     * Mixin constructor for GuiOverlayDebugMixin.
     */
    public GuiOverlayDebugMixin(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    /**
     * Overwrites the renderDebugInfoLeft method to display custom debug information on the left side of the screen.
     * @author Minecraft // Syz66
     * @reason Legacy Debug Menu
     */
    @Overwrite
    public void renderDebugInfoLeft() {
        // Retrieve debug information from DebugUtils for the left display
        List<String> debugInfo = DebugUtils.getDebugInfoLeft();

        // Render each line of debug information
        for (int lineIndex = 0; lineIndex < debugInfo.size(); ++lineIndex) {
            String debugLine = debugInfo.get(lineIndex);
            if (!Strings.isNullOrEmpty(debugLine)) {
                int lineHeight = this.fontRenderer.FONT_HEIGHT;
                int yOffset = 2 + lineHeight * lineIndex;
                // Render the debug line on the left side of the screen
                this.fontRenderer.drawString(debugLine, 2, yOffset, DebugUtils.textColor, true);
            }
        }
    }

    /**
     * Overwrites the renderDebugInfoRight method to display custom debug information on the right side of the screen.
     * @author Minecraft // Syz66
     * @reason Legacy Debug Menu
     */
    @Overwrite
    public void renderDebugInfoRight(ScaledResolution scaledResolution) {
        // Retrieve debug information from DebugUtils for the right display
        List<String> debugInfo = DebugUtils.getDebugInfoRight();

        // Render each line of debug information
        for (int lineIndex = 0; lineIndex < debugInfo.size(); ++lineIndex) {
            String debugLine = debugInfo.get(lineIndex);
            if (!Strings.isNullOrEmpty(debugLine)) {
                int lineHeight = this.fontRenderer.FONT_HEIGHT;
                int lineWidth = this.fontRenderer.getStringWidth(debugLine);
                int xPos = scaledResolution.getScaledWidth() - 2 - lineWidth;
                int yPos = 2 + lineHeight * lineIndex;
                // Render the debug line on the right side of the screen
                this.fontRenderer.drawString(debugLine, xPos, yPos, DebugUtils.textColor, true);
            }
        }
    }
}

