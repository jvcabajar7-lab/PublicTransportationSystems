
package transportations;

public class FareCalculator {

    // Return the minimum or configured base fare
    public double calculateFare(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Please select a vehicle."
            );
        }

        return vehicle.getFare();
    }

    // Calculate an estimated fare using distance
    public double calculateFare(Vehicle vehicle, double distanceKm) {

        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Please select a vehicle."
            );
        }

        if (!Double.isFinite(distanceKm) || distanceKm <= 0) {
            throw new IllegalArgumentException(
                    "Distance must be greater than zero."
            );
        }

        double baseFare;
        double includedKm;
        double succeedingRate;

        if (vehicle instanceof Jeepney) {

            Jeepney jeepney = (Jeepney) vehicle;

            if (jeepney.isModern()) {
                baseFare = 17.00;
                succeedingRate = 2.40;
            } else {
                baseFare = 14.00;
                succeedingRate = 2.00;
            }

            includedKm = 4.0;

        } else if (vehicle instanceof Bus) {

            Bus bus = (Bus) vehicle;

            if (bus.hasAircon()) {
                baseFare = 18.00;
                succeedingRate = 2.98;
            } else {
                baseFare = 15.00;
                succeedingRate = 2.49;
            }

            includedKm = 5.0;

        } else {

            // Tricycle retains its configured fare.
            // Actual local zone fares still need verification.
            return vehicle.getFare();
        }

        double extraKm = Math.max(0.0, distanceKm - includedKm);

        // Simplified estimate: count each started extra kilometre
        int extraKilometers = (int) Math.ceil(extraKm);

        return baseFare + (extraKilometers * succeedingRate);
    }

    // Apply a percentage discount
    public double applyDiscount(double fare, double discountRate) {

        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException(
                    "Discount must be between 0 and 1."
            );
        }

        return fare - (fare * discountRate);
    }
}
