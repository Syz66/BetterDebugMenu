package me.zircta.betterdebug.mixins;

import me.zircta.betterdebug.BetterDebugMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GuiIngame.class)
public class GuiIngameMixin {
    @Shadow @Final
    public Minecraft mc;

    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    public void legacyDebug$showNormalCrosshair(CallbackInfoReturnable<Boolean> cir) {
        if (BetterDebugMenu.config != null && BetterDebugMenu.config.crosshair == 0) {
            if (mc.gameSettings.showDebugInfo) {
                cir.setReturnValue(true);
            }
        }
    }
}
