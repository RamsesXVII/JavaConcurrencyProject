package tree;

public class UnbalancedBinaryTreeGenerator  implements BinaryTreeGenerator  {
	private String type;

	public UnbalancedBinaryTreeGenerator(String TreeType) {
		this.type=TreeType;		
	}


	public UnbalancedBinaryTreeGenerator() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public Node buildTree(int height) {
		if(type.equals("left"))
			return this.builtUnbalancedLeft(height);
		if(type.equals("right"))
			return this.builtUnbalancedRight(height);
		else
			return this.builtUnbalancedRandom(height);
	}


	private Node builtUnbalancedRandom(int height) {
		if(height==0)
			return null;

		else{
			BinaryTreeNode node= new BinaryTreeNode();
			node.setValue(height);

			int toGenerateRight=(Math.random()<0.5)?0:1;
			int toGenerateLeft=(Math.random()<0.5)?0:1;


			if(toGenerateRight==1)
				node.setDx(buildTree(height-1));

			if(toGenerateLeft==1)
				node.setSx(buildTree(height-1));

			if((toGenerateLeft==toGenerateRight)&&toGenerateLeft==0){
				toGenerateRight=(Math.random()<0.5)?0:1;
				if(toGenerateRight==1)
					node.setDx(buildTree(height-1));
				else
					node.setSx(buildTree(height-1));
			}

			return node;
		}}


	private Node builtUnbalancedRight(int height) {
		if(height==0)
			return null;

		else{
			BinaryTreeNode node= new BinaryTreeNode();
			node.setValue(height);

			node.setDx(buildTree(height-1));

			return node;
		}}


	private Node builtUnbalancedLeft(int height) {
		if(height==0)
			return null;

		else{
			BinaryTreeNode node= new BinaryTreeNode();
			node.setValue(height);

			node.setSx(buildTree(height-1));

			return node;
		}}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public void setLeftModeGeneration(){
		this.type="left";
	}

	public void setRightModeGeneration(){
		this.type="right";
	}

	public void setRandomModeGeneration(){
		this.type="random";
	}

}
