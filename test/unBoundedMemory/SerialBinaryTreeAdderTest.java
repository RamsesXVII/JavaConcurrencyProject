package unBoundedMemory;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

import adder.SerialBinaryTreeAdder;
import tree.BalancedBinaryTreeGenerator;
import tree.Node;
import tree.UnbalancedBinaryTreeGenerator;

public class SerialBinaryTreeAdderTest {

	private SerialBinaryTreeAdder sbadder;
	
	private BalancedBinaryTreeGenerator btgenerator;
	private UnbalancedBinaryTreeGenerator ubtgenerator;
	
	private  Node rootFullBinaryH0;
	private  Node rootFullBinaryH1;
	private  Node rootFullBinaryH5;
	private  Node rootFullBinaryH10; 

	
	private Node rootUnBalancedLeftH0;
	private Node rootUnBalancedLeftH5;

	private Node rootUnBalancedRightH0;
	private Node rootUnBalancedRightH5;

	//private Node rootRandomH5;

	@Before
	public void setUp() throws Exception {
		
		this.btgenerator=new BalancedBinaryTreeGenerator();
		this.ubtgenerator=new UnbalancedBinaryTreeGenerator();
				
		this.sbadder=new SerialBinaryTreeAdder();

		this.rootFullBinaryH0=btgenerator.buildTree(0);
		this.rootFullBinaryH1=btgenerator.buildTree(1);
		this.rootFullBinaryH5=btgenerator.buildTree(5);
		this.rootFullBinaryH10=btgenerator.buildTree(10);
		
		ubtgenerator.setLeftModeGeneration();

		this.rootUnBalancedLeftH0=ubtgenerator.buildTree(0);
		this.rootUnBalancedLeftH5=ubtgenerator.buildTree(5);
		
		ubtgenerator.setRightModeGeneration();
		
		this.rootUnBalancedRightH0=ubtgenerator.buildTree(0);
		this.rootUnBalancedRightH5=ubtgenerator.buildTree(5);
		
		/*ubtgenerator.setRandomModeGeneration();
		this.rootRandomH5=ubtgenerator.buildTree(5);*/
	}

	@Test
	public void testAdderOnBalancedTree() {

		assertEquals(0,this.sbadder.computeOnerousSum(rootFullBinaryH0));
		assertEquals(1,this.sbadder.computeOnerousSum(rootFullBinaryH1));
		assertEquals(57,this.sbadder.computeOnerousSum(rootFullBinaryH5));
		assertEquals(2036,this.sbadder.computeOnerousSum(rootFullBinaryH10));

	}
	
	@Test
	public void testAdderOnUnbalancedTree() {

		assertEquals(0,this.sbadder.computeOnerousSum(this.rootUnBalancedLeftH0));
		assertEquals(15,this.sbadder.computeOnerousSum(this.rootUnBalancedLeftH5));
		assertEquals(0,this.sbadder.computeOnerousSum(this.rootUnBalancedRightH0));
		assertEquals(15,this.sbadder.computeOnerousSum(this.rootUnBalancedRightH5));

	}
}