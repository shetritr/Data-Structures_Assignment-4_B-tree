import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/// DO NOT CHANGE
/// DO NOT SUBMIT
public class HashUtils {

	/**
	 * Concatenates the given data and hashes it with SHA1
	 * @param dataList an ArrayList of byte arrays (byte[]) containing the data to be concatenated and hashed.
	 * @return 20 byte fixed-length hash value.
	 */
	public static byte[] sha1Hash(ArrayList<byte[]> dataList) {
		MessageDigest crypt = null;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		crypt.reset();
		for (byte[] data : dataList){
			crypt.update(data);
		}
	    return crypt.digest();
	}
}
