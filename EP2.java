
public class EP2 {
    public static int N;
    public static Vertice[] pq;
    public static int[] qp;
    public int[] custo;
    public int[] pais;

    public void imprimirVertices(Grafo grafo) {
        for (int i = 0; i < grafo.vertices.length; i++) {
            System.out.println("chave: " + grafo.vertices[i].chave);
            System.out.println("pai: " + pais[i]);
            System.out.println("peso: " + custo[i]);
        }
    }

    public void inicializarCaminhosMinimos (Grafo grafo){
        custo = new int [grafo.n];
        pais = new int [grafo.n];
    }

    public void BellmanFordDAG(Grafo grafo, int chaveInicial) {
        // Infinito eh o numero de pior caso (caso todos os vertices tenham peso k)
        int infinito = grafo.k * grafo.n;
        inicializarCaminhosMinimos(grafo);
        // Atribui peso 0
        custo[chaveInicial] = 0;
        pais[chaveInicial] = chaveInicial;

        // Atribui a todos os vertices valor infinito
        for (int i = 0; i < grafo.vertices.length; i++) {
            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }
        Arco arestas;

        for (int i = 0; i < grafo.vertices.length; i++) {
            for (int j = 0; j < grafo.vertices.length; j++) {
                arestas = grafo.vertices[j].primeiroArco;
                while (arestas != null) {
                    if (custo[arestas.chave1] != infinito) {
                        relaxar(arestas);
                        arestas = arestas.irmao;
                    }

                }
            }

        }

    }

    public boolean BellmanFord(Grafo grafo, int chaveInicial) {
        // Infinito eh o numero de pior caso (caso todos os vertices tenham peso k)
        int infinito = grafo.k * grafo.n;
        // Atribui peso 0
        inicializarCaminhosMinimos(grafo);
        custo[chaveInicial] = 0;
        pais[chaveInicial] = chaveInicial;

        // Atribui a todos os vertices valor infinito
        for (int i = 0; i < grafo.vertices.length; i++) {
            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }
        Arco arestas;

        for (int i = 0; i < grafo.vertices.length; i++) {
            for (int j = 0; j < grafo.vertices.length; j++) {
                arestas = grafo.vertices[j].primeiroArco;
                while (arestas != null) {
                    relaxar(arestas);
                    arestas = arestas.irmao;

                }
            }

        }
        for (int i = 0; i < grafo.vertices.length; i++) {
            arestas = grafo.vertices[i].primeiroArco;
            while (arestas != null) {
                if (custo[arestas.chave2] > custo[arestas.chave1] + arestas.peso) {
                    return false;
                }

                arestas = arestas.irmao;
            }
        }
        return true;

    }

    public void relaxar(Arco arco) {
        if (custo[arco.chave2] > custo[arco.chave1] + arco.peso) {
            custo[arco.chave2] = custo[arco.chave1] + arco.peso;
            pais[arco.chave2] = arco.chave1;

        }
    }

    public void Dijkstra(Grafo grafo, int chaveInicial) {
        int infinito = grafo.k * grafo.n;
        inicializarCaminhosMinimos(grafo);
        custo[chaveInicial] = 0;
        pais[chaveInicial] = chaveInicial;

        Arco arcos = grafo.vertices[chaveInicial].primeiroArco;

        pq = new Vertice[grafo.vertices.length + 1];
        qp = new int[grafo.vertices.length];

        for (int i = 0; i < grafo.vertices.length; i++) {
            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }


        iniciapq();
        inserirPq(grafo.vertices[chaveInicial]);
                
        while (!pqVazio()) {

               Vertice aux = minimo();
            arcos = aux.primeiroArco;
            boolean visitado;
            while (arcos != null) {
            
                visitado = custo[arcos.chave2] != infinito;
                relaxar(arcos);
                if (!visitado) {
                    inserirPq(grafo.vertices[arcos.chave2]);
                } else {
                    decrementa(grafo.vertices[arcos.chave2]);
                }
                arcos = arcos.irmao;
            } 
            }

        }

    

    // Funções para o heap
    public void iniciapq() {
        N = 0;
    }

    public boolean pqVazio() {
        return N == 0;
    }

    public void inserirPq(Vertice v) {
        N++;
        qp[v.chave] = N;
        pq[N] = v;
        fixUp(N);
    }

    public Vertice minimo() {
       troca(1, N);
       N--;
       fixDown(1);
       return pq[N+1];
    }

    //Problema aqui
    public void troca(int i, int j) {
        Vertice t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
        qp[pq[j].chave] = j;
        qp[pq[i].chave] = i;
    }

    public void fixUp(int i) {
        while (i > 1 && custo[pq[(int)(i / 2)].chave] > custo[pq[i].chave]) {
            troca(i / 2, i);
            i = (int)(i / 2);
        }   
    
       
    }
    // Talvez aqui
    public void fixDown(int i) {
        int j;
        while (2 * i <= N) {
            j = 2 * i;
            if (j < N && custo[pq[j].chave] > custo[pq[j + 1].chave]) {
                j++;
            }

            if (custo[pq[i].chave] <= custo[pq[j].chave]){
                return;
            }

            troca(i, j);
            i = j;
        }
    }



    public void decrementa(Vertice vertice) {
        fixUp(qp[vertice.chave]); 
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo(6, 10, 78);
        grafo.geradorTeste();
        EP2 aux = new EP2();
        aux.Dijkstra(grafo, 0);
        aux.imprimirVertices(grafo);
    }
}