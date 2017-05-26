import java.util.ArrayList;
import java.util.Arrays;

/// DO NOT CHANGE
/// DO NOT SUBMIT

/**
 * Merkle-B-Tree node. <br>
 * Contains the signature of B-Node, and a childrenList of other MBT node.
 */
public class MerkleBNode {

	private byte[] hashValue;
	private boolean isLeaf;
	private ArrayList<MerkleBNode> childrenList;

	public MerkleBNode(byte[] hashValue, boolean isLeaf,
			ArrayList<MerkleBNode> childrenList) {
		this.hashValue = hashValue;
		this.isLeaf = isLeaf;
		this.childrenList = childrenList;
	}

	public MerkleBNode(byte[] hashValue, ArrayList<MerkleBNode> childrenList) {
		this(hashValue, false, childrenList);
	}

	/**
	 * Constructs a MBT leaf node.
	 */
	public MerkleBNode(byte[] hashValue) {
		this(hashValue, true, new ArrayList<MerkleBNode>());

	}

	public byte[] getHashValue() {
		return hashValue;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public ArrayList<MerkleBNode> getChildrenList() {
		return childrenList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((childrenList == null) ? 0 : childrenList.hashCode());
		result = prime * result + Arrays.hashCode(hashValue);
		result = prime * result + (isLeaf ? 1231 : 1237);
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
		MerkleBNode other = (MerkleBNode) obj;
		if (childrenList == null) {
			if (other.childrenList != null)
				return false;
		} else if (!childrenList.equals(other.childrenList))
			return false;
		if (!Arrays.equals(hashValue, other.hashValue))
			return false;
		if (isLeaf != other.isLeaf)
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "MerkleBNode [hashValue=" + Arrays.toString(hashValue)
				+ ", isLeaf=" + isLeaf + ", childrenList=" + childrenList + "]";
	}
	
	
	
}
