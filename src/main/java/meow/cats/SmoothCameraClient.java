package meow.cats;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmoothCameraClient  implements ClientModInitializer {
	public static final String MOD_ID = "smoothcamera";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static SmoothCameraController camera;

	@Override
	public void onInitializeClient() {
		SmoothCameraConfig.load();
		camera = new SmoothCameraController();
		SmoothCameraCommands.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
