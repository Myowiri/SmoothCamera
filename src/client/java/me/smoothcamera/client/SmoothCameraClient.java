package me.smoothcamera.client;

import net.fabricmc.api.ClientModInitializer;

public class SmoothCameraClient implements ClientModInitializer {
	public static SmoothCameraController camera;

	@Override
	public void onInitializeClient() {
		SmoothCameraConfig.load();
		camera = new SmoothCameraController();
		SmoothCameraCommands.register();
	}
}