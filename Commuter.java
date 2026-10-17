
package transportations;

import java.util.ArrayList;
import java.util.List;

public class Commuter {
    private String commuterId;
    private String fullName;
    private final ArrayList<Route> savedRoutes = new ArrayList<>();

    public Commuter(String commuterId, String fullName) {
        this.commuterId = commuterId;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void saveRoute(Route route) {
        if (!savedRoutes.contains(route)) {
            savedRoutes.add(route);
        }
    }

    public List<Route> getSavedRoutes() {
        return new ArrayList<>(savedRoutes);
    }
}
