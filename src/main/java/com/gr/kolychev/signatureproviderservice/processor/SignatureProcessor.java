package com.gr.kolychev.signatureproviderservice.processor;

import com.gr.kolychev.signatureproviderservice.model.Signature;

/**
 * Processes provided data, creates signature.
 */
public interface SignatureProcessor {

    /**
     * Provides encrypted signature based on provided data.
     *
     * @param operationId {@link String} identification of operation.
     * @param data        {@link String} data to be encrypted.
     * @return {@link Signature}
     */
    Signature createSignature(String operationId, String data);

}
