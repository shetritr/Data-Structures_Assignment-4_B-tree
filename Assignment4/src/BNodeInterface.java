import java.util.ArrayList;
/// DO NOT CHANGE
/// DO NOT SUBMIT
public interface BNodeInterface {

	public abstract int getT();

	public abstract int getNumOfBlocks();

	public abstract boolean isLeaf();

	public abstract ArrayList<Block> getBlocksList();

	public abstract ArrayList<BNode> getChildrenList();

	
	public abstract int getBlockKeyAt(int indx);
	
	
	public abstract Block getBlockAt(int indx);
	
	
	public abstract BNode getChildAt(int indx);
	
	/**
	 * True if and only if the numOfBlocks is maximal, (2t-1).
	 */
	public abstract boolean isFull();
	/**
	 * True if and only if the numOfBlocks is minimal, (t-1).
	 */
	public abstract boolean isMinSize();

	/**
	 * True if and only if the numOfBlocks is 0.
	 */
	public abstract boolean isEmpty();
	
	/**
	 * Search a block by its key, in the subtree rooted by this node.
	 * @param key 
	 * @return the block if found, null otherwise.
	 */
	public abstract Block search(int key);
	
	/**
	 * Insert a new block to the subtree rooted by this node.<br>
	 * Assuming this node is not full. 
	 * @param b the new block.
	 */
	public abstract void insertNonFull(Block d);

	/**
	 * Delete a block by its key, in the subtree rooted by this node.
	 * @param key 
	 * @return the block if found, null otherwise.
	 */
	public abstract void delete(int key);
	
	/**
	 * @return the corresponding Merkle-B-Tree node of this BNode.
	 */
	public abstract MerkleBNode createHashNode();

}