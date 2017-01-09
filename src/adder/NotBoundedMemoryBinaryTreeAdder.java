package adder;

import java.util.Deque;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import tree.BinaryTreeVisitUtility;
import tree.Node;

public class NotBoundedMemoryBinaryTreeAdder implements BinaryTreeAdder {

	private int NCPU;

	private CyclicBarrier barrier;
	private ExecutorService pool;
	private ExecutorCompletionService<Integer> ecs;

	private Deque<Node> nodesQueue;


	public NotBoundedMemoryBinaryTreeAdder() {

		this.NCPU=Runtime.getRuntime().availableProcessors();
		this.barrier=new CyclicBarrier(NCPU);

		this.pool= Executors.newFixedThreadPool(NCPU);

		this.ecs=new ExecutorCompletionService<Integer>(pool);

		this.nodesQueue=new LinkedBlockingDeque<Node>();
	}

	@Override
	public int computeOnerousSum(Node root) {

		BinaryTreeVisitUtility bt=new BinaryTreeVisitUtility(false);
		this.nodesQueue.addFirst(root);
		for(int i=0;i<this.NCPU;i++)
			this.ecs.submit(new NotBoundedMemorySumTask(nodesQueue, this.barrier,bt));

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