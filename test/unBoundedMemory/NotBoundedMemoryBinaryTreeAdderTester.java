package unBoundedMemory;

import java.util.Deque;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import adder.BinaryTreeAdder;
import tree.BinaryTreeVisitUtility;
import tree.Node;


public class NotBoundedMemoryBinaryTreeAdderTester implements BinaryTreeAdder {
	private int NCPU;

	private CyclicBarrier barrier;
	private ExecutorService pool;
	private ExecutorCompletionService<Integer> ecs;


	private Deque<Node> nodesQueue;
	private LinkedBlockingQueue<Integer>countVisitedNodes;


	public NotBoundedMemoryBinaryTreeAdderTester() {
		this.NCPU=Runtime.getRuntime().availableProcessors();
		this.barrier=new CyclicBarrier(NCPU);

		this.pool= Executors.newFixedThreadPool(NCPU);

		this.ecs=new ExecutorCompletionService<Integer>(pool);

		this.nodesQueue=new LinkedBlockingDeque<Node>();
		this.countVisitedNodes=new LinkedBlockingQueue<Integer>();

	}


	@Override
	public int computeOnerousSum(Node root) {

		BinaryTreeVisitUtility bt=new BinaryTreeVisitUtility(false);
		this.nodesQueue.addLast(root);
		for(int i=0;i<this.NCPU;i++)
			this.ecs.submit(new NotBoundedMemorySumTaskTester(nodesQueue, this.barrier,bt,this.countVisitedNodes));

		int sum=0;

		try {
			for(int i=0; i<this.NCPU;i++)
				sum+=this.ecs.take().get();

		} catch (InterruptedException e) {
			e.printStackTrace();

		} catch (ExecutionException e) {
			e.printStackTrace();}

		this.pool.shutdown();

		return sum;
	}


	public LinkedBlockingQueue<Integer> getCountVisitedNodes() {
		return countVisitedNodes;
	}

	public void setCountNodes(LinkedBlockingQueue<Integer> countNodes) {
		this.countVisitedNodes = countNodes;
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


