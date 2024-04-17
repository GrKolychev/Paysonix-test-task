package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HmacUtils {

    /**
     * Calculates the HMAC (Hash-based Message Authentication Code) for the given data using the specified
     * algorithm and secret key.
     *
     * @param algorithm {@link String} The hashing algorithm to use for calculating HMAC, e.g., "HmacSHA256".
     * @param data      {@link String} The data for which to calculate the HMAC.
     * @param secretKey {@link String} The secret key used for the HMAC calculation.
     * @return The calculated HMAC as a Base64-encoded string.
     * @throws NoSuchAlgorithmException If the specified algorithm is not available in the environment.
     * @throws InvalidKeyException      If the provided secret key is not valid for the specified algorithm.
     */
    @SneakyThrows({NoSuchAlgorithmException.class, InvalidKeyException.class})
    public static String calculateHMAC(String algorithm, String data, String secretKey) {
        log.info("Calculating HMAC for");
        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), algorithm);
        mac.init(keySpec);

        byte[] hash = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
