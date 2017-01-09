package treeStructure;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tree.BinaryTreeVisitUtility;
import tree.Node;
import tree.UnbalancedBinaryTreeGenerator;

public class UnBalancedTreeBuildTest {

	private UnbalancedBinaryTreeGenerator ubt;

	private BinaryTreeVisitUtility bu;

	private Node rootUnBalancedLeftH0;
	private Node rootUnBalancedLeftH5;

	private Node rootUnBalancedRightH0;
	private Node rootUnBalancedRightH5;

	private Node rootRandomH5;

	@Before
	public void setUp() throws Exception {

		this.ubt=new UnbalancedBinaryTreeGenerator();
		this.bu=new BinaryTreeVisitUtility();

		ubt.setLeftModeGeneration();

		this.rootUnBalancedLeftH0=ubt.buildTree(0);
		this.rootUnBalancedLeftH5=ubt.buildTree(5);
		
		ubt.setRightModeGeneration();
		
		this.rootUnBalancedRightH0=ubt.buildTree(0);
		this.rootUnBalancedRightH5=ubt.buildTree(5);
		
		ubt.setRandomModeGeneration();
		this.rootRandomH5=ubt.buildTree(5);
	}

	@Test
	public void testTreeSize() {
		
		assertNull(this.rootUnBalancedLeftH0);
		assertEquals(0, bu.getNumberofNodes(rootUnBalancedLeftH0));
		
		assertNotNull(this.rootUnBalancedLeftH5);
		assertEquals(5, bu.getNumberofNodes(rootUnBalancedLeftH5));
		
		assertNull(this.rootUnBalancedRightH0);
		assertEquals(0, bu.getNumberofNodes(rootUnBalancedRightH0));
		
		assertNotNull(this.rootUnBalancedRightH5);
		assertEquals(5, bu.getNumberofNodes(rootUnBalancedRightH5));
	}

	@Test
	public void testTreeHeight() {
		
		assertNull(this.rootUnBalancedLeftH0);
		assertEquals(0, bu.getTreeHeight(rootUnBalancedLeftH0));

		assertNotNull(this.rootUnBalancedLeftH5);
		assertEquals(5, bu.getTreeHeight(rootUnBalancedLeftH5));
		
		assertNull(this.rootUnBalancedRightH0);
		assertEquals(0, bu.getTreeHeight(rootUnBalancedRightH0));

		assertNotNull(this.rootUnBalancedRightH5);
		assertEquals(5, bu.getTreeHeight(rootUnBalancedRightH5));
		
		assertNotNull(this.rootRandomH5);
		assertEquals(5, bu.getTreeHeight(rootRandomH5));}}

