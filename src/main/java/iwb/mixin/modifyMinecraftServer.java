package iwb.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iwb.ModdedWorldBorder;

import java.util.function.Function;
import net.minecraft.nbt.NbtCompound;
import iwb.WorldBorderPersistentState;
import net.minecraft.world.border.WorldBorder;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.NbtOps;
import java.util.function.Supplier;

import net.minecraft.world.border.WorldBorderListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.WorldBorderSizeChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderInterpolateSizeS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderCenterChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderWarningTimeChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderWarningBlocksChangedS2CPacket;

import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.PlayerManager;

@ Mixin (MinecraftServer.class) abstract class modifyMinecraftServer {
  @ Inject (method = "createWorlds", at = @ At ("RETURN"), require = 1) void onCreateWorlds(CallbackInfo ci) {
    System.out.println("SDA");
    for (var world: getWorlds()) {
      ((ModdedWorldBorder) world.getWorldBorder()).clearListeners();
      
      // After decoupling this `world`'s world border from the Overworld world border, load the saved state (if it exists)
      var worldborderpersistentstate = world.getChunkManager().getPersistentStateManager().getOrCreate(
        // Deserialize: `NbtCompound` -> `WorldBorderPersistentState`
        new Function<NbtCompound, WorldBorderPersistentState> () { @ Override public WorldBorderPersistentState apply(NbtCompound c) {
          var x = WorldBorder.Properties.fromDynamic(new Dynamic<> (NbtOps.INSTANCE, c), world.getWorldBorder().write());
          world.getWorldBorder().load(x);
          return new WorldBorderPersistentState (world.getWorldBorder());
        }},
        // ... or create a new one
        new Supplier<WorldBorderPersistentState> () { @ Override public WorldBorderPersistentState get() {
          return new WorldBorderPersistentState (world.getWorldBorder());
        }},
        "world border"
      );
      
      var playerManager = getPlayerManager();
      var dimension = world.getRegistryKey();
      world.getWorldBorder().addListener(new WorldBorderListener () {
        // Based on `PlayerManager :: setMainWorld()`
        void sendToAllPlayersOnThisDimension(Packet<?> packet) {
          playerManager.sendToDimension(packet, dimension);
        }
        @Override public void onSizeChange(WorldBorder border, double size) {
          sendToAllPlayersOnThisDimension(new WorldBorderSizeChangedS2CPacket(border));
          worldborderpersistentstate.markDirty();
        }
        @Override public void onInterpolateSize(WorldBorder border, double fromSize, double toSize, long time) {
          sendToAllPlayersOnThisDimension(new WorldBorderInterpolateSizeS2CPacket(border));
          worldborderpersistentstate.markDirty();
        }
        @Override public void onCenterChanged(WorldBorder border, double centerX, double centerZ) {
          sendToAllPlayersOnThisDimension(new WorldBorderCenterChangedS2CPacket(border));
          worldborderpersistentstate.markDirty();
        }
        @Override public void onWarningTimeChanged(WorldBorder border, int warningTime) {
          sendToAllPlayersOnThisDimension(new WorldBorderWarningTimeChangedS2CPacket(border));
          worldborderpersistentstate.markDirty();
        }
        @Override public void onWarningBlocksChanged(WorldBorder border, int warningBlockDistance) {
          sendToAllPlayersOnThisDimension(new WorldBorderWarningBlocksChangedS2CPacket(border));
          worldborderpersistentstate.markDirty();
        }
        @Override public void onDamagePerBlockChanged(WorldBorder border, double damagePerBlock) {
          // ?
          worldborderpersistentstate.markDirty();
        }
        @Override public void onSafeZoneChanged(WorldBorder border, double safeZoneRadius) {
          // 없나봄
          worldborderpersistentstate.markDirty();
        }
      });
    }
  }
  @ Shadow public abstract Iterable<ServerWorld> getWorlds();
  @ Shadow public abstract PlayerManager getPlayerManager();
}
