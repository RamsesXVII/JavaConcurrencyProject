package adder;

import java.util.concurrent.RecursiveTask;

import processor.FakeProcessor;
import processor.OnerousProcessor;
import tree.BinaryTreeVisitUtility;
import tree.Node;

public class ForkJoinSumTask extends RecursiveTask<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Node root;
	private int SEQUENTIAL_TRESHOLD=128;
	private final OnerousProcessor onerousProcessor = new FakeProcessor(1000);


	public ForkJoinSumTask(Node root){
		this.root=root;
	}

	@Override
	protected Integer compute() {

		BinaryTreeVisitUtility bUtility=new BinaryTreeVisitUtility();
		
		if(bUtility.getNumberofNodes(root)<this.SEQUENTIAL_TRESHOLD)
			return this.extractTreeNodesSum(this.root);
		
		else{
			Integer sum=this.onerousProcessor.onerousFunction(this.root.getValue());
			
			ForkJoinSumTask dx=new ForkJoinSumTask(root.getDx());
			ForkJoinSumTask sx= new ForkJoinSumTask(root.getSx());

			sx.fork();
			
			Integer sumDx=dx.compute();
			Integer sumSX=sx.join();
			
			return sumDx+sumSX+sum;
		}
	}

	private int extractTreeNodesSum(Node root2) {
		int sum=0;
		if(root2!=null){
		 sum = onerousProcessor.onerousFunction(root2.getValue());

		if(root2.getDx() != null)
			sum+=extractTreeNodesSum(root2.getDx());
		if(root2.getSx() != null)
			sum+=extractTreeNodesSum(root2.getSx());
		}
		return sum;	
		
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}
