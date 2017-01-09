package boundedMemory;

import java.util.Deque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import processor.FakeProcessor;
import tree.BinaryTreeVisitUtility;
import tree.Node;

public class BoundedMemorySumTaskTester implements Callable<Integer> {
	private LinkedBlockingQueue<Node> initialNode;
	private CyclicBarrier barrier;
	private FakeProcessor processor;
	private BinaryTreeVisitUtility visitStatus;
	private int visitedNodescount;
	private int treeHeight;
	private Deque<Node>myNodesToVisit;
	private LinkedBlockingQueue<Integer>listCountNodesVisited;
	private LinkedBlockingQueue<Deque<Node>> threadDequesList;

	public BoundedMemorySumTaskTester(LinkedBlockingQueue<Node> initialNode, CyclicBarrier barrier,
			BinaryTreeVisitUtility visitStatus,LinkedBlockingQueue<Deque<Node>> threadDequesList,
			int treeHeight,LinkedBlockingQueue<Integer>listCountNodesVisited) {

		this.initialNode=initialNode;
		this.barrier=barrier;
		this.processor=new FakeProcessor(1000);
		this.visitStatus=visitStatus;
		this.visitedNodescount=0;
		this.threadDequesList=threadDequesList;
		this.treeHeight=treeHeight;
		this.myNodesToVisit=new LinkedBlockingDeque<Node>(this.treeHeight);
		this.listCountNodesVisited=listCountNodesVisited;
	}

	@Override
	public Integer call() {

		Node initialNode=this.initialNode.poll();

		if(initialNode!=null)
			this.myNodesToVisit.addFirst(initialNode);

		this.threadDequesList.add(myNodesToVisit);

		Integer result= new Integer(0);

		while(!this.visitStatus.isCompleted())
			result+=this.do_extractNewValuesFromNodesTree();

		this.listCountNodesVisited.add(new Integer(this.visitedNodescount));

		return result;
	}

	private int do_extractNewValuesFromNodesTree() {

		Node extractedNode=null;

		try {
			extractedNode = this.myNodesToVisit.pollLast();

			if(extractedNode!=null){
				this.visitedNodescount++;
				return this.getSumSubTreeNodes(extractedNode);}

			else{	
				boolean workStolen=this.stealWork();

				if(workStolen)
					return 0;

				if(this.barrier.getParties()-this.barrier.getNumberWaiting()==1)
					this.visitStatus.setIsCompleted(true);

				this.barrier.await();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();

		} catch (BrokenBarrierException e) {
			return 0;		}

		return 0;
	}

	private int getSumSubTreeNodes(Node extractedNode) {
		int result=this.processor.onerousFunction(extractedNode.getValue());

		Node sx=extractedNode.getSx();
		Node dx=extractedNode.getDx();

		if(dx!=null){
			this.myNodesToVisit.addLast(dx);
			this.barrier.reset();
		}

		if(sx!=null){
			this.visitedNodescount++;
			return result+ getSumSubTreeNodes(sx);}

		return result;

	}

	private boolean stealWork() {
		Node newNode=null;

		for(Deque<Node> listNodesToVisit:this.threadDequesList){
			if(listNodesToVisit!=this.myNodesToVisit){
				newNode=listNodesToVisit.pollFirst();

				if(newNode!=null){
					this.myNodesToVisit.addLast(newNode);
					return true;
				}
			}
		}
		return false;	
	}

}
