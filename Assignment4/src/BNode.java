import java.nio.channels.NetworkChannel;
import java.util.ArrayList;
import java.util.Iterator;

//SUBMIT
public class BNode implements BNodeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private final int t;
	private int numOfBlocks;
	private boolean isLeaf;
	private ArrayList<Block> blocksList;
	private ArrayList<BNode> childrenList;

	/**
	 * Constructor for creating a node with a single child.<br>
	 * Useful for creating a new root.
	 */
	public BNode(int t, BNode firstChild) {
		this(t, false, 0);
		this.childrenList.add(firstChild);
	}

	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	public BNode(int t, Block firstBlock) {
		this(t, true, 1);
		this.blocksList.add(firstBlock);
	}

	public BNode(int t, boolean isLeaf, int numOfBlocks) {
		this.t = t;
		this.isLeaf = isLeaf;
		this.numOfBlocks = numOfBlocks;
		this.blocksList = new ArrayList<Block>();
		this.childrenList = new ArrayList<BNode>();
	}

	// For testing purposes.
	public BNode(int t, int numOfBlocks, boolean isLeaf,
			ArrayList<Block> blocksList, ArrayList<BNode> childrenList) {
		this.t = t;
		this.numOfBlocks = numOfBlocks;
		this.isLeaf = isLeaf;
		this.blocksList = blocksList;
		this.childrenList = childrenList;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public ArrayList<Block> getBlocksList() {
		return blocksList;
	}

	@Override
	public ArrayList<BNode> getChildrenList() {
		return childrenList;
	}

	@Override
	public boolean isFull() {
		return numOfBlocks == 2 * t - 1;
	}

	@Override
	public boolean isMinSize() {
		return numOfBlocks == t - 1;
	}
	
	@Override
	public boolean isEmpty() {
		return numOfBlocks == 0;
	}
	
	@Override
	public int getBlockKeyAt(int indx) {
		return blocksList.get(indx).getKey();
	}
	
	@Override
	public Block getBlockAt(int indx) {
		return blocksList.get(indx);
	}

	@Override
	public BNode getChildAt(int indx) {
		return childrenList.get(indx);
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blocksList == null) ? 0 : blocksList.hashCode());
		result = prime * result
				+ ((childrenList == null) ? 0 : childrenList.hashCode());
		result = prime * result + (isLeaf ? 1231 : 1237);
		result = prime * result + numOfBlocks;
		result = prime * result + t;
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
		BNode other = (BNode) obj;
		if (blocksList == null) {
			if (other.blocksList != null)
				return false;
		} else if (!blocksList.equals(other.blocksList))
			return false;
		if (childrenList == null) {
			if (other.childrenList != null)
				return false;
		} else if (!childrenList.equals(other.childrenList))
			return false;
		if (isLeaf != other.isLeaf)
			return false;
		if (numOfBlocks != other.numOfBlocks)
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BNode [t=" + t + ", numOfBlocks=" + numOfBlocks + ", isLeaf="
				+ isLeaf + ", blocksList=" + blocksList + ", childrenList="
				+ childrenList + "]";
	}

	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	
	
	
	@Override
	public Block search(int key) {//by class
		int i=0;
		Iterator it=blocksList.iterator();//Iteration 
		Block block = (Block) it.next();
		while (it.hasNext() & block.getKey()<key) {//check where is the the key
			block=(Block) it.next();
			i++;//add 1 to the counting
		}
		if(it.hasNext()&block.getKey()==key)
			return block;
		else if(isLeaf)
			return null;
		else
			return childrenList.get(i).search(key);
	}

	@Override
	public void insertNonFull(Block d) {
		int i;
		for ( i = numOfBlocks-1; i >= 0&& d.getKey()<blocksList.get(i).getKey(); i--);//found the right index
		i++;
		if(isLeaf){
			blocksList.add(i, d);//use Array list function 
			numOfBlocks++;
		}else{
			if(childrenList.get(i).numOfBlocks==2*t-1){
				splitChild(i);
				if(d.getKey()>blocksList.get(i).getKey())
					i++;
			}
			childrenList.get(i).insertNonFull(d);
		}
		
	}

	@Override
	public void delete(int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MerkleBNode createHashNode() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	* Splits the child node at childIndex into 2 nodes.
	* @param childIndex
	*/
	//done
	public void splitChild(int childIndex){
		BNode y=childrenList.get(childIndex);
		BNode z=new BNode(t, y.isLeaf, t-1);
		for (int i = 0; i < t-1; i++) {
			z.blocksList.add(y.blocksList.remove(t));
			y.numOfBlocks--;
			}
		if(!y.isLeaf){
			for (int i = 0; i < t; i++) {
				z.childrenList.add(y.childrenList.remove(t));
			}
		}
		childrenList.add(childIndex+1, z);
		blocksList.add(childIndex, y.blocksList.remove(t));
		y.numOfBlocks--;
		numOfBlocks++;
	}
	/**
	* True iff the child node at childIndx-1 exists and has more than t-1 blocks.
	* @param childIndx
	* @return
	*/
	private boolean childHasNonMinimalLeftSibling(int childIndx){
		return false;
		}
	/**
	* True iff the child node at childIndx+1 exists and has more than t-1 blocks.
	* @param childIndx
	* @return
	*/
	private boolean childHasNonMinimalRightSibling(int childIndx){
		return isLeaf;
		}
	/**
	* Verifies the child node at childIndx has at least t blocks.<br>
	* If necessary a shift or merge is performed.
	*
	* @param childIndxxs
	*/
	private void shiftOrMergeChildIfNeeded(int childIndx){}
	/**
	* Add additional block to the child node at childIndx, by shifting from left sibling.
	* @param childIndx
	*/
	private void shiftFromLeftSibling(int childIndx){}
	/**
	* Add additional block to the child node at childIndx, by shifting from right sibling.
	* @param childIndx
	*/
	private void shiftFromRightSibling(int childIndx){}
	/**
	* Merges the child node at childIndx with its left or right sibling.
	* @param childIndx
	*/
	private void mergeChildWithSibling(int childIndx){}
	/**
	* Merges the child node at childIndx with its left sibling.<br>
	* The left sibling node is removed.
	* @param childIndx
	*/
	private void mergeWithLeftSibling(int childIndx){}
	/**
	* Merges the child node at childIndx with its right sibling.<br>
	* The right sibling node is removed.
	* @param childIndx
	*/
	private void mergeWithRightSibling(int childIndx){}
	/**
	* Finds and returns the block with the min key in the subtree.
	* @return min key block
	*/
	private Block getMinKeyBlock(){
		return null;
		}
	/**
	* Finds and returns the block with the max key in the subtree.
	* @return max key block
	*/
	private Block getMaxKeyBlock(){
		return null;
		}
	

}