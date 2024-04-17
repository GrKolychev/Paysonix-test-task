package com.gr.kolychev.signatureproviderservice.model;

import lombok.Builder;

/**
 * Represents the generic response entity which could be used for any response.
 * @param status {@link RequestStatus}.
 * @param result result of request which might be representing information in any format.
 * @param <T> The type of the result data.
 */
@Builder
public record GenericResponse<T>(RequestStatus status, T result) {
}
