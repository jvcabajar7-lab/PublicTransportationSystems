
package transportations;

public class Route {

    private String routeName;
    private String origin;
    private String destination;
    private String boardingPoint;
    private String alightingPoint;
    private Vehicle vehicle;



    public Route(String routeName, String origin, String destination,
                 String boardingPoint, String alightingPoint,
                 Vehicle vehicle) {

        this.routeName = routeName;
        this.origin = origin;
        this.destination = destination;
        this.boardingPoint = boardingPoint;
        this.alightingPoint = alightingPoint;
        this.vehicle = vehicle;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getBoardingPoint() {
        return boardingPoint;
    }

    public String getAlightingPoint() {
        return alightingPoint;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return routeName + " | "
                + vehicle.getVehicleType()
                + " | PHP "
                + String.format("%.2f", vehicle.getFare());
    }
}
