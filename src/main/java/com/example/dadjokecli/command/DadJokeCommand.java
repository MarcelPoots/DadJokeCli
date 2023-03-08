package com.example.dadjokecli.command;

import com.example.dadjokecli.model.JokeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ShellComponent
public class DadJokeCommand {

    @ShellMethod
    public void random() throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dotenv.get("API_URL")))
                .header("Accept", "application/json")
                .GET()
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();

        if (response.statusCode() == 200) {
            JokeResponse jokeResponse = mapper.readValue(response.body(), JokeResponse.class);
            System.err.println(jokeResponse.joke());
        } else {
            System.err.println("Could not get joke.." );
        }
       // JokeResponse

    }
}
