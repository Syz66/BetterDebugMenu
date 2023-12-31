package me.zircta.betterdebug.mixins;

import me.zircta.betterdebug.BetterDebugMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class)
public class EntityRendererMixin {
    @Shadow public Minecraft mc;

    @Inject(method = "renderWorldDirections", at = @At("HEAD"), cancellable = true)
    public void legacyDebug$removeDebugCrosshair(float partialTicks, CallbackInfo ci) {
        if (BetterDebugMenu.config != null) {
            if (BetterDebugMenu.config.crosshair == 0) {
                ci.cancel();
            } else if (BetterDebugMenu.config.crosshairfix && mc.gameSettings.thirdPersonView > 0) {
                ci.cancel();
            }
        }
    }
}