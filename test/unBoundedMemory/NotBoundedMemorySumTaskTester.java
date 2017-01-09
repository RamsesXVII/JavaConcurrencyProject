package unBoundedMemory;

import java.util.Deque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

import processor.FakeProcessor;
import tree.BinaryTreeVisitUtility;
import tree.Node;

public class NotBoundedMemorySumTaskTester implements Callable<Integer> {
	private Deque<Node> nodesQueue;
	private CyclicBarrier barrier;
	private FakeProcessor processor;
	private BinaryTreeVisitUtility visitStatus;
	private int visitedNodecount;
	private LinkedBlockingQueue<Integer>listCountNodesVisited;

	public NotBoundedMemorySumTaskTester(Deque<Node> nodesQueue, 
			CyclicBarrier barrier, BinaryTreeVisitUtility visitStatus,LinkedBlockingQueue<Integer>listCountNodesVisited) {
	
		this.nodesQueue=nodesQueue;
		this.barrier=barrier;
		this.processor=new FakeProcessor(1000);
		this.visitStatus=visitStatus;
		this.setVisitedNodecount(0);
		this.listCountNodesVisited=listCountNodesVisited;

	}

	
	@Override
	public Integer call() {
		Integer result= new Integer(0);

		while(!this.visitStatus.isCompleted()){
			result+=new Integer(this.extractNewValue());}

		this.listCountNodesVisited.add(new Integer(this.visitedNodecount));

		return result;
	}

	private int extractNewValue() {

		Node extractedNode=null;

		try {
			extractedNode = this.nodesQueue.pollFirst();

			if(extractedNode!=null){
				this.visitedNodecount++;
				
				if(extractedNode.getSx()!=null)
					this.nodesQueue.addLast(extractedNode.getSx());
				if(extractedNode.getDx()!=null)
					this.nodesQueue.addLast(extractedNode.getDx());

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

	public int getVisitedNodecount() {
		return visitedNodecount;
	}

	public void setVisitedNodecount(int visitedNodecount) {
		this.visitedNodecount = visitedNodecount;
	}

}
