package speedupViz;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;

import ChartDirector.ChartViewer;
import adder.BoundedMemoryBinaryTreeAdder;
import adder.ForkJoinBinaryTreeAdder;
import adder.NotBoundedMemoryBinaryTreeAdder;
import adder.SerialBinaryTreeAdder;
import tree.BalancedBinaryTreeGenerator;
import tree.Node;


public class Viz {

	@SuppressWarnings("resource")
	public static void main(String[]args){

		System.out.println("Questa applicazione misura il comportamento asintotico dello speedup \n "
				+ "all'aumentare della dimensione del problema di ingresso. \n Per le analisi "
				+ "viene preso in considerazione un albero binario completo, la cui altezza aumenta a ciascuna esecuzione.");
		System.out.println("In particolare, gli alberi su cui vengono eseguiti gli algoritmi variano da altezza 1 fino a quella specificata.");
		System.out.println("Questa macchina ha a disposizizone "+ Runtime.getRuntime().availableProcessors()+ " core.");
		System.out.println("Il risultato è visualizzato in una finestra dedicata e viene generato un file .tsv con i valori \"esatti\" dello speedup.");
		System.out.println("Si consiglia di specificare almeno un altezza di 15 per apprezzare valori interessanti e non superiore a 20 per motivi di tempo");
		System.out.println("Digitare l'altezza massima su cui eseguire la misurazione (es. 19): ");

		int maxHeightTree=new Scanner(System.in).nextInt()+1;

		double[] data0=new double[maxHeightTree];
		double[] data1=new double[maxHeightTree];
		double[] data2=new double[maxHeightTree];
		double[] data3=new double[maxHeightTree];
		double[] data4=new double[maxHeightTree];

		String[] labels=new String[maxHeightTree];

		try{

			PrintWriter writer = new PrintWriter("speedUpEvolutionCompleteTree.tsv", "UTF-8");
			writer.println("treeHeight \t speedupBounded(green) \t speedUpNotBounded (red) \t forkJoin (black)");

			warmUp();

			for(int i=1;i<maxHeightTree;i++){
				BalancedBinaryTreeGenerator bt=new BalancedBinaryTreeGenerator();

				MethodsExcecutor me= new MethodsExcecutor();

				Node root=bt.buildTree(i);

				//seriale
				SerialBinaryTreeAdder serialAdder= new SerialBinaryTreeAdder();
				double exTimeSer=me.getExcecutionTimeOfcomputeOnerousSumMethod(serialAdder, root);

				//memoria limitata
				BoundedMemoryBinaryTreeAdder boundedAdder= new BoundedMemoryBinaryTreeAdder();
				double exTimeParBounded=me.getExcecutionTimeOfcomputeOnerousSumMethod(boundedAdder, root);

				//memoria illimitata
				NotBoundedMemoryBinaryTreeAdder notBoundedAdder= new NotBoundedMemoryBinaryTreeAdder(); 
				double exTimeParNotBounded=me.getExcecutionTimeOfcomputeOnerousSumMethod(notBoundedAdder, root);

				//fork join
				ForkJoinBinaryTreeAdder forkJoinAdder= new ForkJoinBinaryTreeAdder(); 
				double exTimeParForkJoin=me.getExcecutionTimeOfcomputeOnerousSumMethod(forkJoinAdder, root);


				if(exTimeSer!=0&&exTimeParBounded!=0)
					data1[i]=(double)(exTimeSer/exTimeParBounded);
				else
					data1[i]=0;


				if(exTimeSer!=0&&exTimeParNotBounded!=0)
					data0[i]=(double)(exTimeSer/exTimeParNotBounded);
				else
					data0[i]=0;

				if(exTimeSer!=0&&exTimeParForkJoin!=0)
					data4[i]=(double)(exTimeSer/exTimeParForkJoin);
				else
					data4[i]=0;

				data2[i]=1;
				data3[i]=Runtime.getRuntime().availableProcessors();
				labels[i]=new String(""+(i));

				System.out.println("Terminata esecuzione su albero di altezza "+i);
				writer.println(i+"\t"+((double)exTimeSer/exTimeParBounded)+"\t"+((double)exTimeSer/exTimeParNotBounded)+"\t"+(double)(exTimeSer/exTimeParForkJoin));
			}

			writer.close();

		} catch (IOException e) {
			// do something
		}

		System.out.println("esecuzione terminata.");
		MultiLine demo = new MultiLine();

		demo.setData0(data0);
		demo.setData1(data1);
		demo.setData2(data2);
		demo.setData3(data3);
		demo.setData4(data4);

		demo.setLabels(labels);

		//Create and set up the main window
		JFrame frame = new JFrame(demo.toString());

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {System.exit(0);} });
		frame.getContentPane().setBackground(Color.white);

		// Create the chart and put them in the content pane
		ChartViewer viewer = new ChartViewer();
		demo.createChart(viewer, 0);
		frame.getContentPane().add(viewer);

		// Display the window
		frame.pack();
		frame.setVisible(true);
	}

	private static void warmUp()
	{
		System.out.println("Warmup start");

		for(int i=0;i<20;i++){
			BalancedBinaryTreeGenerator bt=new BalancedBinaryTreeGenerator();

			MethodsExcecutor me= new MethodsExcecutor();

			Node root=bt.buildTree(12);

			NotBoundedMemoryBinaryTreeAdder notBoundedA = new NotBoundedMemoryBinaryTreeAdder();
			me.getExcecutionTimeOfcomputeOnerousSumMethod(notBoundedA, root);

			BoundedMemoryBinaryTreeAdder boundedA= new BoundedMemoryBinaryTreeAdder();
			me.getExcecutionTimeOfcomputeOnerousSumMethod(boundedA, root);

			ForkJoinBinaryTreeAdder fjA= new ForkJoinBinaryTreeAdder();
			me.getExcecutionTimeOfcomputeOnerousSumMethod(fjA, root);
		}
		System.out.println("Warmup completed");

	}
}
