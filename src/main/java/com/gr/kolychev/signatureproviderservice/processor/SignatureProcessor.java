package com.gr.kolychev.signatureproviderservice.processor;

import com.gr.kolychev.signatureproviderservice.model.Signature;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotBlank;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@ConfigurationProperties(prefix = "signature.processing")
@Setter
@Slf4j
@Validated
public class SignatureProcessor {

    private static final String ALGORITHM = "HmacSHA256";

    @NotBlank
    private String secretKey;

    /**
     * Provides encrypted signature based on provided data. Uses Hmac SHA256 algorithm.
     * @param operationId {@link String} needed to track business requests in logs.
     * @param data {@link String} data to be encrypted.
     * @return {@link Signature}
     */
    public Signature createSignature(String operationId, String data) {
        log.info("Processing signature for operationId: {}", operationId);
        var signatureString = calculateHMAC(operationId, data);
        return new Signature(signatureString);
    }

    @SneakyThrows({NoSuchAlgorithmException.class, InvalidKeyException.class})
    private String calculateHMAC(String operationId, String data) {
        log.info("Calculating HMAC for operationId: {}", operationId);
        Mac sha256HMAC = Mac.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        sha256HMAC.init(keySpec);

        byte[] hash = sha256HMAC.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
