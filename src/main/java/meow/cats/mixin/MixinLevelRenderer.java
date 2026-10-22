package meow.cats.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import meow.cats.SmoothCameraClient;
import meow.cats.SmoothCameraConfig;
import meow.cats.SmoothCameraController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
	@Inject(
			method="render",
			at=@At(
					value="INVOKE",
					target="Lnet/minecraft/client/renderer/LevelTargetBundle;clear()V",
					shift=At.Shift.AFTER
			)
	)
	private void yumehook$render3D(
			GraphicsResourceAllocator resourceAllocator, boolean renderOutline, CameraRenderState cameraState, GpuBufferSlice terrainFog, Vector4f fogColor, boolean shouldRenderSky, boolean consistentDepthRequired, CallbackInfo ci, @Local(name="featureFrame") FeatureRenderDispatcher.PreparedFrame featureFrame
	){
		SmoothCameraController.begin(featureFrame);
		if (!SmoothCameraConfig.INSTANCE.smoothTeleport) return;
		try{
			LocalPlayer player = Minecraft.getInstance().player;
			if (player != null) {
				SmoothCameraClient.camera.tick(player.position());
			}
		}finally{
			SmoothCameraController.end();
		}

	}

}