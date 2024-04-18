package com.gr.kolychev.signatureproviderservice.controller;

import com.gr.kolychev.signatureproviderservice.model.Signature;
import com.gr.kolychev.signatureproviderservice.service.SignatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.gr.kolychev.signatureproviderservice.TestConstants.GET_SIGNATURE_URL;
import static com.gr.kolychev.signatureproviderservice.TestConstants.INVALID_HEADER_VALUE;
import static com.gr.kolychev.signatureproviderservice.TestConstants.OPERATION_ID;
import static com.gr.kolychev.signatureproviderservice.TestConstants.PARAMS;
import static com.gr.kolychev.signatureproviderservice.TestConstants.PARAM_1;
import static com.gr.kolychev.signatureproviderservice.TestConstants.PARAM_1_VALUE;
import static com.gr.kolychev.signatureproviderservice.TestConstants.SIGNATURE_STRING;
import static com.gr.kolychev.signatureproviderservice.TestConstants.TOKEN_HEADER;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignatureController.class)
@TestPropertySource("classpath:test.properties")
class SignatureControllerMvcTest {

    private static final String EXPECTED_OK_CONTENT = """
            {
                "status": "SUCCESS",
                "result": [
                    {
                        "signature": "Ix1lkuuf9YsdhzNQVXpevVLPCV167z+A9T3uGrGBQ6s="
                    }
                ]
            }
            """;
    private static final String EXPECTED_MISSING_TOKEN_CONTENT = """
            {
                "status": "ERROR",
                "result": "Missing Token header in the request"
            }
            """;
    private static final String EXPECTED_INVALID_TOKEN_CONTENT = """
            {
                "status": "FAILED",
                "result": "Provided token is invalid."
            }
            """;
    private static final String EXPECTED_MISSING_PARAMETERS_CONTENT = """
            {
                "status": "ERROR",
                "result": [
                    "Missing request parameters"
                ]
            }
            """;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SignatureService hmacSha256SignatureService;

    @Value("${security.authentication.valid-token}")
    private String token;

    @Test
    void testCreateSignatureShouldReturnOkAndProperContent() throws Exception {
        //given
        when(hmacSha256SignatureService.createSignature(OPERATION_ID, PARAMS))
                .thenReturn(new Signature(SIGNATURE_STRING));
        //when-then
        mockMvc.perform(post(GET_SIGNATURE_URL + OPERATION_ID)
                        .param(PARAM_1, PARAM_1_VALUE)
                        .header(TOKEN_HEADER, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(EXPECTED_OK_CONTENT));
    }

    @Test
    void testCreateSignatureShouldReturn403WhenMissingToken() throws Exception {
        //given
        when(hmacSha256SignatureService.createSignature(OPERATION_ID, PARAMS))
                .thenReturn(new Signature(SIGNATURE_STRING));
        //when-then
        mockMvc.perform(post(GET_SIGNATURE_URL + OPERATION_ID)
                        .param(PARAM_1, PARAM_1_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json(EXPECTED_MISSING_TOKEN_CONTENT));
    }

    @Test
    void testCreateSignatureShouldReturn403WhenInvalidToken() throws Exception {
        //given
        when(hmacSha256SignatureService.createSignature(OPERATION_ID, PARAMS))
                .thenReturn(new Signature(SIGNATURE_STRING));
        //when-then
        mockMvc.perform(post(GET_SIGNATURE_URL + OPERATION_ID)
                        .param(PARAM_1, PARAM_1_VALUE)
                        .header(TOKEN_HEADER, INVALID_HEADER_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().json(EXPECTED_INVALID_TOKEN_CONTENT));
    }

    @Test
    void testCreateSignatureShouldReturn400WhenMissingParameters() throws Exception {
        //given
        when(hmacSha256SignatureService.createSignature(OPERATION_ID, PARAMS))
                .thenReturn(new Signature(SIGNATURE_STRING));
        //when-then
        mockMvc.perform(post(GET_SIGNATURE_URL + OPERATION_ID)
                        .header(TOKEN_HEADER, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(EXPECTED_MISSING_PARAMETERS_CONTENT));
    }
}
