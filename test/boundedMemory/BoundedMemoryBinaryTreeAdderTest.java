package boundedMemory;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

import adder.SerialBinaryTreeAdder;
import tree.BalancedBinaryTreeGenerator;
import tree.BinaryTreeVisitUtility;
import tree.Node;
import tree.UnbalancedBinaryTreeGenerator;

public class BoundedMemoryBinaryTreeAdderTest {

	private BoundedMemoryBinaryTreeAdderTester sbadder;

	private BalancedBinaryTreeGenerator btgenerator;
	private UnbalancedBinaryTreeGenerator ubtgenerator;
	
	private BinaryTreeVisitUtility bvutility;

	private  Node rootFullBinaryH1;
	private  Node rootFullBinaryH5;
	private  Node rootFullBinaryH15; 

	private Node rootUnBalancedLeftH5;

	private Node rootUnBalancedRightH5;

	private Node rootRandomH15;

	@Before
	public void setUp() throws Exception {

		this.btgenerator=new BalancedBinaryTreeGenerator();
		this.ubtgenerator=new UnbalancedBinaryTreeGenerator();
		
		this.bvutility=new BinaryTreeVisitUtility();

		this.sbadder=new BoundedMemoryBinaryTreeAdderTester();

		this.rootFullBinaryH1=btgenerator.buildTree(1);
		this.rootFullBinaryH5=btgenerator.buildTree(5);
		this.rootFullBinaryH15=btgenerator.buildTree(15);

		ubtgenerator.setLeftModeGeneration();

		this.rootUnBalancedLeftH5=ubtgenerator.buildTree(5);

		ubtgenerator.setRightModeGeneration();

		this.rootUnBalancedRightH5=ubtgenerator.buildTree(5);

		ubtgenerator.setRandomModeGeneration();
		this.rootRandomH15=ubtgenerator.buildTree(15);
	}

	@Test
	public void testAdderOnBalancedTreeH1() {

		assertEquals(1,this.sbadder.computeOnerousSum(rootFullBinaryH1));
		assertEquals(this.bvutility.getNumberofNodes(rootFullBinaryH1), this.sbadder.getCountOfVisitedNodes());
	}
	
	@Test
	public void testAdderOnBalancedTreeH5() {

		assertEquals(57,this.sbadder.computeOnerousSum(rootFullBinaryH5));
		assertEquals(this.bvutility.getNumberofNodes(rootFullBinaryH5), this.sbadder.getCountOfVisitedNodes());

	}
	
	@Test
	public void testAdderOnBalancedTreeH15() {

		assertEquals(65519,this.sbadder.computeOnerousSum(rootFullBinaryH15));
		assertEquals(this.bvutility.getNumberofNodes(rootFullBinaryH15), this.sbadder.getCountOfVisitedNodes());
		
	}

	@Test
	public void testAdderOnUnbalancedTreeLH5() {

		assertEquals(15,this.sbadder.computeOnerousSum(this.rootUnBalancedLeftH5));
		assertEquals(this.bvutility.getNumberofNodes(rootUnBalancedLeftH5), this.sbadder.getCountOfVisitedNodes());

	}

	@Test
	public void testAdderOnUnbalancedTreeRH5() {

		assertEquals(15,this.sbadder.computeOnerousSum(this.rootUnBalancedRightH5));
		assertEquals(this.bvutility.getNumberofNodes(rootUnBalancedRightH5), this.sbadder.getCountOfVisitedNodes());

	}

	@Test
	public void testAdderOnRandomTreeRH10() {
		
		SerialBinaryTreeAdder sa= new SerialBinaryTreeAdder();
		int conta=sa.computeOnerousSum(rootRandomH15);

		assertEquals(conta,this.sbadder.computeOnerousSum(this.rootRandomH15));
		assertEquals(this.bvutility.getNumberofNodes(rootRandomH15), this.sbadder.getCountOfVisitedNodes());


	}
}