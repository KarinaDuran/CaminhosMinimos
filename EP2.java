
public class EP2 {
    public static int N;
    public static Vertice[] pq;
    public static int[] qp;
    public int[] custo;
    public int[] pais;

    // Metodo para impressao de vertices na ordem em que estao no grafo
    public void imprimirVertices(Grafo grafo) {
        for (int i = 0; i < grafo.vertices.length; i++) {
            System.out.println("chave: " + grafo.vertices[i].chave);
            if(pais!=null ){
            System.out.println("pai: " + pais[i]);
            System.out.println("peso: " + custo[i]);
            }
        }
    }
    //Metodosauxiliares para encontrar o numero de vertices esperado para o tamanho total do digrafo
    public int calculaNVertices (int entradaEsperada, double p){
        float V =  (float) (((p - 1) + Math.sqrt(Math.pow((1-p),2) +4*p*entradaEsperada))/(2*p));
        return Math.round(V);
    }
    //Metodo para DAG
    public int calculaNVerticesDAG (int entradaEsperada, double p){
        float V =  (float) ((p/2 - 1 + Math.sqrt(Math.pow((1-p/2),2) +4*p/2*entradaEsperada))/p);
        return Math.round(V); 
    }


    public void ordenacaoTopologica(Grafo DAG) {
         
    }



    public void DAGmin(Grafo grafo, int [] ts, int chaveInicial) {
        inicializarCaminhosMinimos(grafo);
        int infinito = grafo.k * grafo.n+1;
        custo[chaveInicial] = 0;
        Arco arcos;
        pais[chaveInicial] = chaveInicial;
       
        // Atribui a todos os vertices valor infinito
        for (int i = 0; i < grafo.vertices.length; i++) {
            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }
         int i;
        for (Vertice aux = grafo.vertices[ts[i = 0]]; i < grafo.vertices.length; aux = grafo.vertices[ts[i++]]){ 
            if (custo[i] != infinito){
                arcos = aux.primeiroArco;
                while (arcos!= null){
                    relaxar(arcos);
                }
                
            }
        }
    }

    // Inicializa os vetores de custo e dos pais
    public void inicializarCaminhosMinimos(Grafo grafo) {
        custo = new int[grafo.n];
        pais = new int[grafo.n];
    }

    // Método que implementa o algoritmo de BellmanFord
    public void BellmanFord(Grafo grafo, int chaveInicial) {
        // Infinito eh o numero de pior caso (caso todos os vertices tenham peso k)
        int infinito = grafo.k * grafo.n + 1;
        // Inicializa os vetores
        inicializarCaminhosMinimos(grafo);
        // Custo recebe 0 e o pai do vertice inicial é ele mesmo
        custo[chaveInicial] = 0;
        pais[chaveInicial] = chaveInicial;

        // Atribui a todos os vertices valor infinito
        for (int i = 0; i < grafo.vertices.length; i++) {
            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }
        // Arco auxiliar
        Arco arestas;
        // Percorre V vezes
        for (int i = 1; i < grafo.vertices.length; i++) {
            // Percorre cada vertices
            for (int j = 0; j < grafo.vertices.length; j++) {
                // Relaxa todas as arestas que saem do vertice
                arestas = grafo.vertices[j].primeiroArco;
                while (arestas != null) {
                    relaxar(arestas);
                    arestas = arestas.irmao;

                }
            }

        }

    }

    // Método que recebe um arco como parametro e compara o peso do vertice destino
    // com o peso do vertice origem acrescido do peso do arco. Se o vertice destino
    // tiver peso maior, substitui o valor anterior pela soma do peso do vetor
    // origem e o peso do vetor origem.
    public void relaxar(Arco arco) {
        if (custo[arco.chave2] > custo[arco.chave1] + arco.peso) {
            custo[arco.chave2] = custo[arco.chave1] + arco.peso;
            pais[arco.chave2] = arco.chave1;

        }
    }

