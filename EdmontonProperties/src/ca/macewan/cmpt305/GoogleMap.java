package ca.macewan.cmpt305;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class GoogleMap {
    private WebView webView;
    private String location;
    private String longitude;
    private String latitude;
    private int width = 600;
    private int height = 580;
    public Boolean bgColor = true;



    public void start(Stage primaryStage) throws Exception {
        File htmlFile = File.createTempFile("map", ".html");
        AtomicReference<String> htmlContent = new AtomicReference<>(String.format("<iframe src=\"%s\" width=\"" + width + "\" height=\"" + height + "\"></iframe>", "https://www.google.com/maps/embed/v1/place?key=AIzaSyAqKxlxMMFZim7xL4nW4CJEM9vfUSeFHjI&maptype=roadmap&q="
                + latitude + ","
                + longitude
                + "&zoom=18"));
        FileWriter writer = new FileWriter(htmlFile);
        writer.write(htmlContent.get());
        writer.close();

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(htmlFile.toURI().toString());


        Button roadMapButton = new Button("RoadMap");
        Button satelliteButton = new Button("Satellite");
        HBox hbox = new HBox();

        HBox.setMargin(roadMapButton, new Insets(10, 0 ,10, 10));
        HBox.setMargin(satelliteButton, new Insets(10, 0 ,10, 10));

        hbox.getChildren().addAll(roadMapButton, satelliteButton);
        hbox.setStyle("-fx-background-color: #35393e");
        //StackPane stackPane = new StackPane(webView);
        BorderPane borderPane = new BorderPane(webView);
        borderPane.setCenter(webView);
        borderPane.setTop(hbox);
        GridPane.setHgrow(webView, Priority.ALWAYS);
        borderPane.setPrefSize(width, height);
        webView.setPrefSize(width, height);
        Scene scene = new Scene(borderPane, width, height);
        if (bgColor) {
            borderPane.setStyle("-fx-background-color: white");
        }
        else {
            borderPane.setStyle("-fx-background-color: #35393e");
        }
        primaryStage.setScene(scene);
        primaryStage.show();
        roadMapButton.setOnAction(e -> {
            if (htmlContent.get().contains("roadmap")){
                e.consume();
            }
            else {
                htmlContent.set(String.format("<iframe src=\"%s\" width=\"" + width + "\" height=\"" + height + "\" ></iframe>", "https://www.google.com/maps/embed/v1/place?key=AIzaSyAqKxlxMMFZim7xL4nW4CJEM9vfUSeFHjI&maptype=roadmap&q="
                        + latitude + ","
                        + longitude
                        + "&zoom=18"));
                webView.getEngine().loadContent(String.valueOf(htmlContent));

            }
        });

        satelliteButton.setOnAction(e -> {
            if (htmlContent.get().contains("satellite")){
                e.consume();
            }
            else {
                htmlContent.set(String.format("<iframe src=\"%s\" width=\"" + width + "\" height=\"" + height + "\"></iframe>", "https://www.google.com/maps/embed/v1/place?key=AIzaSyAqKxlxMMFZim7xL4nW4CJEM9vfUSeFHjI&maptype=satellite&q="
                        + latitude + ","
                        + longitude
                        + "&zoom=18"));
                webView.getEngine().loadContent(String.valueOf(htmlContent));

            }
        });

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            width = newValue.intValue();
            htmlContent.set(String.format("<iframe src=\"%s\" width=\"" + width + "\" height=\"" + height + "\"></iframe>", "https://www.google.com/maps/embed/v1/place?key=AIzaSyAqKxlxMMFZim7xL4nW4CJEM9vfUSeFHjI&maptype=satellite&q="
                    + latitude + ","
                    + longitude
                    + "&zoom=18"));
            webView.getEngine().loadContent(String.valueOf(htmlContent));
        });

        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            height = newValue.intValue();
            htmlContent.set(String.format("<iframe src=\"%s\" width=\"" + width + "\" height=\"" + height + "\"></iframe>", "https://www.google.com/maps/embed/v1/place?key=AIzaSyAqKxlxMMFZim7xL4nW4CJEM9vfUSeFHjI&maptype=satellite&q="
                    + latitude + ","
                    + longitude
                    + "&zoom=18"));
            webView.getEngine().loadContent(String.valueOf(htmlContent));
        });
    }

    public void setLocation(Location loc) {
        this.location = loc.toString();
    }
    public void setLatitude(double lat) {
        this.latitude = String.valueOf(lat);
    }
    public void setLongitude(double lon) {
        this.longitude = String.valueOf(lon);
    }

    public void setBgColor(boolean bg) {
        this.bgColor = bg;
    }

}

