/*
Guilherme Umemura (9353592)
Karina Duran Munhos (11295911)
*/

import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static class MedidorTempo {

		private long inicio;

		public void comeca() {
			System.out.println("Medindo o tempo");
			inicio = System.currentTimeMillis();
		}

		public long termina() {
			System.out.println("Tempo gasto: " + ((System.currentTimeMillis() - inicio) ) + " milissegundo(s).");
			return System.currentTimeMillis() - inicio;
		}
	}

	public static void main(String[] args) {
		int pesoMaximo = 1000;
		MedidorTempo medidor = new MedidorTempo();

		FileWriter fileW;
		try {
      		fileW = new FileWriter("results.txt");
	    } catch (IOException e) {
	    	fileW = null;
	      	System.out.println("An error occurred.");
	      	e.printStackTrace();
	    }

		EP2 a1 = new EP2();
		EP2 a2 = new EP2();
		EP2 a3 = new EP2();
		EP2 a4 = new EP2();
		EP2 a5 = new EP2();

		double[] densidades = {0.05, 0.1, 0.15, 0.20,0.25, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 1}; // 
		int[] entradas = {100, 500, 1000, 5000, 10000, 100000, 250000, 500000, 750000, 1000000, 1250000, 2000000, 5000000, 10000000,20000000,30000000,40000000};
		Digrafo DAG;
		long end;

		for (int i = 0; i < densidades.length; i++) {
			for (int j = 0; j < entradas.length; j++) {
				System.out.println("\nTeste de densidade " + densidades[i] + " e tamanho " + entradas[j]);
				int vertices = a1.calculaNVertices(entradas[j], densidades[i]);
				int verticesDAG = a1.calculaNVerticesDAG(entradas[j], densidades[i]);
				System.out.println("Vertices: " + vertices + " ");

				DAG = new Digrafo(verticesDAG, densidades[i], pesoMaximo);
				DAG.geradorDAG();
				Digrafo digrafo = new Digrafo(vertices, densidades[i], pesoMaximo);
				digrafo.gerador();
				
				System.out.println("DAGS:");
				System.out.println(" - DAGmin");

				medidor.comeca();
				a1.DAGmin(DAG, 0);
				end = medidor.termina();
				try{
					fileW.write("\n" + densidades[i] + "\n" + entradas[j] + "\n" + vertices + "\n");
					fileW.write(end + "\n");
				}catch (IOException e) {
			      	e.printStackTrace();
			    }
				
				if(entradas[j] <= 1250000){
					System.out.println(" - BellmanFord");
					medidor.comeca();
					a2.BellmanFord(DAG, 0);
					end = medidor.termina();
					try{
						fileW.write(end + "\n");
					}catch (IOException e) {
				      	e.printStackTrace();
				    }
				}else{
					try{
						fileW.write("-1" + "\n");
					}catch (IOException e) {
				      	e.printStackTrace();
				    }
				}

				System.out.println(" - Dijkstra");
				medidor.comeca();
				a3.Dijkstra(DAG, 0);
				end = medidor.termina();
				try{
					fileW.write(end + "\n");
				}catch (IOException e) {
			      	e.printStackTrace();
			    }

				if(entradas[j] <= 1250000){
					System.out.println("Grafos comuns: ");
					System.out.println(" - BellmanFord");
					medidor.comeca();
					a4.BellmanFord(digrafo, 0);
					end = medidor.termina();
					try{
						fileW.write(end + "\n");
					}catch (IOException e) {
				      	e.printStackTrace();
				    }
				}else{
					try{
						fileW.write("-1" + "\n");
					}catch (IOException e) {
				      	e.printStackTrace();
				    }
				}

				System.out.println(" - Dijkstra");				
				medidor.comeca();
				a5.Dijkstra(digrafo, 0);
				end = medidor.termina();
				try{
					fileW.write(end + "\n");
				}catch (IOException e) {
			      	e.printStackTrace();
			    }
			}
		}

		try {
			fileW.close();
	    } catch (IOException e) {
	      	e.printStackTrace();
	    }

		// for (int i = 0; i < densidades.length; i++) {
		// for (int j = 0; j < entradas.length; j++) {
		// System.out.println("Teste de densidade " + densidades[i] + " e tamanho " +
		// entradas[j]);
		// a1 = new EP2();
		// int vertices = a1.calculaNVertices(entradas[j], densidades[i]);
		// int verticesDAG = a1.calculaNVerticesDAG(entradas[j], densidades[i]);

		// DAG = new Digrafo(verticesDAG, densidades[i], pesoMaximo);
		// DAG.geradorDAG();

		// digrafo = new Digrafo(vertices, densidades[i], pesoMaximo);
		// digrafo.gerador();

		// // calcular tempo
		// System.out.println("DAGS:");

		// System.out.println(" - DAGmin");
		// medidor.comeca();
		// a1.DAGmin(DAG, 0);
		// medidor.termina();

		// System.out.println(" - BellmanFord");
		// medidor.comeca();
		// a1.BellmanFord(DAG, 0);
		// medidor.termina();

		// System.out.println(" - Dijkstra");
		// medidor.comeca();
		// a1.Dijkstra(DAG, 0);
		// medidor.termina();

		// System.out.println("Grafos comuns: ");

		// System.out.println(" - BellmanFord");
		// medidor.comeca();
		// a1.BellmanFord(digrafo, 0);
		// medidor.termina();

		// System.out.println(" - Dijkstra");
		// medidor.comeca();
		// a1.Dijkstra(digrafo, 0);
		// medidor.termina();
		// }
		// }

	}
}
