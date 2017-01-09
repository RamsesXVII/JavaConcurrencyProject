package speedupViz;

import adder.BinaryTreeAdder;
import tree.Node;

public class MethodsExcecutor {

	public double getExcecutionTimeOfcomputeOnerousSumMethod(BinaryTreeAdder a, Node root){

		long start=(long)System.nanoTime();
		a.computeOnerousSum(root);
		long end=(long)System.nanoTime();
		return (double)(end-start);

	}

}
