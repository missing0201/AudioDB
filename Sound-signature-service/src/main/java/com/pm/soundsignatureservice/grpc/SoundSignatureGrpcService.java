package com.pm.soundsignatureservice.grpc;

import com.pm.soundsignatureservice.model.SignatureEntity;
import com.pm.soundsignatureservice.repo.SignatureRepo;
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
    private SignatureRepo signatureRepository;

    public SoundSignatureGrpcService(SignatureRepo signatureRepository) {
        this.signatureRepository = signatureRepository;
    }

    @Override
    //templated Demo for an earphone will improve to identify and correctly label each earphone in the future
    public void getSignature(GetSignatureRequest  request,
                             StreamObserver<GetSignatureResponse> responseObserver) {

        String earphoneId = request.getEarphoneId();
        Long id = Long.parseLong(earphoneId);

        SignatureEntity entity = signatureRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Signature not found for earphoneId: " + earphoneId));

        Signature signature = Signature.newBuilder()
                .setId(String.valueOf(entity.getEarphoneId()))
                .setPrimarySignature(mapToProtoEnum(entity.getPrimarySignature()))
                .setBassScore(entity.getBassScore())
                .setMidsScore(entity.getMidsScore())
                .setTrebleScore(entity.getTrebleScore())
                .setDescription(entity.getDescription() == null ? "" : entity.getDescription())
                .build();

        GetSignatureResponse response = GetSignatureResponse.newBuilder()
                .setSignature(signature)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private SoundSignature mapToProtoEnum(String value) {
        try {
            return SoundSignature.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return SoundSignature.SOUND_SIGNATURE_UNSPECIFIED;
        }
    }
}
