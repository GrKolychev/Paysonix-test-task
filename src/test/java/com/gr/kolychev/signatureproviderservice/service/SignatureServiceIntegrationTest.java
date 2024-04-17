package com.gr.kolychev.signatureproviderservice.service;

import com.gr.kolychev.signatureproviderservice.model.Signature;
import com.gr.kolychev.signatureproviderservice.processor.SignatureProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gr.kolychev.signatureproviderservice.TestConstants.DATA;
import static com.gr.kolychev.signatureproviderservice.TestConstants.OPERATION_ID;
import static com.gr.kolychev.signatureproviderservice.TestConstants.PARAMS_2;
import static com.gr.kolychev.signatureproviderservice.TestConstants.SIGNATURE;
import static com.gr.kolychev.signatureproviderservice.TestConstants.SIGNATURE_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignatureServiceIntegrationTest {

    @Mock
    private SignatureProcessor processor;

    private SignatureService service;

    @BeforeEach
    void setUp() {
        service = new SignatureService(processor);
    }

    @Test
    void testGetSignature() {
        //given
        when(processor.createSignature(OPERATION_ID, DATA)).thenReturn(new Signature(SIGNATURE_STRING));
        //when
        var resultSignature = service.getSignature(OPERATION_ID, PARAMS_2);
        //then
        assertNotNull(resultSignature);
        assertEquals(SIGNATURE, resultSignature);
        verify(processor, times(1)).createSignature(OPERATION_ID, DATA);
        verifyNoMoreInteractions(processor);
    }
}
