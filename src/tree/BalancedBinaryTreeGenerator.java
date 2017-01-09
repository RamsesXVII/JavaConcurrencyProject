package tree;


public class BalancedBinaryTreeGenerator implements BinaryTreeGenerator {

	@Override
	public Node buildTree(int height) {

		if(height==0)
			return null;

		else{
			BinaryTreeNode node= new BinaryTreeNode();
			node.setValue(height);

			node.setDx(buildTree(height-1));
			node.setSx(buildTree(height-1));
			
			return node;
		}}

}
