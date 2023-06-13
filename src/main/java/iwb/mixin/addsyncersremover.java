package iwb.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.border.WorldBorder;
import iwb.WorldBorderWithSyncersRemover;
import net.minecraft.world.border.WorldBorderListener.WorldBorderSyncer;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import net.minecraft.world.border.WorldBorderListener;

@ Mixin (WorldBorder.class) class addsyncersremover implements WorldBorderWithSyncersRemover {
  public void removeSyncers() {
    System.out.println(listeners.size());
    listeners.removeIf(listener -> listener instanceof WorldBorderSyncer);
    System.out.println(listeners.size());
  }
  @ Shadow private List<WorldBorderListener> listeners;
}
