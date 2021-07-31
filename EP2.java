

public class EP2 {
    public int N;
    public Vertice[] pq;
    public int[] qp;
    public int[] custo;
    public int[] pais;
    // Auxiliares para DAGS
    int[] visitado;
    public int[] fila;
    public int inicio;
    public int fim;

    // Metodo para impressao de vertices na ordem em que estao no digrafo
    public void imprimirVertices(Digrafo digrafo) {
        for (int i = 0; i < digrafo.vertices.length; i++) {
            System.out.println("chave: " + digrafo.vertices[i].chave);
            // Imprirmir pos achar os caminhos minimos
            if (pais != null) {
                System.out.println("pai: " + pais[i]);
                System.out.println("peso: " + custo[i]);
                // Para imprimir todos os arcos, antes de encontrar o caminho minimo
            } else {
                Arco aux = digrafo.vertices[i].primeiroArco;
                while (aux != null) {
                    System.out.println(aux.chave2 + " " + aux.peso);
                    aux = aux.irmao;
                }
            }
        }
    }

    // Metodos auxiliares para encontrar o numero de vertices esperado para o tamanho
    // total do digrafo
    public int calculaNVertices(int entradaEsperada, double p) {
        float V = (float) (((p - 1) + Math.sqrt(Math.pow((1 - p), 2) + 4 * p * entradaEsperada)) / (2 * p));
        return Math.round(V);
    }

    // Metodo para DAG
    public int calculaNVerticesDAG(int entradaEsperada, double p) {
        float V = (float) ((p / 2 - 1 + Math.sqrt(Math.pow((1 - p / 2), 2) + 4 * p / 2 * entradaEsperada)) / p);
        return Math.round(V);
    }

    // Retorna a ordenacao topologica
    public int[] ordenacaoTopologica(Digrafo DAG, int chaveInicial) {
        int[] ts = new int[DAG.vertices.length];
        // Vetor auxiliar para saber qual o grau de entrada
        int[] entrada = new int[DAG.vertices.length - chaveInicial];
        // Inicialmente todos 0
        for (int v = chaveInicial; v < entrada.length; v++) {
            entrada[v] = 0;
        }
        // Percorre todos os arcos para descobrir o grau de todo mundo
        Arco arcos;
        for (int v = chaveInicial; v < DAG.vertices.length; v++) {
            arcos = DAG.vertices[v].primeiroArco;
            while (arcos != null) {
                entrada[arcos.chave2] = entrada[arcos.chave2] + 1;
                arcos = arcos.irmao;

            }
        }
        // Inicia a fila
        QUEUEinit(entrada.length);
        for (int v = chaveInicial; v < DAG.vertices.length; v++) {
            // Adiciona as fontes na fila
            if (entrada[v] == 0)
                QUEUEput(v);
        }

        int i = 0;
        int v;
        // Percorre a fila
        while (!QUEUEempty()) {
            v = QUEUEget();
            ts[i] = v;
            arcos = DAG.vertices[v].primeiroArco;
            while (arcos != null) {
                entrada[arcos.chave2] = entrada[arcos.chave2] - 1;
                // Se o vertice destino ja tiver grau 0 de entrada, adiciona na fila
                if (entrada[arcos.chave2] == 0) {
                    QUEUEput(arcos.chave2);
                }
                arcos = arcos.irmao;
            }
            i++;
        }
        return ts;
    }

    // Funcoes de implementacao de fila
    public void QUEUEinit(int tamanho) {
        fila = new int[tamanho];
        fim = 0;
        inicio = 0;

    }

    public boolean QUEUEempty() {
        return inicio == fim;
    }

    public void QUEUEput(int posicao) {
        fila[fim] = posicao;
        fim++;
    }

    public int QUEUEget() {
        int retorno;
        retorno = fila[inicio];
        inicio++;
        return retorno;
    }

    // Algoritmo de DAGmin
    public void DAGmin(Digrafo digrafo, int chaveInicial) {
        // Estipula um valor máximo
        int infinito = digrafo.k * digrafo.n + 1;
        int[] ts = ordenacaoTopologica(digrafo, chaveInicial);
        Arco arcos;
        // Inicializa os vetores auxiliares
        inicializarCaminhosMinimos(digrafo);
        // Inicializa o custo da chave inicial com 0 e o pai como ele mesmo
        custo[chaveInicial] = 0;
        pais[chaveInicial] = chaveInicial;
        // Atribui a todos os vertices valor infinito
        for (int i = 0; i < digrafo.vertices.length; i++) {

            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }

        // Percorre o vetor de ordenação topológica
        int i;
        for (Vertice aux = digrafo.vertices[ts[i = 0]]; i < ts.length; aux = digrafo.vertices[ts[i++]]) {
            if (custo[ts[i]] != infinito) {
                // Percorre os arcos do vertice
                arcos = aux.primeiroArco;
                while (arcos != null) {
                    relaxar(arcos);
                    arcos = arcos.irmao;
                }

            }
        }
    }

    // Inicializa os vetores de custo e dos pais
    public void inicializarCaminhosMinimos(Digrafo digrafo) {
        custo = new int[digrafo.n];
        pais = new int[digrafo.n];
    }

    // Método que implementa o algoritmo de BellmanFord
    public void BellmanFord(Digrafo digrafo, int chaveInicial) {
        // Infinito eh o numero de pior caso (caso todos os vertices tenham peso k)
        int infinito = digrafo.k * digrafo.n + 1;
        // Inicializa os vetores
        inicializarCaminhosMinimos(digrafo);
        // Custo recebe 0 e o pai do vertice inicial é ele mesmo
        custo[chaveInicial] = 0;
        pais[chaveInicial] = chaveInicial;

        // Atribui a todos os vertices valor infinito
        for (int i = 0; i < digrafo.vertices.length; i++) {
            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }
        // Arco auxiliar
        Arco arestas;
        // Percorre V vezes
        for (int i = 1; i < digrafo.vertices.length; i++) {
            // Percorre cada vertices
            for (int j = 0; j < digrafo.vertices.length; j++) {
                // Relaxa todas as arestas que saem do vertice
                arestas = digrafo.vertices[j].primeiroArco;
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
    public void Dijkstra(Digrafo digrafo, int chaveInicial) {
        int infinito = digrafo.k * digrafo.n + 1;
        inicializarCaminhosMinimos(digrafo);
        custo[chaveInicial] = 0;
        pais[chaveInicial] = chaveInicial;

        Arco arcos = digrafo.vertices[chaveInicial].primeiroArco;

        // Inicializa os vetores para o heap
        pq = new Vertice[digrafo.vertices.length + 1];
        qp = new int[digrafo.vertices.length];

        // Todos os vértices com exceção do inicial inicializados com infinito e pai -1
        for (int i = 0; i < digrafo.vertices.length; i++) {
            if (i != chaveInicial) {
                custo[i] = infinito;
                pais[i] = -1;
            }
        }

        // Inicializa o heap
        iniciapq();
        // Insere o vertice iniial
        inserirPq(digrafo.vertices[chaveInicial]);

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
                        inserirPq(digrafo.vertices[arcos.chave2]);
                    } else {
                        // Se já foi visitado, decrementa
                        decrementa(digrafo.vertices[arcos.chave2]);
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

   
}