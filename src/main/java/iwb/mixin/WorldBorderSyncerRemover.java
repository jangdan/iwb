package iwb.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import iwb.WorldBorderWithListenersExposed;
import net.minecraft.world.border.WorldBorderListener.WorldBorderSyncer;
import java.util.ArrayList;
import net.minecraft.world.border.WorldBorderListener;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.server.world.ServerWorld;

@ Mixin (MinecraftServer.class) abstract class WorldBorderSyncerRemover {
  @ Inject (method = "createWorlds", at = @At("RETURN"), require = 1) void onCreateWorlds(CallbackInfo ci) {
    System.out.println("SDA");
    for (var world: getWorlds()) {
      /*
      var listeners = ((ListenersExposer) world.getWorldBorder()).invokeGetListeners();
      // var listeners = ((WorldBorderWithListenersExposed) world.getWorldBorder()).stealListeners();
      // var listeners = ((ListenersExposer) world.getWorldBorder()).accessListeners();
      */

      /*
      var a = listeners.removeIf(listener -> listener instanceof WorldBorderSyncer);
      System.out.println(world.getDimensionKey() + " " + a);
      */
      /*
      var listenersToRemove = new ArrayList<WorldBorderListener> ();
      for (var listener: listeners) {
        if (listener instanceof WorldBorderSyncer) {
          listenersToRemove.add(listener);
          System.out.println(listener);
        }
      }
      listeners.removeAll(listenersToRemove);
      System.out.println(listeners.size() + " vs " + ((ListenersExposer) world.getWorldBorder()).invokeGetListeners().size());
      */
      
      // (AlternativeWorldBorderWithListenersExposed) world.getWorldBorder()
    }
  }
  @ Shadow public abstract Iterable<ServerWorld> getWorlds();
}
