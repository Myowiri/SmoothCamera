package me.smoothcamera.client.mixin;

import me.smoothcamera.client.SmoothCameraClient;
import me.smoothcamera.client.SmoothCameraConfig;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class MixinCamera {

    @Shadow private Vec3 position;
    @Shadow protected abstract void setPosition(Vec3 vec3);
    @Shadow protected abstract void move(float f, float g, float h);
    @Inject(method = "setup", at = @At("RETURN"))
    private void smoothcamera$applyOffset(Level level, Entity entity, boolean detached, boolean thirdPersonReverse, float tickDelta, CallbackInfo ci) {
        if (SmoothCameraClient.camera == null) return;
        if (!SmoothCameraConfig.INSTANCE.enabled) return;

        Vec3 offset = SmoothCameraClient.camera.getOffset(tickDelta);
        if (!offset.equals(Vec3.ZERO)) {
            this.setPosition(this.position.add(offset));
        }
    }
    @Inject(method = "setup", at = @At("RETURN"))
    private void onSetupReturn(Level level, Entity entity, boolean bl, boolean bl2, float f, CallbackInfo ci) {
        if (!SmoothCameraConfig.INSTANCE.enabled) {
            return;
        }
            float smoothDist = SmoothCameraClient.camera.getSmoothF5Dist(f, !bl);
            if (!bl && smoothDist > 0.01f) {
                this.move(-smoothDist, 0.0f, 0.0f);
            } else if (bl) {
                this.move(4.0f - smoothDist, 0.0f, 0.0f);
            }
    }
}