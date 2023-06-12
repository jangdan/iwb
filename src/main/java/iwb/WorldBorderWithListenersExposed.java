package iwb;

import java.util.List;
import net.minecraft.world.border.WorldBorderListener;

public interface WorldBorderWithListenersExposed {
  List<WorldBorderListener> stealListeners();
}
