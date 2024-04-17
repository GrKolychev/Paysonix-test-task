package com.gr.kolychev.signatureproviderservice.controller;

import com.gr.kolychev.signatureproviderservice.model.GenericResponse;
import com.gr.kolychev.signatureproviderservice.model.RequestStatus;
import com.gr.kolychev.signatureproviderservice.model.Signature;
import com.gr.kolychev.signatureproviderservice.service.SignatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.gr.kolychev.signatureproviderservice.TestConstants.OPERATION_ID;
import static com.gr.kolychev.signatureproviderservice.TestConstants.PARAMS;
import static com.gr.kolychev.signatureproviderservice.TestConstants.SIGNATURE_STRING;
import static com.gr.kolychev.signatureproviderservice.TestConstants.TOKEN_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignatureControllerIntegrationTest {

    @Mock
    private SignatureService service;

    private SignatureController controller;

    @BeforeEach
    void setUp() {
        controller = new SignatureController(service);
    }

    @Test
    void testGetSignature() {
        //given
        var expectedResult = GenericResponse.<List<Signature>>builder()
                .status(RequestStatus.SUCCESS)
                .result(List.of(new Signature(SIGNATURE_STRING)))
                .build();

        when(service.getSignature(OPERATION_ID, PARAMS)).thenReturn(new Signature(SIGNATURE_STRING));
        //when
        var response = controller.getSignature(OPERATION_ID, PARAMS, TOKEN_HEADER);
        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResult, response.getBody());
        verify(service, times(1)).getSignature(OPERATION_ID, PARAMS);
        verifyNoMoreInteractions(service);
    }
}
