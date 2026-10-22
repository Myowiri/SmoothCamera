package meow.cats.mixin;

import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FeatureRenderDispatcher.PreparedFrame.class)
public interface MixinPreparedFrameAccessor {

    @Accessor("submitNodeStorage")
    SubmitNodeStorage getSubmitNodeStorage();
}