package com.gr.kolychev.signatureproviderservice.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import util.HmacUtils;

import static com.gr.kolychev.signatureproviderservice.TestConstants.ALGORITHM;
import static com.gr.kolychev.signatureproviderservice.TestConstants.DATA;
import static com.gr.kolychev.signatureproviderservice.TestConstants.OPERATION_ID;
import static com.gr.kolychev.signatureproviderservice.TestConstants.SECRET_KEY;
import static com.gr.kolychev.signatureproviderservice.TestConstants.SIGNATURE;
import static com.gr.kolychev.signatureproviderservice.TestConstants.SIGNATURE_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SignatureProcessorIntegrationTest {

    private SignatureProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new SignatureProcessor();
    }

    @Test
    void testCreateSignatureShouldRunSuccessfully() {
        try (MockedStatic<HmacUtils> hmacUtilsMocked = Mockito.mockStatic(HmacUtils.class)) {
            //given
            hmacUtilsMocked.when(() -> HmacUtils.calculateHMAC(ALGORITHM, DATA, SECRET_KEY))
                    .thenReturn(SIGNATURE_STRING);
            ReflectionTestUtils.setField(processor, "secretKey", SECRET_KEY);
            //when
            var result = processor.createSignature(OPERATION_ID, DATA);
            //then
            assertNotNull(result);
            assertEquals(SIGNATURE, result);
            hmacUtilsMocked.verify(() -> HmacUtils.calculateHMAC(ALGORITHM, DATA, SECRET_KEY), times(1));
            hmacUtilsMocked.verifyNoMoreInteractions();
        }
    }
}
