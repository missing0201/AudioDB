package com.pm.soundsignatureservice.grpc;

import com.pm.soundsignatureservice.model.SignatureEntity;
import com.pm.soundsignatureservice.repo.SignatureRepo;
import com.pm.soundsignatureservice.service.SignatureService;
import io.grpc.stub.StreamObserver;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sound_signature.*;

import java.util.NoSuchElementException;

@GrpcService
public class SoundSignatureGrpcService extends SoundSignatureServiceGrpc.SoundSignatureServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(SoundSignatureGrpcService.class);
    private SignatureService signatureService;

    public SoundSignatureGrpcService(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @Override
    public void getSignature(GetSignatureRequest  request,
                             StreamObserver<GetSignatureResponse> responseObserver) {

        GetSignatureResponse response =
                signatureService.getSignature(request.getEarphoneId());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
