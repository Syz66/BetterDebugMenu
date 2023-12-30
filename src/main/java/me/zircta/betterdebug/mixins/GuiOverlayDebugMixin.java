package me.zircta.betterdebug.mixins;

import com.google.common.base.Strings;
import me.zircta.betterdebug.BetterDebugMenu;
import me.zircta.betterdebug.config.BetterDebugConfig;
import me.zircta.betterdebug.utils.DebugUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.*;

import java.util.List;

@Mixin(value = GuiOverlayDebug.class)
public abstract class GuiOverlayDebugMixin {
    @Mutable @Final @Shadow public FontRenderer fontRenderer;

    @Shadow public abstract List<String> getDebugInfoRight();

    @Shadow public abstract List<String> call();

    public GuiOverlayDebugMixin(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    /**
     * @author Syz66
     * @reason Changing debug info / style
     */
    @Overwrite
    public void renderDebugInfoLeft() {
        BetterDebugConfig config = BetterDebugMenu.config;

        List<String> debugInfo;
        if (config.information == 0) {
            debugInfo = DebugUtils.getDebugInfoLeft();
        } else {
            debugInfo = call();
        }

        for (int lineIndex = 0; lineIndex < debugInfo.size(); ++lineIndex) {
            String debugLine = debugInfo.get(lineIndex);
            if (!Strings.isNullOrEmpty(debugLine)) {
                int lineHeight = this.fontRenderer.FONT_HEIGHT;
                int lineWidth = this.fontRenderer.getStringWidth(debugLine);
                int yPos = 2 + lineHeight * lineIndex;
                if (config.background) Gui.drawRect(1, yPos - 1, 2 + lineWidth + 1, yPos + lineHeight - 1, config.backgroundColor.getRGB());
                this.fontRenderer.drawString(debugLine, 2, yPos, config.textColor.getRGB(), config.shadow);
            }
        }
    }

    /**
     * @author Syz66
     * @reason Changing debug info / style
     */
    @Overwrite
    public void renderDebugInfoRight(ScaledResolution scaledResolution) {
        BetterDebugConfig config = BetterDebugMenu.config;

        List<String> debugInfo;
        if (BetterDebugMenu.config.information == 0) {
            debugInfo = DebugUtils.getDebugInfoRight();
        } else {
            debugInfo = getDebugInfoRight();
        }

        for (int lineIndex = 0; lineIndex < debugInfo.size(); ++lineIndex) {
            String debugLine = debugInfo.get(lineIndex);
            if (!Strings.isNullOrEmpty(debugLine)) {
                int lineHeight = this.fontRenderer.FONT_HEIGHT;
                int lineWidth = this.fontRenderer.getStringWidth(debugLine);
                int xPos = scaledResolution.getScaledWidth() - 2 - lineWidth;
                int yPos = 2 + lineHeight * lineIndex;
                if (config.background) Gui.drawRect(xPos - 1, yPos - 1, xPos + lineWidth + 1, yPos + lineHeight - 1, config.backgroundColor.getRGB());
                this.fontRenderer.drawString(debugLine, xPos, yPos, config.textColor.getRGB(), config.shadow);
            }
        }
    }

}
