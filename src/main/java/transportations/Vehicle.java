
package transportations;

public abstract class Vehicle {
    private String vehicleId;
    private int capacity;

    public Vehicle(String vehicleId, int capacity) {
        this.vehicleId = vehicleId;
        this.capacity = capacity;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public int getCapacity() {
        return capacity;
    }

    public abstract double getFare();

    public abstract String getVehicleType();

    public void boardingProcedure() {
        System.out.println("Board at the assigned stop.");
    }
}
