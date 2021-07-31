import java.util.Random;

public class Digrafo {
    public int n;
    public double p;
    public int k;
    public Vertice[] vertices;

    Digrafo(int n, double p, int k) {
        if (p > 0 && n > 0 && k > 0 && p <= 1) {
            vertices = new Vertice[n];
            this.n = n;
            this.p = p;
            this.k = k;
        } else {
            System.out.println("Um dos valores está negativo ou a probabilidade é maior que 1.");
        }
    }

    // public void geradorTeste() {
    //     for (int i = 0; i < n; i++) {
    //         vertices[i] = new Vertice(i);
    //     }

    //     vertices[0].primeiroArco = new Arco(0, 1, 2);
    //     // vertices[0].primeiroArco.irmao = new Arco(0, 3, 3);
    //     vertices[0].primeiroArco.irmao = new Arco(0, 2, 4);

    //     vertices[1].primeiroArco = new Arco(1, 5, 1);
    //     vertices[1].primeiroArco.irmao = new Arco(1, 3, -1);

    //     vertices[2].primeiroArco = new Arco(2, 3, -2);
    //     vertices[2].primeiroArco.irmao = new Arco(2, 4, 1);

    //     vertices[3].primeiroArco = new Arco(3, 4, 1);
    //     // vertices[3].primeiroArco.irmao = new Arco(3, 5, 0);

    //     // vertices[4].primeiroArco = new Arco(4, 5, 2);
    //     // vertices[4].primeiroArco.irmao = new Arco (4,0,7);

    //     // vertices[6].primeiroArco = new Arco(6, 3,-6);
    //     // vertices[6].primeiroArco.irmao = new Arco (6, 7,7);

    // }

    public void geradorDAG() {
        Random rand = new Random();
        int probabilidade;
        Arco arco;
        Arco anterior = null;
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertice(i);
        }
        // Igual o gerador anterior, mas com j > i
        for (int i = 0; i < vertices.length - 1; i++) {
            for (int j = i + 1; j < vertices.length; j++) {
                probabilidade = rand.nextInt(101);
                if (probabilidade <= p * 100) {
                    int peso = rand.nextInt(k + 1);
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
        Random rand = new Random();
        int probabilidade;
        Arco arco;
        Arco anterior = null;
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertice(i);
        }
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                if (j != i) {
                    probabilidade = rand.nextInt(101);
                    if (probabilidade <= p * 100) {

                        int peso = rand.nextInt(k + 1);
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
