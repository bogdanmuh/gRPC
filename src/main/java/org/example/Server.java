package org.example;

import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Hello world!
 */
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        io.grpc.Server server = ServerBuilder.forPort(8080)
                .addService(new ServiceImpl())
                .intercept(new Interceptor())
                .build();

        server.start();
        System.out.println("Server is start");
        server.awaitTermination();
    }
}
