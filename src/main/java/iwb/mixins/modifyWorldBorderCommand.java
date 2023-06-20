package iwb.mixins;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.command.WorldBorderCommand;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.command.ServerCommandSource;

@ Mixin (WorldBorderCommand.class) class modifyWorldBorderCommand {
  @ Redirect (
    method = {"executeBuffer", "executeDamage", "executeWarningTime", "executeWarningDistance", "executeGet", "executeCenter", "executeSet"},
    at = @ At (
      value = "INVOKE",
      target = "Lnet/minecraft/server/world/ServerWorld;getWorldBorder()Lnet/minecraft/world/border/WorldBorder;"
    )
  ) private static WorldBorder moddedgetWorldBorder(ServerWorld overworld, ServerCommandSource source) {
    return source.getWorld().getWorldBorder();
  }
}
