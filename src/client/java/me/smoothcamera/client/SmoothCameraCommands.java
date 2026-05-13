package me.smoothcamera.client;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class SmoothCameraCommands {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {

            dispatcher.register(literal("smoothcamera")

                .then(literal("toggle")
                    .executes(ctx -> {
                        SmoothCameraConfig.INSTANCE.enabled = !SmoothCameraConfig.INSTANCE.enabled;
                        SmoothCameraConfig.save();

                        send("Enabled: " + SmoothCameraConfig.INSTANCE.enabled);
                        return 1;
                    })
                )

                    .then(literal("smoothness")
                            .then(argument("value", FloatArgumentType.floatArg(0.001f, 0.5f))
                                    .executes(ctx -> {
                                        float value = FloatArgumentType.getFloat(ctx, "value");
                                        SmoothCameraConfig.INSTANCE.smoothness = value;
                                        SmoothCameraConfig.save();
                                        send("Smoothness: " + value);
                                        return 1;
                                    })
                            )
                    )

                    .then(literal("f5")
                            .then(argument("value", FloatArgumentType.floatArg(0.001f, 0.1f))
                                    .executes(ctx -> {
                                        float value = FloatArgumentType.getFloat(ctx, "value");
                                        SmoothCameraConfig.INSTANCE.smoothnessf5 = value;
                                        SmoothCameraConfig.save();
                                        send("F5 Smoothness: " + value);
                                        return 1;
                                    })
                            )
                    )
            );
        });
    }

    private static void send(String text) {
        Minecraft.getInstance().player.displayClientMessage(Component.literal("[SmoothCamera] " + text), true);
    }
}