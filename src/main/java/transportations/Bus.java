
package transportations;

public class Bus extends Vehicle {

    private boolean hasAircon;

    public Bus(String vehicleId, int capacity, boolean hasAircon) {
        super(vehicleId, capacity);
        this.hasAircon = hasAircon;
    }

    public boolean hasAircon() {
        return hasAircon;
    }

    @Override
    public double getFare() {
        return hasAircon ? 18.00 : 15.00;
    }

    @Override
    public String getVehicleType() {
        return hasAircon ? "Aircon Bus" : "Regular Bus";
    }
}
