package com.gr.kolychev.signatureproviderservice.processor;

import com.gr.kolychev.signatureproviderservice.model.Signature;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import util.HmacUtils;

import javax.validation.constraints.NotBlank;

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
        var signatureString = HmacUtils.calculateHMAC(ALGORITHM, data, secretKey);
        return new Signature(signatureString);
    }
}
