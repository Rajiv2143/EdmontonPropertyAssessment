package ca.macewan.cmpt305;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;

public class PropertyAssessmentGUI extends Application {
    private final int WIDTH = 1366;
    private final int HEIGHT = 768;

    private ObservableList<PropertyAssessment> tableData;

    private BorderPane borderPane;

    private PropertyAssessmentInterface pai;

    private boolean csvBool = false; //CSV FILE = TRUE : API = FALSE

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage Primarystage) throws Exception {
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setCenter(getCenterView());
        borderPane.setRight(getHorizontalView());
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);

        Primarystage.setTitle("Edmonton Property Assessments");
        Primarystage.setScene(scene);
        Primarystage.show();
    }

    private VBox getCenterView() {
        Label title = new Label("Edmonton Property Assessments");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        TableView<PropertyAssessment> tableView = getTableView();
        VBox node = new VBox(8);
        node.setPadding(new Insets(0, 0, 0, 10));
        VBox.setVgrow(tableView, Priority.ALWAYS);
        node.getChildren().addAll(title, tableView);
        return node;
    }

    private HBox getHorizontalView() {
        Label title = new Label("CSV File");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        HBox hbox = new HBox(16);
        hbox.getChildren().addAll(title);
        return hbox;

    }


    private TableView<PropertyAssessment> getTableView() {
        TableView<PropertyAssessment> tableView = new TableView<>();

        try {
            pai = csvBool ? new CSVPropertyAssessment() : new APIPropertyAssessment();
        } catch (IOException | URISyntaxException e) {
            return tableView;
        }


        tableData = FXCollections.observableArrayList(pai.getData(1000));
        tableView.setItems(tableData);

        TableColumn<PropertyAssessment, Integer> accountColumn = new TableColumn<>("Account");
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        accountColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.08));
        tableView.getColumns().add(accountColumn);

        TableColumn<PropertyAssessment, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        tableView.getColumns().add(addressColumn);

        TableColumn<PropertyAssessment, Integer> assessedColumn = new TableColumn<>("Assessed Value");
        assessedColumn.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));
        assessedColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        tableView.getColumns().add(assessedColumn);

        TableColumn<PropertyAssessment, Integer> assessmentClassColumn = new TableColumn<>("Assessment Class");
        assessmentClassColumn.setCellValueFactory(new PropertyValueFactory<>("assessmentClass"));
        assessmentClassColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        tableView.getColumns().add(assessmentClassColumn);

        TableColumn<PropertyAssessment, Integer> neighbourhoodColumn = new TableColumn<>("Neighbourhood");
        neighbourhoodColumn.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
        neighbourhoodColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.24));
        tableView.getColumns().add(neighbourhoodColumn);

        TableColumn<PropertyAssessment, Integer> locationColumn = new TableColumn<>("(Latitude, Longitude)");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        tableView.getColumns().add(locationColumn);

        tableView.setPlaceholder(new Label("No data to display"));

        return tableView;

    }

    private static class CurrencyTableCell extends TableCell<PropertyAssessment, Integer> {
        private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        @Override
        protected void updateItem(Integer value, boolean empty) {
            super.updateItem(value, empty);
            currencyFormat.setMaximumFractionDigits(0);
            setText(empty ? "" : currencyFormat.format(value));
        }
    }
}
