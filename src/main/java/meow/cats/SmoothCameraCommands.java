package meow.cats;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class SmoothCameraCommands {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {

            dispatcher.register(literal("SmoothCamera")

                .then(literal("toggle")
                    .executes(ctx -> {
                        SmoothCameraConfig.INSTANCE.enabled = !SmoothCameraConfig.INSTANCE.enabled;
                        SmoothCameraConfig.save();

                        send("Enabled: " + SmoothCameraConfig.INSTANCE.enabled);
                        return 1;
                    })
                )

                    .then(literal("SmoothTeleport")
                            .executes(ctx -> {
                                SmoothCameraConfig.INSTANCE.SmoothTeleport = !SmoothCameraConfig.INSTANCE.SmoothTeleport;
                                SmoothCameraConfig.save();

                                send("Enabled: " + SmoothCameraConfig.INSTANCE.SmoothTeleport);
                                return 1;
                            })
                    )

                    .then(literal("TPSmoothness")
                            .then(argument("value", FloatArgumentType.floatArg(0.001f, 0.5f))
                                    .executes(ctx -> {
                                        float value = FloatArgumentType.getFloat(ctx, "value");
                                        SmoothCameraConfig.INSTANCE.TPSmoothness = value;
                                        SmoothCameraConfig.save();
                                        send("TP Smoothness: " + value);
                                        return 1;
                                    })
                            )
                    )

                    .then(literal("F5Smoothness")
                            .then(argument("value", FloatArgumentType.floatArg(0.001f, 0.1f))
                                    .executes(ctx -> {
                                        float value = FloatArgumentType.getFloat(ctx, "value");
                                        SmoothCameraConfig.INSTANCE.F5Smoothness = value;
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
        Minecraft.getInstance().player.sendOverlayMessage(Component.literal("[SmoothCamera] " + text));
    }
}