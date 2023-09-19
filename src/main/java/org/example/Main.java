package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.StreamSupport.stream;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //get next five bank holidays
        var client = HttpClient.newBuilder().build();// set parameters
        var request = HttpRequest.newBuilder(URI.create("https://www.gov.uk/bank-holidays.json"))
                .GET()
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var objectMapper = new ObjectMapper();

        var events = objectMapper.readTree(response.body()).get("england-and-wales").get("events");
        String todayDate = LocalDate.now().toString();
        Iterator<JsonNode> iterator = events.iterator();
        boolean isFuture;
        JsonNode event;
        do {
            event = iterator.next();
            isFuture = event.get("date").asText().compareTo(todayDate) > 0;
        } while (!isFuture && iterator.hasNext());

        for (int i = 0; i < 5 && iterator.hasNext(); i++, event = iterator.next()) {
            System.out.println(
                    new StringBuilder(event.get("date").asText())
                            .append(" - ")
                            .append(event.get("title"))
            );
        }
//        Using stream
        stream(events.spliterator(), false)
                .dropWhile(e -> e.get("date").asText().compareTo(todayDate) <= 0)
                .map(e -> e.get("date"));
    }

    public class BankHolidayFetcher {

        public static class NationData {
            String division;
            List<Event> events;

        }

        public static class Event {
            String title;
            String date;
            String notes;
            boolean bunting;
        }

        public
    }
}
