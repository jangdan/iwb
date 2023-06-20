package iwb.mixins;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.border.WorldBorder;
import iwb.ModdedWorldBorder;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import net.minecraft.world.border.WorldBorderListener;

@ Mixin (WorldBorder.class) class modifyWorldBorder implements ModdedWorldBorder {
  public void clearListeners() {
    listeners.clear();
  }
  @ Shadow private List<WorldBorderListener> listeners;
}
