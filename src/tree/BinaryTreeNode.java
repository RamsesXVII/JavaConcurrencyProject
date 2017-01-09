package tree;

public class BinaryTreeNode implements Node{
	
	private Node Sx;
	private Node Dx;
	private int Value;
	
	@Override
	public Node getSx() {
		return this.Sx;
	}
	
	@Override
	public Node getDx() {
		return this.Dx;

	}
	@Override
	public int getValue() {
		return this.Value;

	}
	
	public void setSx(Node sx){
		this.Sx=sx;
	}
	
	public void setDx(Node dx) {
		this.Dx = dx;
	}
	public void setValue(int value) {
		this.Value = value;
	}

	@Override
	public String toString() {
		return "BinaryTreeNode [Sx=" + Sx + ", Dx=" + Dx + ", Value=" + Value
				+ "]";
	}

}
