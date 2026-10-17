
package transportations;

public class Jeepney extends Vehicle {

    private String routeCode;
    private boolean modern;

    // Original constructor: traditional jeepney by default
    public Jeepney(String vehicleId, int capacity, String routeCode) {
        this(vehicleId, capacity, routeCode, false);
    }

    // Constructor supporting traditional and modern jeepneys
    public Jeepney(String vehicleId, int capacity,
                   String routeCode, boolean modern) {
        super(vehicleId, capacity);
        this.routeCode = routeCode;
        this.modern = modern;
    }

    public boolean isModern() {
        return modern;
    }

    @Override
    public double getFare() {
        return modern ? 17.00 : 14.00;
    }

    @Override
    public String getVehicleType() {
        String type = modern
                ? "Modern Jeepney"
                : "Traditional Jeepney";

        if (routeCode == null || routeCode.trim().isEmpty()) {
            return type;
        }

        return type + " - " + routeCode;
    }
}
