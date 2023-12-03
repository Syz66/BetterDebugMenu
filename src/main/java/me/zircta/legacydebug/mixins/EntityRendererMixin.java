package me.zircta.legacydebug.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin class to modify the behavior of the renderWorldDirections method in the EntityRenderer class.
 * Cancels the execution of renderWorldDirections.
 */
@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    /**
     * Injected method to cancel the execution of renderWorldDirections.
     */
    @Inject(method = "renderWorldDirections", at = @At("HEAD"), cancellable = true)
    public void legacyDebug$removeDebugCrosshair(float v, CallbackInfo ci) {
        // Cancels the renderWorldDirections method, thus removing the debug crosshair.
        ci.cancel();
    }
}

