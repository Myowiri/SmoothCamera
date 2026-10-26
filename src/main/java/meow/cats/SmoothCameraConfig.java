package meow.cats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class SmoothCameraConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH =
            FabricLoader.getInstance().getConfigDir().resolve("smoothcamera.json");

    public boolean enabled = true;
    public boolean SmoothTeleport = true;
    public float TPSmoothness = 0.5f;
    public float F5Smoothness = 0.01f;

    public static SmoothCameraConfig INSTANCE = new SmoothCameraConfig();

    public static void load() {
        try {
            File file = PATH.toFile();

            if (file.exists()) {
                INSTANCE = GSON.fromJson(new FileReader(file), SmoothCameraConfig.class);
                if (INSTANCE == null) {
                    INSTANCE = new SmoothCameraConfig();
                }
            } else {
                INSTANCE = new SmoothCameraConfig();
                save();
                return;
            }

            INSTANCE.validate();
            save();
        } catch (Exception e) {
            e.printStackTrace();
            INSTANCE = new SmoothCameraConfig();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(PATH.toFile())) {
            INSTANCE.validate();
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validate() {
        TPSmoothness = clamp(TPSmoothness, 0.01f, 0.5f);
        F5Smoothness = clamp(F5Smoothness, 0.01f, 0.1f);
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}