package boundedMemory;

import java.util.Deque;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import adder.BinaryTreeAdder;
import tree.BinaryTreeVisitUtility;
import tree.Node;

public class BoundedMemoryBinaryTreeAdderTester implements BinaryTreeAdder {

	private int NCPU;

	private CyclicBarrier barrier;
	private ExecutorService pool;
	private ExecutorCompletionService<Integer> ecs;

	private LinkedBlockingQueue<Node> initialNode;
	private LinkedBlockingQueue<Integer>globalCountOfVisitedNodes;
	private LinkedBlockingQueue<Deque<Node>> threadDequesList;


	public BoundedMemoryBinaryTreeAdderTester() {
		this.NCPU=Runtime.getRuntime().availableProcessors();
		this.barrier=new CyclicBarrier(NCPU);

		this.pool= Executors.newFixedThreadPool(NCPU);

		this.ecs=new ExecutorCompletionService<Integer>(pool);

		this.initialNode=new LinkedBlockingQueue<Node>();
		this.globalCountOfVisitedNodes=new LinkedBlockingQueue<Integer>();
		this.threadDequesList=new LinkedBlockingQueue<Deque<Node>>();
	}

	@Override
	public int computeOnerousSum(Node root) {

		BinaryTreeVisitUtility visitUtility=new BinaryTreeVisitUtility(false);
		int treeHeight=visitUtility.getTreeHeight(root);

		try {
			this.initialNode.put(root);
			for(int i=0;i<this.NCPU;i++)
				this.ecs.submit(new BoundedMemorySumTaskTester(initialNode, this.barrier,
						visitUtility,threadDequesList,treeHeight,this.globalCountOfVisitedNodes));

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int sum=0;

		try {
			for(int i=0; i<this.NCPU;i++)
				sum+=this.ecs.take().get();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		this.pool.shutdown();

		return sum;
	}

	public LinkedBlockingQueue<Integer> getCountVisitedNodes() {
		return globalCountOfVisitedNodes;
	}

	public void setCountNodes(LinkedBlockingQueue<Integer> countNodes) {
		this.globalCountOfVisitedNodes = countNodes;
	}


	public int getCountOfVisitedNodes(){
		int sum=0;

		int number=this.getCountVisitedNodes().size();
		for(int i=0;i<number;i++){
			sum+=this.getCountVisitedNodes().poll();}

		return sum;

	}

	public int getNCPU() {
		return NCPU;
	}

	public void setNCPU(int nCPU) {
		NCPU = nCPU;
	}

	public CyclicBarrier getBarrier() {
		return barrier;
	}

	public void setBarrier(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	public ExecutorService getPool() {
		return pool;
	}

	public void setPool(ExecutorService pool) {
		this.pool = pool;
	}

	public ExecutorCompletionService<Integer> getEcs() {
		return ecs;
	}

	public void setEcs(ExecutorCompletionService<Integer> ecs) {
		this.ecs = ecs;
	}

}


