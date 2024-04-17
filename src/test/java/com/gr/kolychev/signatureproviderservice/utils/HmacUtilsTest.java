package com.gr.kolychev.signatureproviderservice.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.HmacUtils;

import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HmacUtilsTest {

    @ParameterizedTest
    @MethodSource("successfulScenarios")
    void testCalculateHmacShouldSucceed(String algorithm, String data, String secretKey, String expectedHmac) {

        //when
        String resultHmac = HmacUtils.calculateHMAC(algorithm, data, secretKey);

        //then
        assertEquals(expectedHmac, resultHmac);
    }

    @ParameterizedTest(name = "Test unsuccessful scenario for algorithm: {0}")
    @MethodSource("unsuccessfulScenarios")
    void testCalculateHmacShouldThrowException(String algorithm, String data, String secretKey,
                                               Class<? extends Throwable> expectedEx) {
        //assert
        assertThrows(expectedEx, () -> {
            //when
            HmacUtils.calculateHMAC(algorithm, data, secretKey);
        });
    }

    private static Stream<Arguments> successfulScenarios() {
        return Stream.of(
                Arguments.of("HmacSHA256", "data", "secretKey",
                        "FjdxjqxEkJN/kzJv+n7RaBaPDbDupNIdbZm07hDo+i8="),
                Arguments.of("HmacSHA512", "data", "secretKey",
                        "O3uTT04zlisqfd41G1DXoXfBkvK1sw1v1UP9qmRfv+8eXNGka483hfbO6p1yES7lZU0+dKo4dcdlJSI9NcePQg==")
        );
    }

    private static Stream<Arguments> unsuccessfulScenarios() {
        return Stream.of(
                Arguments.of("InvalidAlgorithm", "data", "secretKey", NoSuchAlgorithmException.class),
                Arguments.of("HmacSHA256", null, "secretKey", NullPointerException.class),
                Arguments.of("HmacSHA256", "data", null, NullPointerException.class),
                Arguments.of("HmacSHA256", "data", "", IllegalArgumentException.class)
        );
    }
}
