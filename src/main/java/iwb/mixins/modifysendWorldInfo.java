package iwb.mixins;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.network.ServerPlayerEntity;

@ Mixin (PlayerManager.class) class modifysendWorldInfo {
  @ Redirect (
    method = "sendWorldInfo",
    at = @ At (
      value = "INVOKE",
      target = "Lnet/minecraft/server/world/ServerWorld;getWorldBorder()Lnet/minecraft/world/border/WorldBorder;"
    )
  ) WorldBorder moddedgetWorldBorder(ServerWorld overworld, ServerPlayerEntity player, ServerWorld world) {
    return world.getWorldBorder();
  }
}
