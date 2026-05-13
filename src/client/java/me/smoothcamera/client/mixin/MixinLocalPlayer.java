package me.smoothcamera.client.mixin;

import me.smoothcamera.client.SmoothCameraClient;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @Inject(method = "tick", at = @At("RETURN"))
    private void smoothcamera$tickHook(CallbackInfo ci) {
        if (SmoothCameraClient.camera == null) return;

        LocalPlayer player = (LocalPlayer)(Object)this;
        SmoothCameraClient.camera.tick(player.position());
    }
}