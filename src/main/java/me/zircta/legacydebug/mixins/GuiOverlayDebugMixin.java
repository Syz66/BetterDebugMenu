package me.zircta.legacydebug.mixins;

import com.google.common.base.Strings;
import me.zircta.legacydebug.utils.DebugUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.util.List;

@Mixin(GuiOverlayDebug.class)
public abstract class GuiOverlayDebugMixin {
    @Shadow
    public final FontRenderer fontRenderer;

    protected GuiOverlayDebugMixin(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    /**
     * @author Minecraft
     * @reason Legacy Debug Menu
     */
    @Overwrite
    public void renderDebugInfoLeft() {
        List<String> debugInfo = DebugUtils.getDebugInfoLeft();

        for(int lineIndex = 0; lineIndex < debugInfo.size(); ++lineIndex) {
            String debugLine = debugInfo.get(lineIndex);
            if(!Strings.isNullOrEmpty(debugLine)) {
                int lineHeight = this.fontRenderer.FONT_HEIGHT;
                int yOffset = 2 + lineHeight * lineIndex;
                int textColor = new Color(224, 224, 224).getRGB();
                this.fontRenderer.drawString(debugLine, 2, yOffset, textColor, true);
            }
        }
    }

    /**
     * @author Minecraft
     * @reason Legacy Debug Menu
     */
    @Overwrite
    public void renderDebugInfoRight(ScaledResolution scaledResolution) {
        List<String> debugInfo = DebugUtils.getDebugInfoRight();

        for(int lineIndex = 0; lineIndex < debugInfo.size(); ++lineIndex) {
            String debugLine = debugInfo.get(lineIndex);
            if(!Strings.isNullOrEmpty(debugLine)) {
                int lineHeight = this.fontRenderer.FONT_HEIGHT;
                int lineWidth = this.fontRenderer.getStringWidth(debugLine);
                int xPos = scaledResolution.getScaledWidth() - 2 - lineWidth;
                int yPos = 2 + lineHeight * lineIndex;
                int textColor = new Color(224, 224, 224).getRGB();
                this.fontRenderer.drawString(debugLine, xPos, yPos, textColor, true);
            }
        }
    }
}
