package iwb.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import iwb.WorldBorderWithSyncersRemover;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.server.world.ServerWorld;

@ Mixin (MinecraftServer.class) abstract class removeinitialworldbordersyncers {
  @ Inject (method = "createWorlds", at = @At("RETURN"), require = 1) void onCreateWorlds(CallbackInfo ci) {
    System.out.println("SDA");
    for (var world: getWorlds()) {
      ((WorldBorderWithSyncersRemover) world.getWorldBorder()).removeSyncers();
    }
  }
  @ Shadow public abstract Iterable<ServerWorld> getWorlds();
}
