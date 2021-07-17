import java.util.Random;
public class Grafo {
    public int n;
    public int p;
    public int k;
    public Vertice [] vertices;


    Grafo(int n, int p, int k) {
        vertices = new Vertice[n];
        this.n = n;
        this.p = p;
        this.k = k;
    }

    public void geradorTeste(){
        for (int i = 0; i<n; i++){
            vertices[i] = new Vertice(i);
        }

        vertices[0].primeiroArco = new Arco(0, 2, 7);
        vertices[0].primeiroArco.irmao = new Arco (0,4,4);
        vertices[0].primeiroArco.irmao.irmao = new Arco (0,3,2);

        vertices[1].primeiroArco = new Arco(1, 2,0);

        vertices[2].primeiroArco = new Arco(2, 4, 1);

        vertices[3].primeiroArco = new Arco(3, 4,1);
        vertices[3].primeiroArco.irmao = new Arco (3, 5,3);
        
        vertices[4].primeiroArco = new Arco(4, 1,4);
        vertices[4].primeiroArco.irmao = new Arco (4, 5,1);

        vertices[5].primeiroArco = new Arco(5, 1,2);
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

    public void geradorDijkstra() {
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
}
