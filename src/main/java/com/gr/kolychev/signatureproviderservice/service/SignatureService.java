package com.gr.kolychev.signatureproviderservice.service;

import com.gr.kolychev.signatureproviderservice.model.Signature;

import java.util.Map;

/**
 * Handles received data and orchestrates the signature related operations.
 */
public interface SignatureService {

    /**
     * Handles data received as map .
     *
     * @param operationId {@link String} identification of operation.
     * @param params      {@link Map} incoming data.
     * @return {@link Signature}
     */
    Signature createSignature(String operationId, Map<String, String> params);
}
