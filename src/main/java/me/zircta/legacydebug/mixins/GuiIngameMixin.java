package me.zircta.legacydebug.mixins;

import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.zircta.legacydebug.utils.DebugUtils.mc;

/**
 * Mixin class injecting modifications into the GuiIngame class to control the display of the crosshair.
 * Conditions are checked to determine whether the crosshair should be shown based on debug settings.
 */
@Mixin(GuiIngame.class)
public class GuiIngameMixin {

    /**
     * Method injected into showCrosshair in GuiIngame class to modify make it display while the debug menu is open.
     */
    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    public void legacyDebug$showNormalCrosshair(CallbackInfoReturnable<Boolean> cir) {
        // Checking if the debug menu is open
        if (mc.gameSettings.showDebugInfo) {
            // If true. Set the return value to true, thus enabling the normal crosshair.
            cir.setReturnValue(true);
        }
        // Else, the method continues like normal.
    }
}

