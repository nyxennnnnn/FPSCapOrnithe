
package dev.tiller.customfpscap.mixin;

import dev.tiller.customfpscap.config.FpsCapConfig;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftFpsCapMixin {

    @Inject(method = "getMaxFramerate", at = @At("HEAD"), cancellable = true)
    private void customfpscap$overrideLimit(CallbackInfoReturnable<Integer> cir) {
        int limit = FpsCapConfig.getLimit();
        if (limit > 0) {
            cir.setReturnValue(limit);
        }
    }

    @Inject(method = "isFramerateValid", at = @At("HEAD"), cancellable = true)
    private void customfpscap$enableFrameSync(CallbackInfoReturnable<Boolean> cir) {
        if (FpsCapConfig.getLimit() > 0) {
            cir.setReturnValue(true);
        }
    }
}
