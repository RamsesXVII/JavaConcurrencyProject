package treeStructure;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tree.BalancedBinaryTreeGenerator;
import tree.BinaryTreeVisitUtility;
import tree.Node;

public class BalancedTreeBuildTest {
	private BalancedBinaryTreeGenerator bt;

	private BinaryTreeVisitUtility bu;

	private  Node rootFullBinaryH0;
	private  Node rootFullBinaryH1;
	private  Node rootFullBinaryH5;
	private  Node rootFullBinaryH10;


	@Before
	public void setUp() throws Exception {

		this.bt=new BalancedBinaryTreeGenerator();
		this.bu=new BinaryTreeVisitUtility();

		this.rootFullBinaryH0=bt.buildTree(0);
		this.rootFullBinaryH1=bt.buildTree(1);
		this.rootFullBinaryH5=bt.buildTree(5);
		this.rootFullBinaryH10=bt.buildTree(10);
		
	}

	@Test
	public void testTreeSize() {

		assertNull(this.rootFullBinaryH0);
		assertEquals(0, bu.getNumberofNodes(rootFullBinaryH0));

		assertNotNull(this.rootFullBinaryH1);
		assertEquals(1, bu.getNumberofNodes(rootFullBinaryH1));

		assertNotNull(this.rootFullBinaryH5);
		assertEquals(31, bu.getNumberofNodes(rootFullBinaryH5));

		assertNotNull(this.rootFullBinaryH10);
		assertEquals(1023, bu.getNumberofNodes(rootFullBinaryH10));
		
	}

	@Test
	public void testTreeHeight() {

		assertNull(this.rootFullBinaryH0);
		assertEquals(0, bu.getTreeHeight(rootFullBinaryH0));

		assertNotNull(this.rootFullBinaryH1);
		assertEquals(1, bu.getTreeHeight(rootFullBinaryH1));

		assertNotNull(this.rootFullBinaryH5);
		assertEquals(5, bu.getTreeHeight(rootFullBinaryH5));

		assertNotNull(this.rootFullBinaryH10);
		assertEquals(10, bu.getTreeHeight(rootFullBinaryH10));
		}}

