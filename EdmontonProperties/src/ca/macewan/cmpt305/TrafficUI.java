package ca.macewan.cmpt305;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.Scanner;

public class TrafficUI extends BorderPane {

    private TableView<Traffic> table;
    private TableColumn<Traffic, String> idCol;
    private TableColumn<Traffic, String> dateIssuedCol;
    private TableColumn<Traffic, String> startDateCol;
    private TableColumn<Traffic, String> finishDateCol;
    private TableColumn<Traffic, String> statusCol;
    private TableColumn<Traffic, String> closureCol;
    private TableColumn<Traffic, String> onStreetCol;
    private TableColumn<Traffic, String> fromStreetCol;
    private TableColumn<Traffic, String> toStreetCol;
    private TableColumn<Traffic, String> impactCol;
    private TableColumn<Traffic, String> durationCol;
    private TableColumn<Traffic, String> detailsCol;
    private TableColumn<Traffic, String> descriptionCol;
    private TableColumn<Traffic, String> activityTypeCol;
    private TableColumn<Traffic, String> trafficDistrictCol;
    private TableColumn<Traffic, String> infrastructureCol;
    private TableColumn<Traffic, String> pointCol;

    public TrafficUI() {
    }
    public void start(Stage primaryStage) {
        setupTable();
        this.setCenter(table);

        try {
            FileReader fr = new FileReader("Traffic_Disruptions.csv");
            Scanner sc = new Scanner(fr);
            sc.nextLine();
            ObservableList<Traffic> observableList = FXCollections.observableArrayList();
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                String[] data = line.split(",");
                if (data[0] == null || data[0].isEmpty()){
                    data[0] = "N/A";
                } else if (data[1] == null || data[1].isEmpty()){
                    data[1] = "N/A";
                } else if (data[2] == null || data[2].isEmpty()){
                    data[2] = "N/A";
                } else if (data[3] == null || data[3].isEmpty()){
                    data[3] = "N/A";
                } else if (data[4] == null || data[4].isEmpty()){
                    data[4] = "N/A";
                } else if (data[5] == null || data[5].isEmpty()){
                    data[5] = "N/A";
                } else if (data[6] == null || data[6].isEmpty()){
                    data[6] = "N/A";
                } else if (data[7] == null || data[7].isEmpty()){
                    data[7] = "N/A";
                } else if (data[8] == null || data[8].isEmpty()){
                    data[8] = "N/A";
                } else if (data[9] == null || data[9].isEmpty()){
                    data[9] = "N/A";
                } else if (data[10] == null || data[10].isEmpty()){
                    data[10] = "N/A";
                } else if (data[11] == null || data[11].isEmpty()){
                    data[11] = "N/A";
                } else if (data[12] == null || data[12].isEmpty()){
                    data[12] = "N/A";
                } else if (data[13] == null || data[13].isEmpty()){
                    data[13] = "N/A";
                } else if (data[14] == null || data[14].isEmpty()){
                    data[14] = "N/A";
                } else if (data[15] == null || data[15].isEmpty()){
                    data[15] = "N/A";
                } else if (data[16] == null || data[16].isEmpty()){
                    data[16] = "N/A";
                }
                Traffic traffic = new Traffic();
                traffic.setId(data[0]);
                traffic.setDateIssued(data[1]);
                traffic.setStartDate(data[2]);
                traffic.setFinishDate(data[3]);
                traffic.setStatus(data[4]);
                traffic.setClosure(data[5]);
                traffic.setOnStreet(data[6]);
                traffic.setFromStreet(data[7]);
                traffic.setToStreet(data[8]);
                traffic.setImpact(data[9]);
                traffic.setDuration(data[10]);
                traffic.setDetails(data[11]);
                traffic.setDescription(data[12]);
                traffic.setActivityType(data[13]);
                traffic.setTrafficDistrict(data[14]);
                traffic.setInfrastructure(data[15]);
                traffic.setPoint(data[16]);
                observableList.add(traffic);
            }
            this.idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            this.dateIssuedCol.setCellValueFactory(new PropertyValueFactory<>("dateIssued"));
            this.startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            this.finishDateCol.setCellValueFactory(new PropertyValueFactory<>("finishDate"));
            this.statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            this.closureCol.setCellValueFactory(new PropertyValueFactory<>("closure"));
            this.onStreetCol.setCellValueFactory(new PropertyValueFactory<>("onStreet"));
            this.fromStreetCol.setCellValueFactory(new PropertyValueFactory<>("fromStreet"));
            this.toStreetCol.setCellValueFactory(new PropertyValueFactory<>("toStreet"));
            this.impactCol.setCellValueFactory(new PropertyValueFactory<>("impact"));
            this.durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
            this.detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
            this.descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            this.activityTypeCol.setCellValueFactory(new PropertyValueFactory<>("activityType"));
            this.trafficDistrictCol.setCellValueFactory(new PropertyValueFactory<>("trafficDistrict"));
            this.infrastructureCol.setCellValueFactory(new PropertyValueFactory<>("infrastructure"));
            this.pointCol.setCellValueFactory(new PropertyValueFactory<>("point"));
            table.setItems(observableList);
        }catch (Exception e){
            e.printStackTrace();
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(table);
        Scene scene = new Scene(borderPane);

        primaryStage.setTitle("Edmonton Property Assessments");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setupTable(){
        table = new TableView<>();
        idCol = new TableColumn<>("ID");
        dateIssuedCol = new TableColumn<>("Date Issued");
        startDateCol = new TableColumn<>("Start Date");
        finishDateCol = new TableColumn<>("Finish Date");
        statusCol = new TableColumn<>("Status");
        closureCol = new TableColumn<>("Closure");
        onStreetCol = new TableColumn<>("On Street");
        fromStreetCol = new TableColumn<>("From Street");
        toStreetCol = new TableColumn<>("To Street");
        impactCol = new TableColumn<>("Impact");
        durationCol = new TableColumn<>("Duration");
        detailsCol = new TableColumn<>("Details");
        descriptionCol = new TableColumn<>("Description");
        activityTypeCol = new TableColumn<>("Activity Type");
        trafficDistrictCol = new TableColumn<>("Traffic District");
        infrastructureCol = new TableColumn<>("Infrastructure");
        pointCol = new TableColumn<>("Point");

        table.getColumns().addAll(idCol,dateIssuedCol,startDateCol,finishDateCol,statusCol,closureCol,onStreetCol,fromStreetCol,toStreetCol,impactCol,durationCol,detailsCol,descriptionCol,activityTypeCol,trafficDistrictCol,infrastructureCol,pointCol);
    }

}