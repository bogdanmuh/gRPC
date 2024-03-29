package org.example;

import com.example.grpc.ServiceGrpc;
import com.example.grpc.ServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    static AtomicInteger currentValue = new AtomicInteger(0);

    public static void main(String[] args) {
        int firstValue = 1;
        int lastValue = 10;
        int max = 50;

        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()
                .build();

        ServiceGrpc.ServiceStub stub = ServiceGrpc.newStub(channel);
        ServiceOuterClass.Request request = ServiceOuterClass.Request.newBuilder()
                .setFirstValue(firstValue)
                .setLastValue(lastValue)
                .build();
        System.out.println("Client is Starting ... ");
        stub.test(request, new StreamObserver<ServiceOuterClass.Response>() {
            @Override
            public void onNext(ServiceOuterClass.Response response) {
                System.out.println("new value : " + response.getValue());
                sum(response.getValue());

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.toString());
            }

            @Override
            public void onCompleted() {
                System.out.println("request completed");
            }
        });

        for (int i = 0; i < max; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sum(0);
            System.out.println("currentValue : " + currentValue);

        }
        channel.shutdownNow();
    }

    public static void sum(int x) {
        currentValue.set(currentValue.addAndGet(x + 1));
    }

}
