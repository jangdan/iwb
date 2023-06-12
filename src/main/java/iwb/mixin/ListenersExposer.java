package iwb.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.gen.Invoker;
import java.util.List;
import net.minecraft.world.border.WorldBorderListener;
import org.spongepowered.asm.mixin.gen.Accessor;

@ Mixin (WorldBorder.class) interface ListenersExposer {
  @ Invoker ("getListeners") List<WorldBorderListener> invokeGetListeners();
  @ Accessor ("listeners") List<WorldBorderListener> accessListeners();
}
