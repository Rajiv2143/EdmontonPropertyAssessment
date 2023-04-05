package ca.macewan.cmpt305;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssessmentGraph extends Application {

    private ObservableList<XYChart.Data<String, Number>> barChartData;
    private ObservableList<XYChart.Data<String, Number>> lineChartData;
    private ObservableList<PieChart.Data> pieChartData;

    private BarChart<String, Number> barChart;
    private LineChart<String, Number> lineChart;
    private PieChart pieChart;

    private double width = Screen.getPrimary().getBounds().getWidth();
    private double height = Screen.getPrimary().getBounds().getHeight();

    private void initializeChartData() {
        // Initialize the charts as empty
        this.barChartData = FXCollections.observableArrayList();
        this.lineChartData = FXCollections.observableArrayList();
        this.pieChartData = FXCollections.observableArrayList();
    }
    //Empty constructor
    public AssessmentGraph() {
    }

    //Initialize the new stage
    public void start(Stage primaryStage) {
        initializeChartData();

        // Create the custom x axis for the bar and line chart to line up data
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickLabelGap(3);
        xAxis.setTickLabelRotation(90);
        xAxis.setTickMarkVisible(true);
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickLabelFont(Font.font("Arial", FontWeight.NORMAL, 14)); // set font size if the x-axis labels
        xAxis.setPrefWidth(100);

        CategoryAxis xAxis1 = new CategoryAxis();
        xAxis1.setTickLabelRotation(90);
        xAxis1.setTickMarkVisible(true);
        xAxis1.setTickLabelsVisible(true);
        xAxis1.setTickLabelFont(Font.font("Arial", FontWeight.NORMAL, 14));
        xAxis1.setPrefWidth(100);

        // Initialize the charts to be displayed with their respective data observable lists.
        barChart = new BarChart<>(xAxis, new NumberAxis());
        barChart.setData(FXCollections.observableArrayList(
                new XYChart.Series<>("Mean assessed value of assessment class", barChartData)));
        barChart.setPrefSize(1600, 800);

        lineChart = new LineChart<>(xAxis1, new NumberAxis());
        lineChart.setData(FXCollections.observableArrayList(
                new XYChart.Series<>("Mean assessed value of assessment class", lineChartData)));

        pieChart = new PieChart();
        pieChart.setData(pieChartData);

        // Allow charts to grow as much as they need to.
        barChart.setMaxWidth(Double.MAX_VALUE);
        barChart.setMaxHeight(Double.MAX_VALUE);

        lineChart.setMaxWidth(Double.MAX_VALUE);
        lineChart.setMaxHeight(Double.MAX_VALUE);

        pieChart.setMaxWidth(Double.MAX_VALUE);
        pieChart.setMaxHeight(Double.MAX_VALUE);

        // Create VBox to display the title
        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        Label titleLabel = new Label("Distribution of Average Mean Assessment Value by Assessment Class");
        titleLabel.setStyle("-fx-font-size: 24;");

        topBox.getChildren().add(titleLabel);

        // Create HBox to hold charts
        HBox chartBox = new HBox(10);
        chartBox.setAlignment(Pos.CENTER);
        chartBox.setPadding(new Insets(10));
        chartBox.setStyle("-fx-background-color: white;");

        // Search through the csv data for all assessment class types, and add them to the labels array.
        List<BigInteger> means = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        CSVPropertyAssessment p = new CSVPropertyAssessment("q7d6-ambg.csv");
        List<PropertyAssessment> pList = p.getData();
        for (PropertyAssessment prop : pList) {
            for (int k = 0; k < prop.getAssessmentClass().size(); k++) {
                String currentClass = prop.getAssessmentClass().get(k).getAssessmentClass();
                if (!labels.contains(currentClass))
                    labels.add(currentClass);
            }
        }

        // Calculate means for every property assessment class type and add them to the means array
        for (String label : labels) {
            CSVPropertyAssessment neighbourhoodProperties = new CSVPropertyAssessment(p.getByAssessmentClassData(label));
            means.add(neighbourhoodProperties.getMean());
        }

        // Map each class to its respective mean value and plot them on each graph.
        for (int i = 0; i < labels.size() - 1; i++) {
            barChartData.add(new XYChart.Data<>(labels.get(i), means.get(i)));
            lineChartData.add(new XYChart.Data<>(labels.get(i), means.get(i)));
            pieChartData.add(new PieChart.Data(labels.get(i), Double.parseDouble(String.valueOf(means.get(i)))));
        }




        // Instantiate chart pane for graphs and borderpane for new window
        TilePane chartPane = new TilePane();
        chartPane.setPadding(new Insets(10));
        chartPane.setHgap(10);
        chartPane.setVgap(10);
        chartPane.setTileAlignment(Pos.TOP_LEFT);
        pieChart.setLegendVisible(false);

        chartPane.getChildren().addAll(barChart, lineChart, pieChart);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(chartPane);
        scrollPane.setFitToWidth(true);



        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        layout.setTop(topBox);
        layout.setCenter(scrollPane);
        Scene scene = new Scene(layout, width*0.90, height*0.90);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Property Stats");


        primaryStage.show();
    }
}