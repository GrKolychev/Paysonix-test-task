package com.gr.kolychev.signatureproviderservice;

import com.gr.kolychev.signatureproviderservice.model.Signature;

import java.util.Map;

public class TestConstants {
    public static final String PARAMS_KEY_VALUE_DELIMITER = "=";
    public static final String PARAMS_DELIMITER = "&";
    public static final String GET_SIGNATURE_URL = "/api/v1/signatures/";
    public static final String OPERATION_ID = "1";
    public static final String PARAM_1 = "name1";
    public static final String PARAM_1_VALUE = "value1";
    public static final String PARAM_2 = "name2";
    public static final String PARAM_2_VALUE = "value2";
    public static final String TOKEN_HEADER = "Token";
    public static final String INVALID_HEADER_VALUE = "invalid";
    public static final String SIGNATURE_STRING = "Ix1lkuuf9YsdhzNQVXpevVLPCV167z+A9T3uGrGBQ6s=";
    public static final Signature SIGNATURE = new Signature(SIGNATURE_STRING);
    public static final Map<String, String> PARAMS = Map.of(PARAM_1, PARAM_1_VALUE);
    public static final Map<String, String> PARAMS_2 = Map.of(PARAM_2, PARAM_2_VALUE, PARAM_1, PARAM_1_VALUE);
    public static final String DATA = PARAM_1 + PARAMS_KEY_VALUE_DELIMITER + PARAM_1_VALUE + PARAMS_DELIMITER
            + PARAM_2 + PARAMS_KEY_VALUE_DELIMITER + PARAM_2_VALUE;
    public static final String ALGORITHM = "HmacSHA256";
    public static final String SECRET_KEY = "Secret_key";

}
