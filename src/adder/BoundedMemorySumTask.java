package adder;

import java.util.Deque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import processor.FakeProcessor;
import tree.BinaryTreeVisitUtility;
import tree.Node;

public class BoundedMemorySumTask implements Callable<Integer> {
	private LinkedBlockingQueue<Node> initialNode;
	private CyclicBarrier barrier;
	private FakeProcessor processor;
	private BinaryTreeVisitUtility visitStatus;
	private LinkedBlockingQueue<Deque<Node>> threadDequesList;
	private int treeHeight;
	private Deque<Node>myNodesToVisit;

	public BoundedMemorySumTask(LinkedBlockingQueue<Node> initialNode, CyclicBarrier barrier,
			BinaryTreeVisitUtility visitStatus,LinkedBlockingQueue<Deque<Node>> threadDequesList,
			int treeHeight) {

		this.initialNode=initialNode;
		this.barrier=barrier;
		this.processor=new FakeProcessor(1000);
		this.visitStatus=visitStatus;
		this.threadDequesList=threadDequesList;
		this.treeHeight=treeHeight;
		this.myNodesToVisit=new LinkedBlockingDeque<Node>(this.treeHeight);
	}

	@Override
	public Integer call() {

		Node initialNode=this.initialNode.poll();

		if(initialNode!=null)
			this.myNodesToVisit.addFirst(initialNode);

		this.threadDequesList.add(myNodesToVisit);

		Integer result= new Integer(0);

		while(!this.visitStatus.isCompleted()){
			result+=this.do_extractNewValuesFromNodesTree();}

		return result;
	}

	private int do_extractNewValuesFromNodesTree() {

		Node extractedNode=null;

		try {
			extractedNode = this.myNodesToVisit.pollLast();

			if(extractedNode!=null){
				return this.getSumSubTreeNodes(extractedNode);}

			else{	
				boolean workStolen=this.stealWork();

				if(workStolen)
					return 0;

				/*è l'ultimo thread->è stato visitato l'albero*/
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

		if(sx!=null)
			return result+ getSumSubTreeNodes(sx);

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
