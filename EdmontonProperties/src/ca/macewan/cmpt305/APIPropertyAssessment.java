package ca.macewan.cmpt305;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class APIPropertyAssessment implements PropertyAssessmentInterface {
    private String endpoint;
    private int maxRecords;
    private List<PropertyAssessment> Data;
    private HttpClient client;

    public APIPropertyAssessment() throws URISyntaxException {
        this("https://data.edmonton.ca/resource/q7d6-ambg.csv");
    }

    public APIPropertyAssessment(String endpoint) throws URISyntaxException {
        this.Data = new ArrayList<>();
        this.endpoint = endpoint;
        this.maxRecords = 1000; //Displaying the whole dataset is slow, so we limit it to 1000 Records
        this.client = HttpClient.newHttpClient();
    }

    private HttpRequest buildHttpRequest(String query) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(URI.create(query))
                .GET()
                .build();
    }

    private List<PropertyAssessment> getProperties(String query) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = buildHttpRequest(query);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Scanner scanner = new Scanner(response.body());
        if (scanner.hasNextLine()) scanner.nextLine();
        List<PropertyAssessment> results = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine().replace("\"","");
            results.add(CreateProperty.CreatePropertyAssessment((data)));
        }
        return results;

    }

    public List<PropertyAssessment> getData() {
        if (this.Data.size() > 0) return Collections.unmodifiableList(Data); //in case data is already populated

        try {
            int startIndex = 0;
            String query = this.endpoint
                    + "$limit=" + maxRecords
                    + "&$offset=" + startIndex
                    + "&$order=account_number";

            List<PropertyAssessment> current = getProperties(query);
            Data.addAll(current);
            while (current.size() == maxRecords) {
                startIndex += maxRecords;
                query = this.endpoint
                        + "?$limit=" + maxRecords
                        + "&$offset=" + startIndex
                        + "&$order=account_number";
                current = getProperties(query);
                Data.addAll(current);
            }
            return Collections.unmodifiableList(Data);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return null;
        }
    }

    public List<PropertyAssessment> getData(int maxRecords){
        return getData(maxRecords,0);
    }

    public List<PropertyAssessment> getData(int maxRecords, int startIndex){
        try {
            String query = this.endpoint;
            return getProperties(query);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return null;
        }
    }
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        try {
            String query = this.endpoint + "?account_number=" + accountNumber;
            List<PropertyAssessment> results = getProperties(query);
            if (results.size() > 0) {
                return results.get(0);
            }
            return null;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return null;
        }
    }

    public List<PropertyAssessment> getByAssessmentClassData(String assessmentClass) {
        try {
            assessmentClass = assessmentClass.toUpperCase().replace(" ", "+");
            int startIndex = 0;
            String query = this.endpoint + "?$query="
                    + "SELECT+*+WHERE+(mill_class_1='" + assessmentClass + "'"
                    + "+OR+mill_class_2='" + assessmentClass + "'"
                    + "+OR+mill_class_3='" + assessmentClass + "')"
                    + "+ORDER+BY+account_number"
                    + "+LIMIT" + maxRecords
                    + "+OFFSET+" + startIndex;
            List<PropertyAssessment> current = getProperties((query));
            List<PropertyAssessment> results = new ArrayList<>(current);
            while (current.size() == maxRecords) {
                maxRecords += startIndex;
                query = this.endpoint + "?$query="
                        + "SELECT+*+WHERE+(mill_class_1='" + assessmentClass + "'"
                        + "+OR+mill_class_2='" + assessmentClass + "'"
                        + "+OR+mill_class_3='" + assessmentClass + "')"
                        + "+ORDER+BY+account_number"
                        + "+LIMIT" + maxRecords
                        + "+OFFSET+" + startIndex;
                current = getProperties(query);
                results.addAll(current);
            }
            return results;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return null;
        }
    }
    public List<PropertyAssessment> getByNeighbourhoodData(String neighbourhood) {
        try {
            neighbourhood = neighbourhood.toUpperCase().replace(" ", "+");
            int startIndex = 0;
            String query = this.endpoint + "?$query="
                    + "SELECT+*+WHERE+(neighbourhood='" + neighbourhood + "'"
                    + "+ORDER+BY+account_number"
                    + "+LIMIT" + maxRecords
                    + "+OFFSET+" + startIndex;
            List<PropertyAssessment> current = getProperties((query));
            List<PropertyAssessment> results = new ArrayList<>(current);
            while (current.size() == maxRecords) {
                startIndex += maxRecords;
                query = this.endpoint + "?$query="
                        + "SELECT+*+WHERE+(neighbourhood='" + neighbourhood + "'"
                        + "+ORDER+BY+account_number"
                        + "+LIMIT" + maxRecords
                        + "+OFFSET+" + startIndex;
                current = getProperties(query);
                results.addAll(current);
            }
            return results;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return null;
        }
    }

}


