package tree;

public class BinaryTreeVisitUtility {

	private boolean visitCompleted;


	public BinaryTreeVisitUtility(){}

	public BinaryTreeVisitUtility(boolean value){
		this.visitCompleted=value;
	}

	public boolean getValue() {
		return visitCompleted;
	}

	public void setIsCompleted(boolean value) {
		this.visitCompleted = value;
	}

	public boolean isCompleted(){
		return this.visitCompleted;
	}

	public int getNumberofNodes(Node root){
		if(root==null)
			return 0;
		else{
			Node right=root.getDx();
			Node left=root.getSx();

			int c=1;
			if(left!=null)
				c+=getNumberofNodes(left);
			if(right!=null)
				c+=getNumberofNodes(right);

			return c;
		}

	}
	

	public int getTreeHeight(Node root){
		if(root==null)
			return 0;
		
		else{
			return 1+Math.max(getTreeHeight(root.getSx()), getTreeHeight(root.getDx()));
		}
					
	}

}
