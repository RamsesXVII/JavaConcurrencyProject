package adder;
import processor.FakeProcessor;
import processor.OnerousProcessor;
import tree.Node;


public class SerialBinaryTreeAdder implements BinaryTreeAdder {

	private final OnerousProcessor onerousProcessor = new FakeProcessor(1000);

	@Override
	public int computeOnerousSum(Node root) {

		int sum=0;
		if(root!=null){
			sum = onerousProcessor.onerousFunction(root.getValue());

			if(root.getDx() != null)
				sum+=computeOnerousSum(root.getDx());
			if(root.getSx() != null)
				sum+=computeOnerousSum(root.getSx());
		}
		return sum;	
	}
}