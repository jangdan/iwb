package iwb.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import iwb.ModdedWorldBorder;
import net.minecraft.world.border.WorldBorderListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.network.packet.s2c.play.WorldBorderSizeChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderInterpolateSizeS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderCenterChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderWarningTimeChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderWarningBlocksChangedS2CPacket;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.PlayerManager;

@ Mixin (MinecraftServer.class) abstract class replacevanillaWorldBorderListeners {
  @ Inject (method = "createWorlds", at = @At("RETURN"), require = 1) void onCreateWorlds(CallbackInfo ci) {
    System.out.println("SDA");
    for (var world: getWorlds()) {
      ((ModdedWorldBorder) world.getWorldBorder()).clearListeners();
      var playerManager = getPlayerManager();
      var dimension = world.getRegistryKey();
      world.getWorldBorder().addListener(new WorldBorderListener () {
        // Based on `PlayerManager :: setMainWorld()`
        void sendToAllPlayersOnThisDimension(Packet<?> packet) {
          playerManager.sendToDimension(packet, dimension);
        }
        @Override public void onSizeChange(WorldBorder border, double size) {
          sendToAllPlayersOnThisDimension(new WorldBorderSizeChangedS2CPacket(border));
        }
        @Override public void onInterpolateSize(WorldBorder border, double fromSize, double toSize, long time) {
          sendToAllPlayersOnThisDimension(new WorldBorderInterpolateSizeS2CPacket(border));
        }
        @Override public void onCenterChanged(WorldBorder border, double centerX, double centerZ) {
          sendToAllPlayersOnThisDimension(new WorldBorderCenterChangedS2CPacket(border));
        }
        @Override public void onWarningTimeChanged(WorldBorder border, int warningTime) {
          sendToAllPlayersOnThisDimension(new WorldBorderWarningTimeChangedS2CPacket(border));
        }
        @Override public void onWarningBlocksChanged(WorldBorder border, int warningBlockDistance) {
          sendToAllPlayersOnThisDimension(new WorldBorderWarningBlocksChangedS2CPacket(border));
        }
        @Override public void onDamagePerBlockChanged(WorldBorder border, double damagePerBlock) {}
        @Override public void onSafeZoneChanged(WorldBorder border, double safeZoneRadius) {}
      });
    }
  }
  @ Shadow public abstract Iterable<ServerWorld> getWorlds();
  @ Shadow public abstract PlayerManager getPlayerManager();
}
