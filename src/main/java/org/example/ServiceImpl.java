package org.example;


import com.example.grpc.ServiceGrpc;
import com.example.grpc.ServiceOuterClass;
import io.grpc.stub.StreamObserver;

public class ServiceImpl extends ServiceGrpc.ServiceImplBase {
    @Override
    public void test(ServiceOuterClass.Request request,
                     StreamObserver<ServiceOuterClass.Response> responseObserver) {
        System.out.println(request);

        if (request.getFirstValue() >= request.getLastValue()) {
            throw new IllegalArgumentException("Invalid paramets");
        }

        for (int i = request.getFirstValue(); i < request.getLastValue(); i++) {
            ServiceOuterClass.Response response = ServiceOuterClass.Response
                    .newBuilder()
                    .setValue(i)
                    .build();
            responseObserver.onNext(response);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }

}
