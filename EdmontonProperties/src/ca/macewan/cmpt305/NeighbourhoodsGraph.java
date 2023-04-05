package ca.macewan.cmpt305;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
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

public class NeighbourhoodsGraph extends Application {

    private ObservableList<XYChart.Data<String, Number>> barChartData;
    private ObservableList<XYChart.Data<String, Number>> lineChartData;
    private ObservableList<XYChart.Data<String, Number>> scatterChartData;
    private ObservableList<PieChart.Data> pieChartData;

    private BarChart<String, Number> barChart;
    private LineChart<String, Number> lineChart;
    private ScatterChart<String, Number> scatterChart;
    private PieChart pieChart;
    private double width = Screen.getPrimary().getBounds().getWidth();
    private double height = Screen.getPrimary().getBounds().getHeight();

    // Initialize the empty charts
    private void initializeChartData() {
        // Create the chart series objects but don't add any data
        this.barChartData = FXCollections.observableArrayList();
        this.lineChartData = FXCollections.observableArrayList();
        this.scatterChartData = FXCollections.observableArrayList();
        this.pieChartData = FXCollections.observableArrayList();
    }

    public NeighbourhoodsGraph() {
    }

    public void start(Stage primaryStage) {
        initializeChartData();

        // Create custom x axes for bar and line graphs
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickLabelRotation(90);
        xAxis.setTickMarkVisible(true);
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickLabelFont(Font.font("Arial", FontWeight.NORMAL, 8));
        xAxis.setPrefWidth(100);
        xAxis.setAutoRanging(false);
        xAxis.setPrefWidth(100);

        CategoryAxis xAxis1 = new CategoryAxis();
        xAxis1.setTickLabelRotation(90);
        xAxis1.setTickMarkVisible(true);
        xAxis1.setTickLabelsVisible(true);
        xAxis1.setTickLabelFont(Font.font("Arial", FontWeight.NORMAL, 8));
        xAxis1.setPrefWidth(100);

        // Initialize charts with their respective data observable lists.
        barChart = new BarChart<>(xAxis, new NumberAxis());
        barChart.setData(FXCollections.observableArrayList(
                new XYChart.Series<>("Mean assessed value of neighbourhood", barChartData)));
        barChart.setPrefSize(1600, 800);

        lineChart = new LineChart<>(xAxis1, new NumberAxis());
        lineChart.setData(FXCollections.observableArrayList(
                new XYChart.Series<>("Mean assessed value of neighbourhood", lineChartData)));

        scatterChart = new ScatterChart<>(new CategoryAxis(), new NumberAxis());
        scatterChart.setData(FXCollections.observableArrayList(
                new XYChart.Series<>("Scatter chart of individual assessed property values < 2,000,000$", scatterChartData)));

        pieChart = new PieChart();
        pieChart.setData(pieChartData);

        // Allow charts to expand as much as they need
        barChart.setMaxWidth(Double.MAX_VALUE);
        barChart.setMaxHeight(Double.MAX_VALUE);

        lineChart.setMaxWidth(Double.MAX_VALUE);
        lineChart.setMaxHeight(Double.MAX_VALUE);

        scatterChart.setMaxWidth(Double.MAX_VALUE);
        scatterChart.setMaxHeight(Double.MAX_VALUE);
        scatterChart.getXAxis().setVisible(false);

        pieChart.setMaxWidth(Double.MAX_VALUE);
        pieChart.setMaxHeight(Double.MAX_VALUE);

        // Create VBox for title
        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        Label titleLabel = new Label("Distribution of Average Mean Assessment Value by Neighbourhood");
        titleLabel.setStyle("-fx-font-size: 24;");

        topBox.getChildren().add(titleLabel);

        // Create HBox for the charts
        HBox chartBox = new HBox(10);
        chartBox.setAlignment(Pos.CENTER);
        chartBox.setPadding(new Insets(10));
        chartBox.setStyle("-fx-background-color: white;");

        // Find all neighbourhoods from the csv data and create a label for them.
        scatterChart.setVisible(true);
        List<BigInteger> means = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        CSVPropertyAssessment p = new CSVPropertyAssessment("q7d6-ambg.csv");
        List<PropertyAssessment> pList = p.getData();
        List<String> checked = new ArrayList<>();
        for (PropertyAssessment prop : pList) {
            String currentHood = prop.getNeighbourhood().getNeighbourhood();
            if (!labels.contains(currentHood))
                labels.add(currentHood);

            // Find each individual assessed value and add them to the values array, then find the means of each neighbourhood.
            if (!checked.contains(currentHood)) {
                List<PropertyAssessment> members = p.getByNeighbourhoodData(currentHood);
                for (PropertyAssessment member: members)
                    values.add(member.getAssessedValue());

                CSVPropertyAssessment neighbourhoodProperties =  new CSVPropertyAssessment(p.getByNeighbourhoodData(currentHood));
                means.add(neighbourhoodProperties.getMean());
                checked.add(currentHood);
            }
        }

        // Map the data into the charts
        for (int i = 0; i < labels.size(); i++) {
            barChartData.add(new XYChart.Data<>(labels.get(i), means.get(i)));
            lineChartData.add(new XYChart.Data<>(labels.get(i), means.get(i)));
            pieChartData.add(new PieChart.Data(labels.get(i), Double.parseDouble(String.valueOf(means.get(i)))));
        }

        // Add all the property assessment values under 2 million dollars to the scatter plot.
        int l = 0;
        for (int val : values) {
            if (val > 2000000)
                continue;
            XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(l), val);
            Circle dot = new Circle();
            dot.setFill(Color.BLUE);
            dot.setRadius(2);
            data.setNode(dot);
            scatterChartData.add(data);
            l++;
        }

        // Create the chart and window panes and add the data to them.
        TilePane chartPane = new TilePane();
        chartPane.setPadding(new Insets(10));
        chartPane.setHgap(10);
        chartPane.setVgap(10);
        chartPane.setTileAlignment(Pos.TOP_LEFT);
        pieChart.setLegendVisible(false);
        scatterChart.getXAxis().setVisible(false);

        chartPane.getChildren().addAll(barChart, lineChart, scatterChart, pieChart);
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