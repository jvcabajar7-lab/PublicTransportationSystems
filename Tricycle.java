
package transportations;

public class Tricycle extends Vehicle {
    private String zoneCode;

    public Tricycle(String vehicleId, int capacity, String zoneCode) {
        super(vehicleId, capacity);
        this.zoneCode = zoneCode;
    }

    @Override
    public double getFare() {
        return 25.00;
    }

    @Override
    public String getVehicleType() {
        return "Tricycle - Zone " + zoneCode;
    }
}
