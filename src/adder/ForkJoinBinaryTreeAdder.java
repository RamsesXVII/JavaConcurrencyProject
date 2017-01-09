package adder;

import java.util.concurrent.ForkJoinPool;

import tree.Node;

public class ForkJoinBinaryTreeAdder implements BinaryTreeAdder {


	@Override
	public int computeOnerousSum(Node root) {
		final ForkJoinPool pool = new ForkJoinPool(); 
		Integer sum=0;

		try { 
			sum=pool.invoke(new ForkJoinSumTask(root));  
		} finally { 
			pool.shutdown();}

		return sum;
	}

}
