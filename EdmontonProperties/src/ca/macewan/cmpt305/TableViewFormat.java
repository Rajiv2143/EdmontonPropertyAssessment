package ca.macewan.cmpt305;// Adapted from:
// https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html
// Demonstrates how to format the values in a table view

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javafx.scene.control.Label;


/**
 * TableViewFormat - JavaFX application used to display property assessments based off queries made through
 * its interface.
 * Variables:
 * private TableView<PropertyAssessment> table - used to instantiate the TableView object to display a table
 * of property assessments.
 * private ObservableList<PropertyAssessment> displayedAssessments;
 * Instantiates concrete class function definitions.
 */
public class TableViewFormat extends Application {

    private ObservableList<PropertyAssessment> tableData;
    private PropertyAssessmentInterface pai;

    boolean bgColor = true; //true?white:black

    public static void main(String[] args) {
        launch(args);
    }

    // Instantiate stage and columns to display property assessment characteristics.
    @Override
    public void start(Stage primaryStage) {

        TableView<PropertyAssessment> tableView = new TableView<>();
        tableView.setEditable(true);
        TableColumn<PropertyAssessment, String> accountCol = new TableColumn<>("Account");
        TableColumn<PropertyAssessment, String> addrCol = new TableColumn<>("Address");
        TableColumn<PropertyAssessment, String> valueCol = new TableColumn<>("Assessed Value");
        TableColumn<PropertyAssessment, String> classCol = new TableColumn<>("Assessment Class");
        TableColumn<PropertyAssessment, String> hoodCol = new TableColumn<>("Neighbourhood");
        TableColumn<PropertyAssessment, String> coordCol = new TableColumn<>("(Latitude, Longitude)");

        accountCol.setCellValueFactory(cellData -> {
            String account = String.valueOf(cellData.getValue().getAccountNumber());
            return new SimpleStringProperty(account);
        });

        addrCol.setCellValueFactory(cellData -> {
            String addr = String.valueOf(cellData.getValue().getAddress());
            return new SimpleStringProperty(addr);
        });

        valueCol.setCellValueFactory(cellData -> {
            //NumberFormat nf = NumberFormat.getInstance(Locale.CANADA);
            String val = String.valueOf(cellData.getValue().getAssessedValue());
            return new SimpleStringProperty(val);
        });

        classCol.setCellValueFactory(cellData -> {
            String cls = cellData.getValue().getAssessmentClass().toString();
            return new SimpleStringProperty(cls);
        });

        hoodCol.setCellValueFactory(cellData -> {
            String hood = cellData.getValue().getNeighbourhood().getNeighbourhood();
            return new SimpleStringProperty(hood);
        });

        coordCol.setCellValueFactory(cellData -> {
            String loca = cellData.getValue().getLocation().toString();
            return new SimpleStringProperty(loca);
        });

        // Add all columns to the table view
        tableView.getColumns().addAll(accountCol, addrCol, valueCol, classCol, hoodCol, coordCol);

        // Instantiate extra side window for interactive elements.
        BorderPane leftMenu = new BorderPane();
        VBox leftSide = new VBox();
        leftSide.setPrefWidth(300);

        // Instantiate file selection button (csv or api) and set its options.
        ObservableList<String> buttonOptions = FXCollections.observableArrayList("", "CSV file", "Edmonton's Open Data Portal");
        ComboBox<String> fileSelect = new ComboBox<>(buttonOptions);
        fileSelect.setOnAction(event -> {
            String selectedOption = fileSelect.getValue();
        });

        // Search within csv file for unique assessment class elements to display for searching.
        ObservableList<String> classOptions = FXCollections.observableArrayList("");
        CSVPropertyAssessment j = new CSVPropertyAssessment("q7d6-ambg.csv");
        // Create dropdown menu of Assessment classes found within the csv file.
        List<PropertyAssessment> assessments = j.getData();
        for (PropertyAssessment assessment : assessments) {
            if (assessment.getAssessmentClass().size() == 0)
                break;

            List<AssessmentClass> checkClass = assessment.getAssessmentClass();
            AssessmentClass newClass = checkClass.get(0);
            String classStr = newClass.getAssessmentClass();

            if (!classOptions.contains(classStr)) {
                classOptions.add(classStr);
            }
        }
        ComboBox<String> classSelect = new ComboBox<>(classOptions);
        classSelect.setOnAction(event -> {
            String text = classSelect.getValue();
        });

        // Create various interactive fields pertaining to property assessment characteristics for searching.
        TextField accField = new TextField();
        accField.setOnAction(e -> {
            String text = accField.getText();
        });

        TextField addrField = new TextField();
        accField.setOnAction(e -> {
            String text = null;
            if (addrField.getText() != "")
                text = addrField.getText();
        });

        TextField hoodField = new TextField();
        accField.setOnAction(e -> {
            String text = hoodField.getText();
        });

        TextField classField = new TextField();
        accField.setOnAction(e -> {
            String text = classField.getText();
        });

        TextField minField = new TextField();
        minField.setPromptText("Min Value");
        minField.setOnAction(e -> {
            String text = minField.getText();
        });

        TextField maxField = new TextField();
        maxField.setPromptText("Max Value");
        maxField.setOnAction(e -> {
            String text = maxField.getText();
        });

        // Instantiate button to reset editable fields
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            fileSelect.setValue("");
            accField.setText("");
            addrField.setText("");
            hoodField.setText("");
            classField.setText("");
            minField.setText("");
            maxField.setText("");
            if (tableView.getItems() != null)
                tableView.getItems().clear();
        });

        // Create new popup for the case of no found property assessments within search criteria.
        Label popupLabel = new Label("Oops, did not find anything!");

        VBox popBox = new VBox(popupLabel);
        popBox.setAlignment(Pos.CENTER);

        Popup searchError = new Popup();
        searchError.getContent().addAll(popBox);

        searchError.setWidth(200);
        searchError.setHeight(200);

        Button hoodStatsButton = new Button("View Neighbourhood Graphs");
        hoodStatsButton.setOnAction(e -> {
            NeighbourhoodsGraph graph = new NeighbourhoodsGraph();
            Stage graphStage = new Stage();
            graph.start(graphStage);
        });

        Button classStatsButton = new Button("View Assessment Class Graphs");
        classStatsButton.setOnAction(e -> {
            AssessmentGraph graph = new AssessmentGraph();
            Stage graphStage = new Stage();
            graph.start(graphStage);
        });

        Button trafficButton = new Button("View Traffic data");
        trafficButton.setOnAction(e -> {
            TrafficUI traffic = new TrafficUI();
            Stage trafficStage = new Stage();
            traffic.start(trafficStage);
        });

        // Instantiate search button to lock in search criteria and begin querying to the selected destination.
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            searchError.hide();
            String option = fileSelect.getValue();
            if (tableView.getItems() != null)
                tableView.getItems().clear();

            if (Objects.equals(option, "CSV file")) {
                pai = new CSVPropertyAssessment("q7d6-ambg.csv");
                ObservableList<PropertyAssessment> assessList = FXCollections.observableArrayList();
                for (PropertyAssessment assessment : pai.getData()) {
                    if (assessList.contains(assessment))
                        continue;
                    String query = null;
                    // Check fields to find a match with csv file
                    if (classSelect.getValue() != null) {
                        query = accField.getText() + addrField.getText() + hoodField.getText() + classSelect.getValue() + minField.getText();
                    }
                    else
                        query = accField.getText() + addrField.getText() + hoodField.getText() + minField.getText();

                    if (query.contains(String.valueOf(assessment.getAccountNumber())) && !Objects.equals(accField.getText(), "")) {
                        query = query.replaceAll(accField.getText(), "");
                        assessList.add(assessment);
                        continue;
                    }
                    if (query.contains(assessment.getAddress().toString()) && !Objects.equals(addrField.getText(), "")) {
                        query = query.replaceAll(addrField.getText(), "");
                    }
                    if (Objects.equals(assessment.getNeighbourhood().getNeighbourhood(), hoodField.getText().toUpperCase()) && !Objects.equals(hoodField.getText(), "")) {
                        query = query.replaceAll(hoodField.getText(), "");
                    }
                    if (classSelect.getValue() != null) {
                        if (Objects.equals(classSelect.getValue(), assessment.getAssessmentClass().get(0).getAssessmentClass())) {
                            query = query.replaceAll(classSelect.getValue(), "");
                        }
                    }
                    if (!Objects.equals(minField.getText(), "") && !Objects.equals(maxField.getText(), "")) {
                        if ((assessment.getAssessedValue() >= Integer.parseInt(minField.getText())) && (assessment.getAssessedValue() <= Integer.parseInt(maxField.getText()))) {
                            query = query.replaceAll(minField.getText(), "");
                        }
                    }
                    if (query.equals(""))
                        assessList.add(assessment);
                }
                // throw popup if no assessments were found
                if (assessList.isEmpty())
                    searchError.show(primaryStage);

                // display found assessments from criteria
                tableView.setItems(assessList);
            }

            if (Objects.equals(option, "Edmonton's Open Data Portal")) {

                String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv";
                String accOut = "", hoodOut = "", classOut = "", minOut = "", maxOut = "";
                int previous = 0;

                // create query url based on user inputted fields
                if (!Objects.equals(accField.getText(), "")) {
                    accOut = "account_number=" + accField.getText();
                    previous = 1;
                }

                if (!Objects.equals(hoodField.getText(), "")) {
                    if (previous == 1)
                        hoodOut = "AND neighbourhood= " + hoodField.getText().toUpperCase();
                    else {
                        hoodOut = "neighbourhood=" + hoodField.getText().toUpperCase();
                        previous = 1;
                    }
                }

                if (!Objects.equals(minField.getText(), "")) {
                    if (previous == 1)
                        minOut = "AND assessed_value > " + Integer.parseInt(minField.getText());
                    else {
                        minOut = "assessed_value>" + Integer.parseInt(minField.getText());
                        previous = 1;
                    }
                }

                if (!Objects.equals(maxField.getText(), "")) {
                    if (previous == 1)
                        maxOut = "AND assessed_value > " + Integer.parseInt(maxField.getText().toUpperCase());
                    else {
                        maxOut = "assessed_value>" + Integer.parseInt(maxField.getText().toUpperCase());
                    }
                }

                String url = "";
                if (previous == 1) {
                    String queryEnd = accOut + hoodOut + classOut + minOut + maxOut;
                    url = endpoint + "?" + queryEnd;
                }

                else {
                    url = endpoint;
                }

                APIPropertyAssessment p;
                try {
                    System.out.println(url);
                    p = new APIPropertyAssessment(url);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
                tableData = FXCollections.observableArrayList(p.getData(1000));
                tableView.setItems(tableData);
            }
        });

        // create button for base file read from selected destination.
        Button readButton = new Button("Read Data");
        readButton.setOnAction(e -> {


            String option = fileSelect.getValue();
            if (Objects.equals(option, "CSV file")) {
                // use Csv DAO to add all property assessments to an outputtable array.
                pai = new CSVPropertyAssessment("q7d6-ambg.csv");
            }
            // Query data.edmonton.ca for all property assessments, then check to ensure those elements
            // exist in the csv file with the csv handler, and add those elements to a list for output.
            if (Objects.equals(option, "Edmonton's Open Data Portal")) {
                try {
                    pai = new APIPropertyAssessment("https://data.edmonton.ca/resource/q7d6-ambg.csv");
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
            tableData = FXCollections.observableArrayList(pai.getData(1000));
            tableView.setItems(tableData);
        });
        Slider bgColorSlider = new Slider(0, 1, 0);
        Button mapButton = new Button("Property Location");
        GoogleMap map = new GoogleMap();
        Stage stage2 = new Stage();
        mapButton.setVisible(false);

        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        TableView.TableViewSelectionModel<PropertyAssessment> selectionModel = tableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observable, oldSelection, newSelection) -> { //Waits for a selection in the tableview
                    if (newSelection != null) {
                        PropertyAssessment selectedItem = selectionModel.getSelectedItem(); //Save the selected row
                        map.setLocation(selectedItem.getLocation()); //Save the selected location of the row
                        mapButton.setVisible(true);
                        mapButton.setOnAction(e -> {
                            try {
                                if (!selectionModel.isEmpty()) {
                                    map.setLongitude(selectedItem.getLocation().getLongitude());
                                    map.setLatitude(selectedItem.getLocation().getLatitude());
                                    stage2.setTitle("Map");
                                    map.setBgColor(bgColor);
                                    map.start(stage2);
                                    System.out.println(selectedItem.getLocation());
                                }

                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }

                        });
                    }
                });

        resetButton.addEventHandler(ActionEvent.ACTION, event -> {
            selectionModel.clearSelection();
            mapButton.setVisible(false);
        });

        stage2.setOnShown(e -> { //Sets TableView UI to the left of the screen and Map to the right
           primaryStage.setX(0);
           primaryStage.setY(0);
            stage2.setX(width*0.66);
            stage2.setY(0);
        });

        stage2.setOnHidden(e -> {
            primaryStage.centerOnScreen();
        });

        // Instantiate windows and labels for displaying property assessment search GUI.
        HBox valFields = new HBox(minField, maxField);
        HBox bottomButtons = new HBox(searchButton, resetButton);
        HBox bButtons = new HBox(mapButton, trafficButton);
        HBox topContainer = new HBox();

        Label title = new Label("Edmonton Property Assessments (2022)");
        Label dataSource = new Label("Select Data Source");
        Label findProp = new Label("Find Property Assessment");
        Label numStr = new Label("Account Number:");
        Label addrStr = new Label("Address (#suite #house street):");
        Label neighStr = new Label("Neighbourhood:");
        Label classStr = new Label("Assessment Class:");
        Label valueStr = new Label("Assessed Value Range:");

        topContainer.getChildren().addAll(title, bgColorSlider);

        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        dataSource.setFont(Font.font("System", FontWeight.BOLD, 18));
        findProp.setFont(Font.font("System", FontWeight.BOLD, 18));
        numStr.setFont(Font.font("System", FontPosture.REGULAR,14));
        addrStr.setFont(Font.font("System", FontPosture.REGULAR,14));
        neighStr.setFont(Font.font("System", FontPosture.REGULAR,14));
        classStr.setFont(Font.font("System", FontPosture.REGULAR,14));
        valueStr.setFont(Font.font("System", FontPosture.REGULAR,14));
        VBox.setMargin(dataSource, new Insets(0, 0, 0, 10));
        VBox.setMargin(fileSelect, new Insets(8, 0, 0, 10));
        VBox.setMargin(classSelect, new Insets(8, 0, 0, 10));
        VBox.setMargin(readButton, new Insets(8, 0, 0, 10));
        VBox.setMargin(findProp, new Insets(20, 0, 0, 10));
        VBox.setMargin(numStr, new Insets(6, 0, 0, 10));
        VBox.setMargin(addrStr, new Insets(6, 0, 0, 10));
        VBox.setMargin(neighStr, new Insets(6, 0, 0, 10));
        VBox.setMargin(classStr, new Insets(6, 0, 0, 10));
        VBox.setMargin(valueStr, new Insets(6, 0, 0, 10));
        VBox.setMargin(accField, new Insets(0, 38, 0, 10));
        VBox.setMargin(addrField, new Insets(0, 38, 0, 10));
        VBox.setMargin(hoodField, new Insets(0, 38, 0, 10));
        VBox.setMargin(classField, new Insets(0, 38, 0, 10));
        HBox.setMargin(title, new Insets(0, 0, 0, 300));
        HBox.setMargin(bgColorSlider, new Insets(0, 0, 0, 400));
        HBox.setMargin(maxField, new Insets(10, 38, 0, 10));
        HBox.setMargin(minField, new Insets(10, 0, 0, 10));
        HBox.setMargin(searchButton, new Insets(10, 0, 0, 10));
        HBox.setMargin(resetButton, new Insets(10, 0, 0, 12));
        VBox.setMargin(hoodStatsButton, new Insets(10, 0, 0, 10));
        VBox.setMargin(classStatsButton, new Insets(10, 0, 0, 10));
        HBox.setMargin(mapButton, new Insets(10, 0, 0, 12));
        HBox.setMargin(trafficButton, new Insets(10, 0, 0, 10));
        bgColorSlider.setMajorTickUnit(1f);
        bgColorSlider.setSnapToTicks(true);
        bgColorSlider.setMajorTickUnit(1);
        bgColorSlider.setMinorTickCount(0);
        maxField.setPrefWidth(200);
        hoodStatsButton.setPrefWidth(252);
        classStatsButton.setPrefWidth(252);
        minField.setPrefWidth(200);
        searchButton.setPrefWidth(120);
        resetButton.setPrefWidth(120);
        mapButton.setPrefWidth(120);
        trafficButton.setPrefWidth(120);
        fileSelect.setPrefWidth(252);
        classSelect.setPrefWidth(252);
        readButton.setPrefWidth(252);
        bgColorSlider.setPrefWidth(80);
        leftMenu.setPadding(new Insets(0,20, 20,0));
        for (TableColumn<?, ?> column : tableView.getColumns()) {
            column.setPrefWidth(159); // set the preferred width to 200 pixels for each column
        }
        tableView.getColumns().get(5).setPrefWidth(250);
        // add all created elements to the window
        leftSide.getChildren().addAll(dataSource, fileSelect, readButton, findProp, numStr, accField, addrStr, addrField, neighStr, hoodField, classStr, classSelect, valueStr, valFields, bottomButtons, classStatsButton, hoodStatsButton, bButtons);
        leftMenu.setTop(topContainer);
        leftMenu.setLeft(leftSide);
        leftMenu.setCenter(tableView);

        Scene scene = new Scene(leftMenu, width*0.625,height*0.55);

        bgColorSlider.valueProperty().addListener((observable, oldValue, newValue) -> { //waits for change in slider value
            double value = newValue.doubleValue();
            if (value == 1.0){
                //scene.getRoot().setStyle("-fx-background-color: black;");
                bgColor = false;
                title.setStyle("-fx-text-fill: #BDC8D6");
                dataSource.setStyle("-fx-text-fill: #BDC8D6");
                findProp.setStyle("-fx-text-fill: #BDC8D6");
                numStr.setStyle("-fx-text-fill: #BDC8D6");
                addrStr.setStyle("-fx-text-fill: #BDC8D6");
                neighStr.setStyle("-fx-text-fill: #BDC8D6");
                classStr.setStyle("-fx-text-fill: #BDC8D6");
                valueStr.setStyle("-fx-text-fill: #BDC8D6");
                leftMenu.setStyle("-fx-background-color: #35393e");
                tableView.setStyle("-fx-background-color: #35393e");

            }

            else {
                bgColor = true;
                //scene.getRoot().setStyle("-fxbackground-color: white");
                title.setStyle("-fx-text-fill: black");
                dataSource.setStyle("-fx-text-fill: black");
                findProp.setStyle("-fx-text-fill: black");
                numStr.setStyle("-fx-text-fill: black");
                addrStr.setStyle("-fx-text-fill: black");
                neighStr.setStyle("-fx-text-fill: black");
                classStr.setStyle("-fx-text-fill: black");
                valueStr.setStyle("-fx-text-fill: black");
                leftMenu.setStyle("-fx-background-color: white");
                tableView.setStyle("-fx-background-color: white");

            }
        });



        primaryStage.setTitle("Edmonton Property Assessments");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}