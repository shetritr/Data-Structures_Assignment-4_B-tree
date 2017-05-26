import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

/// DO NOT CHANGE
/// DO NOT SUBMIT

/**
 * 
 * A Block is a part of a larger file, to be stored in B-Tree nodes.
 */
public class Block {

	public static int BLOCK_SIZE = 200;

	private int key;
	private byte[] data;

	public Block(int key, byte[] data) {
		super();
		this.key = key;
		this.data = data;
	}

	public int getKey() {
		return key;
	}

	public byte[] getData() {
		return data;
	}

	/**
	 * Generates a sequence of blocks with random data. With keys running from
	 * fromKey to toKey, inclusive.
	 * 
	 * @param fromKey
	 * @param toKey
	 * @return ArrayList of blocks
	 */
	public static ArrayList<Block> blockFactory(int fromKey, int toKey) {
		SecureRandom random = new SecureRandom();
		ArrayList<Block> blocks = new ArrayList<Block>(toKey - fromKey + 1);
		for (int i = fromKey; i <= toKey; i++) {
			BigInteger bi = new BigInteger(BLOCK_SIZE * 8, random);
			byte[] data = bi.toByteArray();
			blocks.add(new Block(i, data));
		}
		return blocks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + key;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (key != other.key)
			return false;
		if (!Arrays.equals(data, other.data))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Block [key=" + key + "]";
		//return "Block [key=" + key + ", data=" + Arrays.toString(data) + "]";
	}
	
	

}
