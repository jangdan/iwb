package iwb.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import iwb.WorldBorderWithListenersExposed;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.world.border.WorldBorder;
import java.util.List;
import net.minecraft.world.border.WorldBorderListener;

@ Mixin (WorldBorder.class) abstract class AlternativeListenersExposer implements WorldBorderWithListenersExposed {
  @ Shadow abstract List<WorldBorderListener> getListeners();
  public List<WorldBorderListener> stealListeners() {
    return getListeners();
  }
}
