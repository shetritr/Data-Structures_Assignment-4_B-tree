import java.util.ArrayList;

//DO NOT SUBMIT
public class Tests {

	private static boolean testLeafMerkleNodeAndLeafDelete(){	
		MerkleBNode mbt, mbtExpected;
		try {
			BTree tree = new BTree(3);
			ArrayList<Block> blocks = Block.blockFactory(11, 14);
			tree.insert(blocks.get(1));
			tree.insert(blocks.get(2));
			tree.insert(blocks.get(0));

			tree.insert(blocks.get(3));//also test delete from leaf
			tree.delete(blocks.get(3).getKey());

			mbt = tree.createMBT();
			
			ArrayList<byte[]> hashInput = new ArrayList<byte[]>();
			hashInput.add(blocks.get(0).getData());
			hashInput.add(blocks.get(1).getData());
			hashInput.add(blocks.get(2).getData());
			byte[] hashValue = HashUtils.sha1Hash(hashInput);
			mbtExpected = new MerkleBNode(hashValue);
			
			if (!mbt.equals(mbtExpected)) {
				System.out.println("Test: testLeafMerkleNode Failed");
				return false;
			}
			System.out.println("Test: testLeafMerkleNode Passed");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test: testLeafMerkleNode Failed");
			return false;
		}
	}
	
	public static void main(String[] args) {
		testLeafMerkleNodeAndLeafDelete();
	}
}

