
package transportations;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;

import java.util.List;

public class    Main extends Application {

    private final RouteFinder finder = new RouteFinder();
    private final FareCalculator calculator = new FareCalculator();
    private final Commuter commuter =
            new Commuter("COM-01", "Sample Commuter");

    private ListView<Route> searchResults;
    private ListView<Route> savedResults;
    private Label routeDetails;

    private TextField originField;
    private TextField destinationField;

    private ComboBox<Vehicle> vehicleBox;
    private Label fareLabel;
    private TextField distanceField;
    private CheckBox discountBox;

    @Override
    public void start(Stage stage) {
        addSampleRoutes();

        Label title = new Label("PUBLIC TRANSPORTATION ROUTE SYSTEM");
        title.setStyle(
                "-fx-font-size: 21px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );

        HBox header = new HBox(title);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #163B65;");

        TabPane tabs = new TabPane();
        tabs.getTabs().add(createSearchTab());
        tabs.getTabs().add(createFareTab());
        tabs.getTabs().add(createSavedTab());

        VBox root = new VBox(header, tabs);
        root.setStyle("-fx-background-color: #F2F6FA;");
        VBox.setVgrow(tabs, Priority.ALWAYS);

        Scene scene = new Scene(root, 850, 600);

        stage.setTitle("Public Transportation Route System");
        stage.setScene(scene);
        stage.setMinWidth(650);
        stage.setMinHeight(500);
        stage.show();
    }


    private void addSampleRoutes() {

        addJeepneyRoute(
                "01K", "Urgello", "Parkmall",
                "P. Urgello", "Parkmall");

        addJeepneyRoute(
                "03A", "Mabolo", "Carbon Market",
                "Mabolo", "Carbon Market");

        addJeepneyRoute(
                "03B", "Mabolo", "Colon",
                "Mabolo", "Colon");

        addJeepneyRoute(
                "03L", "Mabolo", "Carbon Market",
                "Mabolo", "Carbon Market");

        addJeepneyRoute(
                "03Q", "Ayala Center Cebu", "SM City Cebu",
                "Ayala Center Cebu", "SM City Cebu");

        addJeepneyRoute(
                "04B", "Lahug", "Carbon Market",
                "Lahug", "Carbon Market");

        addJeepneyRoute(
                "04L", "Lahug", "SM City Cebu",
                "Lahug", "SM City Cebu");

        addJeepneyRoute(
                "04M", "Ayala Center Cebu", "Lahug",
                "Ayala Center Cebu", "Lahug");

        addJeepneyRoute(
                "06B", "Guadalupe", "Colon",
                "Guadalupe", "Colon");

        addJeepneyRoute(
                "06C", "Guadalupe", "Colon",
                "Guadalupe", "Colon");

        addJeepneyRoute(
                "06H", "Guadalupe", "SM City Cebu",
                "Guadalupe", "SM City Cebu");
        addJeepneyRoute(
                "06G",
                "Cebu Eastern College, Sanciangko",
                "Taboan",
                "Cebu Eastern College, Sanciangko Street",
                "Taboan Public Market"
        );
    }

    // Helper method to create a jeepney route
    private void addJeepneyRoute(
            String code,
            String origin,
            String destination,
            String boardingPoint,
            String alightingPoint) {

        Vehicle vehicle = new Jeepney(
                "JEEP-" + code,
                20,
                code
        );

        Route route = new Route(
                code + " - " + origin + " to " + destination,
                origin,
                destination,
                boardingPoint,
                alightingPoint,
                vehicle
        );

        finder.addRoute(route);
    }


    private Tab createSearchTab() {
        originField = new TextField();
        originField.setPromptText("Example: Cebu City");

        destinationField = new TextField();
        destinationField.setPromptText("Example: SM City Cebu");

        Button searchButton = new Button("Search Route");
        Button saveButton = new Button("Save Selected Route");

        searchButton.setOnAction(event -> searchRoutes());
        saveButton.setOnAction(event -> saveSelectedRoute());

        searchResults = new ListView<>();
        searchResults.setPlaceholder(
                new Label("Search for a route to see results.")
        );

        routeDetails = new Label("Select a route to view its details.");
        routeDetails.setWrapText(true);
        routeDetails.setPadding(new Insets(12));
        routeDetails.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #D4DFEA;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;"
        );

        searchResults.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldRoute, newRoute) -> {
                    if (newRoute != null) {
                        showRouteDetails(newRoute);
                    }
                });

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                new Label("Enter Origin:"),
                originField,
                new Label("Enter Destination:"),
                destinationField,
                searchButton,
                new Label("Matching Routes:"),
                searchResults,
                routeDetails,
                saveButton
        );

        VBox.setVgrow(searchResults, Priority.ALWAYS);

        return new Tab("Search Route", layout);
    }

    private void searchRoutes() {
        String origin = originField.getText().trim();
        String destination = destinationField.getText().trim();

        if (origin.isEmpty() || destination.isEmpty()) {
            showMessage(
                    Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please enter both origin and destination."
            );
            return;
        }

        try {
            List<Route> results =
                    finder.searchRoutesByPlace(origin, destination);

            searchResults.setItems(
                    FXCollections.observableArrayList(results)
            );

            if (results.isEmpty()) {
                routeDetails.setText("No matching route was found.");
            } else {
                searchResults.getSelectionModel().selectFirst();
            }

        } catch (Exception e) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Search Error",
                    "Unable to search routes. Please try again."
            );
        }
    }

    private void showRouteDetails(Route route) {
        routeDetails.setText(
                "Route: " + route.getRouteName() + "\n" +
                        "Origin: " + route.getOrigin() + "\n" +
                        "Destination: " + route.getDestination() + "\n" +
                        "Vehicle: " + route.getVehicle().getVehicleType() + "\n" +
                        "Boarding Point: " + route.getBoardingPoint() + "\n" +
                        "Alighting Point: " + route.getAlightingPoint() + "\n" +
                        String.format(
                                "Minimum/Base Fare: PHP %.2f\n" +
                                        "Final fare depends on distance.",
                                route.getVehicle().getFare()
                        )
        );
    }

    private void saveSelectedRoute() {
        Route selectedRoute =
                searchResults.getSelectionModel().getSelectedItem();

        if (selectedRoute == null) {
            showMessage(
                    Alert.AlertType.WARNING,
                    "No Route Selected",
                    "Please search for and select a route first."
            );
            return;
        }

        boolean alreadySaved =
                commuter.getSavedRoutes().contains(selectedRoute);

        if (alreadySaved) {
            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Already Saved",
                    "This route is already in your saved routes."
            );
            return;
        }

        commuter.saveRoute(selectedRoute);
        refreshSavedRoutes();

        showMessage(
                Alert.AlertType.INFORMATION,
                "Route Saved",
                "Your route has been saved successfully."
        );
    }


    private Tab createFareTab() {

        Vehicle bus = new Bus("BUS-01", 50, true);
        Vehicle regularBus = new Bus("BUS-02", 50, false);

        Vehicle traditionalJeepney =
                new Jeepney("JEEP-TRAD", 20, "", false);

        Vehicle modernJeepney =
                new Jeepney("JEEP-MOD", 20, "", true);

        Vehicle tricycle =
                new Tricycle("TRI-01", 4, "A");

        vehicleBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        traditionalJeepney,
                        modernJeepney,
                        regularBus,
                        bus,
                        tricycle
                )
        );

        vehicleBox.setCellFactory(list ->
                new javafx.scene.control.ListCell<Vehicle>() {
                    @Override
                    protected void updateItem(Vehicle vehicle, boolean empty) {
                        super.updateItem(vehicle, empty);

                        setText(empty || vehicle == null
                                ? null : vehicle.getVehicleType());
                    }
                }
        );

        vehicleBox.setButtonCell(
                new javafx.scene.control.ListCell<Vehicle>() {
                    @Override
                    protected void updateItem(Vehicle vehicle, boolean empty) {
                        super.updateItem(vehicle, empty);

                        setText(empty || vehicle == null
                                ? null : vehicle.getVehicleType());
                    }
                }
        );

        vehicleBox.getSelectionModel().selectFirst();

        distanceField = new TextField();
        distanceField.setPromptText("Example: 6.5 kilometers");

        discountBox = new CheckBox(
                "Apply 20% discount for eligible passengers"
        );

        fareLabel = new Label(
                "Select a vehicle, enter distance, and click Check Fare."
        );

        fareLabel.setWrapText(true);
        fareLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #163B65;"
        );

        Button fareButton = new Button("Check Fare");
        fareButton.setOnAction(event -> calculateSelectedFare());

        VBox layout = new VBox(
                12,
                new Label("Choose a Vehicle:"),
                vehicleBox,
                new Label("Travel Distance (km):"),
                distanceField,
                discountBox,
                fareButton,
                fareLabel
        );

        layout.setPadding(new Insets(30));

        return new Tab("Check Fare", layout);
    }



    private void calculateSelectedFare() {

        try {
            Vehicle selectedVehicle =
                    vehicleBox.getSelectionModel().getSelectedItem();

            if (selectedVehicle == null) {
                throw new IllegalArgumentException(
                        "Please select a vehicle."
                );
            }

            String distanceText = distanceField.getText().trim();

            if (distanceText.isEmpty()) {
                throw new IllegalArgumentException(
                        "Please enter the travel distance."
                );
            }

            double distance;

            try {
                distance = Double.parseDouble(distanceText);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Enter a valid distance, such as 6.5."
                );
            }

            double fare = calculator.calculateFare(
                    selectedVehicle, distance
            );

            boolean discounted = discountBox.isSelected();

            if (discounted) {
                fare = calculator.applyDiscount(fare, 0.20);
            }

            String note;

            if (selectedVehicle instanceof Tricycle) {
                note = "Uses the configured zone fare. " +
                        "Confirm the actual local tariff.";
            } else {
                note = "Estimated fare. Partial succeeding " +
                        "kilometres are rounded up.";
            }

            fareLabel.setText(
                    String.format(
                            "%s\nDistance: %.2f km\n" +
                                    "Estimated Fare: PHP %.2f\n%s%s",
                            selectedVehicle.getVehicleType(),
                            distance,
                            fare,
                            discounted
                                    ? "20% discount applied.\n"
                                    : "",
                            note
                    )
            );

        } catch (IllegalArgumentException e) {
            showMessage(
                    Alert.AlertType.WARNING,
                    "Fare Checker",
                    e.getMessage()
            );
        } catch (Exception e) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Fare Error",
                    "Unable to calculate the fare."
            );
        }
    }


    private Tab createSavedTab() {
        savedResults = new ListView<>();
        savedResults.setPlaceholder(
                new Label("You have no saved routes yet.")
        );

        Button refreshButton = new Button("Refresh Saved Routes");
        refreshButton.setOnAction(event -> refreshSavedRoutes());

        VBox layout = new VBox(
                12,
                new Label("Your Favorite Routes"),
                savedResults,
                refreshButton
        );

        layout.setPadding(new Insets(20));
        VBox.setVgrow(savedResults, Priority.ALWAYS);

        refreshSavedRoutes();

        return new Tab("Saved Routes", layout);
    }

    private void refreshSavedRoutes() {
        if (savedResults != null) {
            savedResults.setItems(
                    FXCollections.observableArrayList(
                            commuter.getSavedRoutes()
                    )
            );
        }
    }

    private void showMessage(
            Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
