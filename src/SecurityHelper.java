import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityHelper {

	/**
	 * Generates a SHA-256 hash of the input string.
	 * Used primarily for securely storing software license keys.
	 * The hash is returned as a lowercase hexadecimal string.
	 * 
	 * @param input The string to hash (e.g., a license key)
	 * @return A 64-character hexadecimal string representing the SHA-256 hash
	 * @throws RuntimeException If the SHA-256 algorithm is not available on this system
	 */
    public static String sha256_hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(String.format("%02x", digest[i]));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}