    // Método que implementa o algoritmo de Dijkstra
    public void Dijkstra(Grafo grafo, int chaveInicial) {
        int infinito = grafo.k * grafo.n + 1;
        inicializarCaminhosMinimos(grafo);
        custo[chaveInicial] = 0;
        pais[chaveInicial] = chaveInicial;

        Arco arcos = grafo.vertices[chaveInicial].primeiroArco;

        // Inicializa os vetores para o heap
        pq = new Vertice[grafo.vertices.length + 1];
        qp = new int[grafo.vertices.length];

        // Todos os vértices com exceção do inicial inicializados com infinito e pai -1
        for (int i = 0; i < grafo.vertices.length; i++) {
            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }

        // Inicializa o heap
        iniciapq();
        // Insere o vertice iniial
        inserirPq(grafo.vertices[chaveInicial]);

        // Enquanto o heap não estiver vazio
        while (!pqVazio()) {
            // Pega o minimo valor
            Vertice aux = minimo();
            arcos = aux.primeiroArco;
            boolean visitado;
            while (arcos != null) {
                // Verifica se o valor do vértice destino precisa ser melhorado
                // Esse if apenas auxilia na reducao do gasto de tempo acessando métodos somente
                // quando necessario, vendo que no metodo relaxar ja existe essa verificacao
                if (custo[arcos.chave2] > custo[arcos.chave1] + arcos.peso) {
                    // Verifica se o vertice já foi visitado
                    visitado = custo[arcos.chave2] != infinito;
                    // Relaxa os arcos
                    relaxar(arcos);
                    if (!visitado) {
                        // Se não foi visitado, insere no heap
                        inserirPq(grafo.vertices[arcos.chave2]);
                    } else {
                        // Se já foi visitado, decrementa
                        decrementa(grafo.vertices[arcos.chave2]);
                    }
                }
                // Pega o próximo arco
                arcos = arcos.irmao;
            }
        }

    }

    // Funções para o heap
    // Inicializa N =0
    public void iniciapq() {
        N = 0;
    }

    // Verifica se o heap tá vazio
    public boolean pqVazio() {
        return N == 0;
    }

    // Insere vertice no heap
    public void inserirPq(Vertice v) {
        N++;
        qp[v.chave] = N;
        pq[N] = v;
        fixUp(N);
    }

    // Retorna o valor minimo do heap(posicao 1) e reorganiza o que ficar
    public Vertice minimo() {
        troca(1, N);
        N--;
        fixDown(1);
        return pq[N + 1];
    }

    // Troca 2 posicoes no heap
    public void troca(int i, int j) {
        Vertice t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
        qp[pq[j].chave] = j;
        qp[pq[i].chave] = i;

    }

    // Método auxiliar para manter o heap organizado, pois "sobe" os vértices com
    // peso menor
    public void fixUp(int i) {
        // Enquanto o "pai" do vertice NO HEAP tiver peso maior que ele, troca as
        // posicoes
        while (i > 1 && custo[pq[(int) (i / 2)].chave] > custo[pq[i].chave]) {
            troca(i / 2, i);
            i = (int) (i / 2);
        }

    }

    // Método auxiliar para manter o heap organizado, pois "desce" os vértices com
    // peso menor
    public void fixDown(int i) {
        int j;
        // Enquanto um dos "filhos" do vertice NO HEAP tiver peso menor que ele, troca
        // as posicoes
        while (2 * i <= N) {
            j = 2 * i;
            if (j < N && custo[pq[j].chave] > custo[pq[j + 1].chave]) {
                j++;
            }

            if (custo[pq[i].chave] <= custo[pq[j].chave]) {
                return;
            }

            troca(i, j);
            i = j;
        }
    }

    // Metodo que decrementa o peso de um vertice
    public void decrementa(Vertice vertice) {
        fixUp(qp[vertice.chave]);
    }

    public static void main(String[] args) {
        int entradaEsperada = 20;
        double probabilidade = 0.1;
        int pesoMaximo = 60;

        EP2 aux = new EP2();
        int vertices = aux.calculaNVertices(entradaEsperada, probabilidade);
        
        Grafo grafo = new Grafo(vertices, probabilidade, pesoMaximo);
        grafo.gerador();

        int verticesDAG = aux.calculaNVerticesDAG(entradaEsperada, probabilidade);
        Grafo DAG = new Grafo(verticesDAG, probabilidade,pesoMaximo);
        grafo.geradorDAG();


        // aux.BellmanFord(grafo, 0);

        // aux.imprimirVertices(grafo);
        // for(int i =0; i<grafo.vertices.length; i++){
        //     System.out.println("chave " + grafo.vertices[i].chave);
        //     Arco arcos = grafo.vertices[i].primeiroArco;
        //     while (arcos != null){
        //         System.out.println("Arco: "+ arcos.chave1 + " " + arcos.chave2);
        //         // System.out.println("peso " + arcos.peso);
        //         arcos = arcos.irmao;
        //     }
        // }

    }
}