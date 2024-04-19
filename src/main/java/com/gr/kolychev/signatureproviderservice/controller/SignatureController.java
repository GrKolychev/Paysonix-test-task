package com.gr.kolychev.signatureproviderservice.controller;

import com.gr.kolychev.signatureproviderservice.aspect.annotation.LogExecutionTime;
import com.gr.kolychev.signatureproviderservice.model.RequestStatus;
import com.gr.kolychev.signatureproviderservice.model.Signature;
import com.gr.kolychev.signatureproviderservice.model.GenericResponse;
import com.gr.kolychev.signatureproviderservice.service.SignatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class SignatureController {

    private SignatureService hmacSha256SignatureService;

    @Operation(summary = "Creates signature based on provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created signature",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "403", description = "Token missing or invalid",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @LogExecutionTime
    @PostMapping("/api/v1/signatures/{operationId}")
    public ResponseEntity<GenericResponse<List<Signature>>> createSignature(@PathVariable String operationId,
                                                                            @RequestParam Map<String, String> params,
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
