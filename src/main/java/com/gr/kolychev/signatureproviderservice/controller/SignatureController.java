package com.gr.kolychev.signatureproviderservice.controller;

import com.gr.kolychev.signatureproviderservice.aspect.annotation.LogExecutionTime;
import com.gr.kolychev.signatureproviderservice.model.RequestStatus;
import com.gr.kolychev.signatureproviderservice.model.Signature;
import com.gr.kolychev.signatureproviderservice.model.GenericResponse;
import com.gr.kolychev.signatureproviderservice.service.SignatureService;
import com.gr.kolychev.signatureproviderservice.validation.annotation.ValidToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@Validated
public class SignatureController {

    private SignatureService hmacSha256SignatureService;

    @LogExecutionTime
    @PostMapping("/api/v1/signatures/{operationId}")
    public ResponseEntity<GenericResponse<List<Signature>>> createSignature(@PathVariable String operationId,
                                                                            @NotEmpty(message =
                                                                                 "Missing request parameters")
                                                                         @RequestParam Map<String, String> params,
                                                                            @ValidToken
                                                                         @RequestHeader("Token") String token) {
        log.info("Incoming getSignature request with operationId: {}", operationId);
        var signature = hmacSha256SignatureService.createSignature(operationId, params);
        var signatureResponse = GenericResponse.<List<Signature>>builder()
                .status(RequestStatus.SUCCESS)
                .result(List.of(signature))
                .build();
        return ResponseEntity.ok(signatureResponse);
    }
}
