import java.util.concurrent.PriorityBlockingQueue;
import java.util.Random;

public class EP2 {
    private class Vertice {
        private int chave;
        private int pai;
        private Arco primeiroArco;
        private boolean visitado;
        private double pesoAtual;

        private Vertice(int nome) {
            this.chave = nome;
            this.primeiroArco = null;
            pesoAtual = 0;
            visitado = false;
        }
    }

    private class Arco {
        private int chave1;
        private int chave2;
        private double peso;
        private Arco irmao;

        Arco(int chave1, int chave2, double peso) {
            this.chave1 = chave1;
            this.chave2 = chave2;
            this.peso = peso;
        }
    }

    public class grafo {
        private int n;
        private double p;
        private int k;
        Vertice[] vertices;
        private int[] heap;

        grafo(int n, double p, int k) {
            vertices = new Vertice[n];
            this.n = n;
            this.p = p;
            this.k = k;
            heap = new int[n * n];
        }

        public class heapMaximo {

        }

        public void geradorDAG() {
            Random ajuda = new Random();
            int probabilidade;
            Arco arco;
            Arco anterior = null;
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = new Vertice(i);
            }
            // Igual o gerador anterior, mas com j > i
            for (int i = 0; i < vertices.length; i++) {
                for (int j = i + 1; j < vertices.length; j++) {

                    probabilidade = ajuda.nextInt(101);
                    if (probabilidade < p * 100) {

                        int peso = ajuda.nextInt(k);
                        arco = new Arco(i, j, peso);
                        if (vertices[i].primeiroArco == null) {
                            vertices[i].primeiroArco = arco;
                            anterior = arco;
                        } else {
                            anterior.irmao = arco;
                            anterior = arco;
                        }
                    }
                }
            }
        }

        public void gerador() {
            Random ajuda = new Random();
            int probabilidade;
            Arco arco;
            Arco anterior = null;
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = new Vertice(i);
            }

            for (int i = 0; i < vertices.length; i++) {
                for (int j = 0; j < vertices.length; j++) {
                    if (j != i) {
                        probabilidade = ajuda.nextInt(101);
                        if (probabilidade < p * 100) {

                            int peso = ajuda.nextInt(k);
                            arco = new Arco(i, j, peso);
                            if (vertices[i].primeiroArco == null) {
                                vertices[i].primeiroArco = arco;
                                anterior = arco;
                            } else {
                                anterior.irmao = arco;
                                anterior = arco;
                            }
                        }
                    }
                }
            }
        }

        public boolean BellmanFord(grafo grafo, int chaveInicial) {
            //Infinito eh o numero de pior caso (caso todos os vertices tenham peso k)
            int infinito = grafo.k * grafo.n;
            // Pega o vertice inicial
            Vertice verticeInicial = grafo.vertices[chaveInicial];
            // Atribui peso 0
            verticeInicial.pesoAtual = 0;
            verticeInicial.pai = -1;

            //Atribui a todos os vertices valor infinito
            for (int i = 0; i < grafo.vertices.length; i++) {
                if (i != chaveInicial) {
                    grafo.vertices[i].pesoAtual = infinito;
                    grafo.vertices[i].pai = -1;
                }
            }
            Arco arestas;

            for (int i = 0; i < grafo.vertices.length; i++) {

                arestas = grafo.vertices[i].primeiroArco;
                while (arestas != null) {
                    relaxar(grafo, arestas);
                    arestas = arestas.irmao;
                }

            }
            for (int i =0; i<grafo.vertices.length; i++){
            arestas = grafo.vertices[i].primeiroArco;
            while (arestas != null) {
                if (grafo.vertices[arestas.chave1].pesoAtual > grafo.vertices[arestas.chave2].pesoAtual + arestas.peso)
                    return false;
                arestas = arestas.irmao;
            }
        }
            return true;
        
        }

        public void relaxar(grafo grafo, Arco arco) {
            if (grafo.vertices[arco.chave2].pesoAtual > grafo.vertices[arco.chave1].pesoAtual + arco.peso) {
                grafo.vertices[arco.chave2].pesoAtual = grafo.vertices[arco.chave1].pesoAtual + arco.peso;
                grafo.vertices[arco.chave2].pai = arco.chave1;

            }
        }

        public String Dijkstra(grafo grafo, int chaveInical) {
            int infinito = grafo.k * grafo.n;
            Vertice verticeInicial = grafo.vertices[chaveInical];
            verticeInicial.pesoAtual = 0;
            Arco arcos = verticeInicial.primeiroArco;
            int[] heap = new int[grafo.vertices.length];

            for (int i = 0; i < grafo.vertices.length; i++) {
                if (i != chaveInical) {
                    grafo.vertices[i].pesoAtual = infinito;
                }
            }

            while (arcos.irmao != null) {

            }
            return "batata";
        }

    }

}