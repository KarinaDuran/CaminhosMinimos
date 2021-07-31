public class Main {
	public static class MedidorTempo {

		private long inicio;

		public void comeca() {
			System.out.println("Medindo o tempo");
			inicio = System.currentTimeMillis();
		}

		public void termina() {
			System.out.println("Tempo gasto: " + ((System.currentTimeMillis() - inicio) ) + " milissegundo(s).");
		}
	}

	public static void main(String[] args) {
		int pesoMaximo = 1000;
		MedidorTempo medidor = new MedidorTempo();

		EP2 a1 = new EP2();
		EP2 a2 = new EP2();
		EP2 a3 = new EP2();
		EP2 a4 = new EP2();
		EP2 a5 = new EP2();

		double[] densidades = {0.05, 0.1, 0.15, 0.20,0.25, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 1 }; // 
		int[] entradas = {500};// , 75000, 100000, 200000,
							// 300000,100000, 200000,300000, 300, 500, 1000, 5000, 10000, 25000, 50000
		Digrafo DAG;

		for (int i = 0; i < densidades.length; i++) {
			for (int j = 0; j < entradas.length; j++) {
				System.out.println("Teste de densidade" + densidades[i] + " e tamanho " + entradas[j]);
				System.out.println(entradas[j] + "\n");
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
				medidor.termina();

				// System.out.println(" - BellmanFord");
				// medidor.comeca();
				// a2.BellmanFord(DAG, 0);
				// medidor.termina();

				System.out.println(" - Dijkstra");
				medidor.comeca();
				a3.Dijkstra(DAG, 0);
				medidor.termina();

				System.out.println("Grafos comuns: ");

				// System.out.println(" - BellmanFord");
				// medidor.comeca();
				// a4.BellmanFord(digrafo, 0);
				// medidor.termina();

				System.out.println(" - Dijkstra");
				medidor.comeca();
				a5.Dijkstra(digrafo, 0);
				medidor.termina();
			}
		}

		// for (int i = 0; i < densidades.length; i++) {
		// for (int j = 0; j < entradas.length; j++) {
		// System.out.println("Teste de densidade" + densidades[i] + " e tamanho " +
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
