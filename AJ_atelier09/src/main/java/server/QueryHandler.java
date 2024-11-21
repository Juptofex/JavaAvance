package server;

import main.Query;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class QueryHandler {
    Query query;

    public CompletableFuture<Void> sendQueryAndPrintResponse() {
        if (query.getMethod() == Query.QueryMethod.POST)
            throw new IllegalStateException("Only GET method is supported");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query.getUrl()))
                .GET()
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(response.statusCode());
                    return response;
                })
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);
    }
}

