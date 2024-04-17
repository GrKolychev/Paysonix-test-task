package com.gr.kolychev.signatureproviderservice.service;

import com.gr.kolychev.signatureproviderservice.model.Signature;
import com.gr.kolychev.signatureproviderservice.processor.SignatureProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SignatureService {

    private static final String PARAMS_KEY_VALUE_DELIMITER = "=";
    private static final String PARAMS_DELIMITER = "&";

    private SignatureProcessor processor;

    /**
     * Handles data received in requests and passes it to {@link SignatureProcessor}.
     * @param operationId {@link String} needed to track business requests in logs.
     * @param params {@link Map} incoming request parameters. Will be used as data to be encrypted.
     * @return {@link Signature}
     */
    public Signature getSignature(String operationId, Map<String, String> params) {
        log.info("Hands over parameters to signature processor for operationId {}", operationId);
        var data = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + PARAMS_KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(PARAMS_DELIMITER));
        return processor.createSignature(operationId, data);
    }
}
