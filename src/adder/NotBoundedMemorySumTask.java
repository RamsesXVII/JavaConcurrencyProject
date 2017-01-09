package adder;

import java.util.Deque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import processor.FakeProcessor;
import tree.BinaryTreeVisitUtility;
import tree.Node;

public class NotBoundedMemorySumTask implements Callable<Integer> {
	private Deque<Node> nodesQueue;
	private CyclicBarrier barrier;
	private FakeProcessor processor;
	private BinaryTreeVisitUtility visitStatus;


	public NotBoundedMemorySumTask(Deque<Node> nodesQueue, CyclicBarrier barrier, BinaryTreeVisitUtility visitStatus) {
		this.nodesQueue=nodesQueue;
		this.barrier=barrier;
		this.processor=new FakeProcessor(500);
		this.visitStatus=visitStatus;
	}

	@Override
	public Integer call() {
		Integer result= new Integer(0);

		while(!this.visitStatus.isCompleted()){
			result+=this.extractNewValue();}

		return result;
	}

	private int extractNewValue() {

		Node extractedNode=null;

		try {
			extractedNode = this.nodesQueue.pollFirst();
			boolean toReset=false;

			if(extractedNode!=null){

				if(extractedNode.getSx()!=null){
					toReset=true;
					this.nodesQueue.addLast(extractedNode.getSx());}

				if(extractedNode.getDx()!=null){
					toReset=true;
					this.nodesQueue.addLast(extractedNode.getDx());}

				if(toReset)
					this.barrier.reset();

				return this.processor.onerousFunction(extractedNode.getValue());}

			else{				

				if(this.barrier.getParties()-this.barrier.getNumberWaiting()==1) 
					this.visitStatus.setIsCompleted(true);

				this.barrier.await();}

		} catch (InterruptedException e) {
			e.printStackTrace();

		} catch (BrokenBarrierException e) {
			return 0;}

		return 0;
	}


}
