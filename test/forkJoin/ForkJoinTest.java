package forkJoin;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import adder.SerialBinaryTreeAdder;
import tree.BalancedBinaryTreeGenerator;
import tree.Node;
import tree.UnbalancedBinaryTreeGenerator;

public class ForkJoinTest {

	private ForkJoinBinaryTreeAdderTester sbadder;

	private BalancedBinaryTreeGenerator btgenerator;
	private UnbalancedBinaryTreeGenerator ubtgenerator;
	
	private  Node rootFullBinaryH1;
	private  Node rootFullBinaryH5;
	private  Node rootFullBinaryH10; 

	private Node rootUnBalancedLeftH5;

	private Node rootUnBalancedRightH5;

	private Node rootRandomH5;

	@Before
	public void setUp() throws Exception {

		this.btgenerator=new BalancedBinaryTreeGenerator();
		this.ubtgenerator=new UnbalancedBinaryTreeGenerator();
		
		this.sbadder=new ForkJoinBinaryTreeAdderTester();

		this.rootFullBinaryH1=btgenerator.buildTree(1);
		this.rootFullBinaryH5=btgenerator.buildTree(5);
		this.rootFullBinaryH10=btgenerator.buildTree(15);

		ubtgenerator.setLeftModeGeneration();

		this.rootUnBalancedLeftH5=ubtgenerator.buildTree(5);

		ubtgenerator.setRightModeGeneration();

		this.rootUnBalancedRightH5=ubtgenerator.buildTree(5);

		ubtgenerator.setRandomModeGeneration();
		this.rootRandomH5=ubtgenerator.buildTree(5);
	}

	@Test
	public void testAdderOnBalancedTreeH1() {

		assertEquals(1,this.sbadder.computeOnerousSum(rootFullBinaryH1));
	}
	
	@Test
	public void testAdderOnBalancedTreeH5() {

		assertEquals(57,this.sbadder.computeOnerousSum(rootFullBinaryH5));

	}
	
	@Test
	public void testAdderOnBalancedTreeH10() {

		assertEquals(65519,this.sbadder.computeOnerousSum(rootFullBinaryH10));
		
	}

	@Test
	public void testAdderOnUnbalancedTreeLH5() {

		assertEquals(15,this.sbadder.computeOnerousSum(this.rootUnBalancedLeftH5));

	}

	@Test
	public void testAdderOnUnbalancedTreeRH5() {

		assertEquals(15,this.sbadder.computeOnerousSum(this.rootUnBalancedRightH5));

	}

	@Test
	public void testAdderOnRandomTreeRH5() {
		
		SerialBinaryTreeAdder sa= new SerialBinaryTreeAdder();
		int conta=sa.computeOnerousSum(rootRandomH5);

		assertEquals(conta,this.sbadder.computeOnerousSum(this.rootRandomH5));


	}
}