package iwb;

import net.minecraft.world.PersistentState;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.nbt.NbtCompound;

public class WorldBorderPersistentState extends PersistentState {
  WorldBorder worldborder;
  public WorldBorderPersistentState (WorldBorder worldborder) {
    this.worldborder = worldborder;
  }
  @ Override public NbtCompound writeNbt(NbtCompound c) {
    worldborder.write().writeNbt(c);
    return c;
  }
}
