package meow.cats.gui;

import meow.cats.SmoothCameraConfig;
import meow.cats.gui.items.Button;
import meow.cats.gui.items.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.transformer.Config;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {


    private final List<Button> buttons = new ArrayList<>();
    private final List<Slider> sliders = new ArrayList<>();


    public static ConfigScreen INSTANCE;
    public int Width = 160;
    public int Height = 20;


    protected ConfigScreen() {
        super(Component.literal("SmoothCamera"));
        setInstance();
        addButtons();
    }

    static {
        INSTANCE = new ConfigScreen();
    }

    private void setInstance() {
        INSTANCE = this;
    }


    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        for (Button button : buttons) {
            button.mouseClicked((int)click.x(), (int)click.y());
        }
        for (Slider slider : sliders) {
            slider.mouseClicked((int)click.x(), (int)click.y());
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(
            MouseButtonEvent click,
            double deltaX,
            double deltaY
    ) {
        int mouseX = (int) click.x();
        int mouseY = (int) click.y();

        for (Slider slider : sliders) {
            slider.mouseDragged((int)click.x(), (int)click.y());
        }

        return super.mouseDragged(click, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent click) {
        for (Slider slider : sliders) {
            slider.mouseReleased();
        }

        SmoothCameraConfig.save();

        return super.mouseReleased(click);
    }

    public void addButtons(){


        buttons.add(new Button(SmoothCameraConfig.INSTANCE.enabled, "Mod", value -> SmoothCameraConfig.INSTANCE.enabled = value));

        buttons.add(new Button(SmoothCameraConfig.INSTANCE.smoothTeleport, "Smooth Teleport", value -> SmoothCameraConfig.INSTANCE.smoothTeleport = value));

        sliders.add(new Slider("TP Smoothness", SmoothCameraConfig.INSTANCE.smoothness, 0.001f, 0.5f, value -> {SmoothCameraConfig.INSTANCE.smoothness = value;SmoothCameraConfig.save();}));

        sliders.add(new Slider("F5 Smoothness", SmoothCameraConfig.INSTANCE.smoothnessf5, 0.001f, 0.1f, value -> {SmoothCameraConfig.INSTANCE.smoothnessf5 = value;SmoothCameraConfig.save();}));

    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        int buttonWidth = ConfigScreen.INSTANCE.Width;
        int buttonHeight = ConfigScreen.INSTANCE.Height;
        int spacing = 5;

        int x = (screenWidth - buttonWidth) / 2;
        int totalHeight = buttonHeight * 2 + spacing;
        int y = (screenHeight - totalHeight) / 2 - 50;

        for (Button button : buttons) {
            button.render(context, x, y);
            y += ConfigScreen.INSTANCE.Height + 5;
        }

        for (Slider slider : sliders) {
            slider.render(context, x, y);
            y += ConfigScreen.INSTANCE.Height + 5;
        }

    }
}
