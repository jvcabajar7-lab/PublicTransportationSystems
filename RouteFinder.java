
package transportations;

import java.util.ArrayList;
import java.util.List;

public class RouteFinder {

    private final ArrayList<Route> routes = new ArrayList<>();

    // Add a route to the system
    public void addRoute(Route route) {
        routes.add(route);
    }

    // Search for a direct route
    public List<Route> searchRoutes(
            String origin, String destination) {

        ArrayList<Route> results = new ArrayList<>();

        String from = origin.trim();
        String to = destination.trim();

        for (Route route : routes) {
            if (route.getOrigin().equalsIgnoreCase(from)
                    && route.getDestination()
                    .equalsIgnoreCase(to)) {
                results.add(route);
            }
        }

        return results;
    }

    // Search direct routes using partial place names
    public List<Route> searchRoutesByPlace(
            String origin, String destination) {

        ArrayList<Route> results = new ArrayList<>();

        String from = origin.trim().toLowerCase();
        String to = destination.trim().toLowerCase();

        for (Route route : routes) {
            boolean originMatches =
                    route.getOrigin().toLowerCase().contains(from);

            boolean destinationMatches =
                    route.getDestination().toLowerCase().contains(to);

            if (originMatches && destinationMatches) {
                results.add(route);
            }
        }

        return results;
    }

    // Return all routes
    public List<Route> getAllRoutes() {
        return new ArrayList<>(routes);
    }

    // Find possible journeys with one transfer
    public List<String> searchWithOneTransfer(
            String origin, String destination) {

        ArrayList<String> results = new ArrayList<>();

        String from = origin.trim();
        String to = destination.trim();

        for (Route first : routes) {

            if (!first.getOrigin().equalsIgnoreCase(from)) {
                continue;
            }

            String transferPoint = first.getDestination();

            if (transferPoint.equalsIgnoreCase(to)) {
                continue;
            }

            for (Route second : routes) {

                boolean sameTransfer = second.getOrigin()
                        .equalsIgnoreCase(transferPoint);

                boolean reachesDestination = second.getDestination()
                        .equalsIgnoreCase(to);

                if (sameTransfer && reachesDestination) {
                    results.add(
                            first.getOrigin() + " -> "
                                    + first.getDestination() + " -> "
                                    + second.getDestination()
                                    + "\nFirst route: " + first.getRouteName()
                                    + "\nSecond route: " + second.getRouteName()
                    );
                }
            }
        }

        return results;
    }
}
