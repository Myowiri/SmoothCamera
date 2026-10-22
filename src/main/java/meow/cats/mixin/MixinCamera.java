package meow.cats.mixin;

import meow.cats.SmoothCameraClient;
import meow.cats.SmoothCameraConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class MixinCamera {


    @Shadow private Vec3 position;
    @Shadow protected abstract void setPosition(Vec3 vec3);
    @Shadow protected abstract void move(float f, float g, float h);
    @Inject(method = "update", at = @At("RETURN"))
    private void smoothcamera$applyOffset(DeltaTracker deltaTracker, CallbackInfo ci) {
        if (SmoothCameraClient.camera == null) return;
        if (!SmoothCameraConfig.INSTANCE.enabled) return;
        float f = deltaTracker.getGameTimeDeltaPartialTick(false);
        Vec3 offset = SmoothCameraClient.camera.getOffset(f);
        if (!offset.equals(Vec3.ZERO)) {
            this.setPosition(this.position.add(offset));
        }
    }
    @Inject(method = "update", at = @At("RETURN"))
    private void onSetupReturn(DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!SmoothCameraConfig.INSTANCE.enabled) {
            return;
        }
        float f = deltaTracker.getGameTimeDeltaPartialTick(false);
            boolean bl = !Minecraft.getInstance().options.getCameraType().isFirstPerson();
            float smoothDist = SmoothCameraClient.camera.getSmoothF5Dist(f, !bl);
            if (!bl && smoothDist > 0.01f) {
                this.move(-smoothDist, 0.0f, 0.0f);
            } else if (bl) {
                this.move(4.0f - smoothDist, 0.0f, 0.0f);
            }
    }
}