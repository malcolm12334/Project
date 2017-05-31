package drone;

import drone.Cube;
import drone.DroneBody;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import java.util.List;
import javax.vecmath.Vector3f;

/**
 * @author Alexandre
 */
@FunctionalInterface
@SarlSpecification("0.5")
@SuppressWarnings("all")
public interface Moving extends Capacity {
  public abstract Vector3f seekingFixedTarget(final List<Cube> listOfObstacle, final DroneBody body);
  
  public static class ContextAwareCapacityWrapper<C extends Moving> extends Capacity.ContextAwareCapacityWrapper<C> implements Moving {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public Vector3f seekingFixedTarget(final List<Cube> listOfObstacle, final DroneBody body) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.seekingFixedTarget(listOfObstacle, body);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}