package meow.cats;

import meow.cats.mixin.MixinPreparedFrameAccessor;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.world.phys.Vec3;

public class SmoothCameraController {
    private Vec3 offset = Vec3.ZERO;
    private Vec3 lastPlayerPos = null;
    private float currentF5Dist = 0.0f;
    public static SmoothCameraController INSTANCE;
    private static SubmitNodeStorage storage;
    public SmoothCameraController(){
        INSTANCE = this;
    }
    public void tick(Vec3 currentPos) {
        if (lastPlayerPos != null) {
            double dist = currentPos.distanceTo(lastPlayerPos);

            if (dist > 4.0) {
                offset = offset.add(lastPlayerPos.subtract(currentPos));
            }
        }

        lastPlayerPos = currentPos;
    }

    public Vec3 getOffset(float partialTicks) {
        if (!SmoothCameraConfig.INSTANCE.enabled || offset.equals(Vec3.ZERO)) {
            return Vec3.ZERO;
        }

        Vec3 currentOffset = offset;
        double factor = Math.pow(1.0 - SmoothCameraConfig.INSTANCE.TPSmoothness, partialTicks);
        offset = offset.scale(factor);

        if (offset.lengthSqr() < 0.0001) {
            offset = Vec3.ZERO;
        }

        return currentOffset;
    }

    public float getSmoothF5Dist(float tickDelta, boolean isFirstPerson) {
        float target = isFirstPerson ? 0.0f : 4.0f;
        currentF5Dist += (target - currentF5Dist) * (SmoothCameraConfig.INSTANCE.F5Smoothness * tickDelta * 10f);

        if (Math.abs(currentF5Dist - target) < 0.001f) {
            currentF5Dist = target;
        }

        return currentF5Dist;
    }
    public static void begin(FeatureRenderDispatcher.PreparedFrame frame) {
        storage = ((MixinPreparedFrameAccessor) frame).getSubmitNodeStorage();
    }

    public static void end() {
        storage = null;
    }
}