package com.pm.earphonesdb.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sound_signature.*;

@Service
public class SoundSignatureGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(SoundSignatureGrpcClient.class);
    private final SoundSignatureServiceGrpc.SoundSignatureServiceBlockingStub blockingStub;

    public SoundSignatureGrpcClient(
            @Value("${sound.signature.service.address:localhost}")String serverAddress,
            @Value("${sound.signature.service.grpc.port:9001}") int ServerPort) {

        log.info(
                "Connecting to SoundSignatureServiceGrpc at {}:{}",
                serverAddress,
                ServerPort
        );

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, ServerPort).usePlaintext().build();

        blockingStub = SoundSignatureServiceGrpc.newBlockingStub(channel);
    }

    public GetSignatureResponse getSignature(String id){
        GetSignatureRequest request= GetSignatureRequest.newBuilder().setEarphoneId(id).build();

        GetSignatureResponse response= blockingStub.getSignature(request);
        log.info("getSignature response={}", response);
        return response;
    }

    public SetSignatureResponse setSignature(Signature signature){
        SetSignatureRequest request= SetSignatureRequest.newBuilder().setSignature(signature).build();

        SetSignatureResponse response= blockingStub.setSignature(request);
        log.info("setSignature response={}", response);
        return response;
    }

    public UpdateSignatureResponse updateSignature(Signature signature){
        UpdateSignatureRequest request= UpdateSignatureRequest.newBuilder().setSignature(signature).build();

        UpdateSignatureResponse response= blockingStub.updateSignature(request);
        return response;
    }
}
