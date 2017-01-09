package forkJoin;

import java.util.concurrent.ForkJoinPool;

import adder.BinaryTreeAdder;
import tree.Node;

public class ForkJoinBinaryTreeAdderTester implements BinaryTreeAdder {

	@Override
	public int computeOnerousSum(Node root) {
		final ForkJoinPool pool = new ForkJoinPool(); 
		Integer sum=0;
		
		try { 
			sum=pool.invoke(new ForkJoinSumTaskTester(root));  
		} finally { 
			pool.shutdown();}
		
		return sum;
	}

}
