
/// DO NOT CHANGE
/// DO NOT SUBMIT
public interface BTreeInterface {
	
	/**
	 * @return the root BNode of the tree.
	 */
	public abstract BNode getRoot();
	
	/**
	 * @return the t constant of the tree
	 */
	public abstract int getT();

	/**
	 * Search a block by its key. 
	 * @param key 
	 * @return the block if found, null otherwise.
	 */
	public abstract Block search(int key);

	/**
	 * Insert a new block
	 * @param b
	 */
	public abstract void insert(Block b);
	/**
	 * Delete a block by its key. 
	 * @param key
	 */
	public abstract void delete(int key);

	/**
	 * Computes the complete Merkle-B-Tree of this B-Tree
	 * @return the root of MBT. 
	 */
	public abstract MerkleBNode createMBT();


}