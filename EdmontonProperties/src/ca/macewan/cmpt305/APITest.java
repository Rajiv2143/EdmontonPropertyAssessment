package ca.macewan.cmpt305;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class APITest {
    public static void main(String[] args) {
        System.out.print("Enter account number: ");
        Scanner scanner = new Scanner(System.in);
        int accountNumber = scanner.nextInt();

        String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv";
        String url = endpoint + "?account_number=" + accountNumber;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
