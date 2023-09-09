package com.csc340f23.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
        getPrediction();
        System.exit(0);
    }

    /**
     * Get a prediction on whether inputted name belongs to a male or a female.
     *
     * @return The name's prediction
     */
    @GetMapping("predict")
    public static void getPrediction() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name to predict for: ");
        String name = in.next();
        try {
            String url = ("https://api.genderize.io?name=" + name);
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jSonQuote = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jSonQuote);

            //Parse out the most important info from the response.
            String prediction = root.get("gender").asText();
            String probability = root.get("probability").asText();
            double probabilityPercent = Double.parseDouble(probability) * 100;

            System.out.println("Prediction: " + prediction + " With confidence level of: " + probabilityPercent + "%");

        } catch (JsonProcessingException ex) {
            System.out.println("error in ?name=");
        }
    }
}
