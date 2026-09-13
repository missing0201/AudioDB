package com.pm.earphonesdb.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sound_signature.GetSignatureRequest;
import sound_signature.GetSignatureResponse;
import sound_signature.SoundSignatureServiceGrpc;

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
}
