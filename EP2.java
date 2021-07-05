import java.util.concurrent.PriorityBlockingQueue;
import java.util.Random;
public class EP2 {
    private class Vertice{
        private int chave;
        private int pai;
        private Arco primeiroArco;
        private boolean visitado;
        private double pesoAtual;
        private Vertice (int nome){
            this.chave = nome;
            this.primeiroArco = null;
            pesoAtual =0;
            visitado = false;
        }
    }


    private class Arco{
        private Vertice v1;
        private Vertice v2;
        private double peso;
        private Arco irmao;
        Arco(Vertice v1, Vertice v2, double peso){
            this.v1= v1;
            this.v2 = v2;
            this.peso = peso;
        }
    }

    public class grafo{
        private int n;
        private double p;
        private int k;
        Vertice [] vertices;
        grafo(int n, double p, int k){
            vertices = new Vertice[n];
            this.n =n;
            this.p=p;
            this.k=k;
        }
        public class filaPrioridade{
            
        }

       public void gerador(){
        Random ajuda = new Random();
        int probabilidade;
        Arco arco;
        Arco anterior = null;
        for(int i =0; i < vertices.length; i++){
            vertices[i] = new Vertice(i);
        }


        for (int i =0; i<vertices.length; i++){
            for (int j =0; j<vertices.length; j++){
                if(j!=i){
                    probabilidade = ajuda.nextInt(101);
                    if (probabilidade < p*100){

                    int peso =ajuda.nextInt(k);
                    arco = new Arco(vertices[i], vertices[j], peso);
                    if (vertices[i].primeiroArco == null){
                        vertices[i].primeiroArco = arco;
                        anterior=arco;
                    }else{
                        anterior.irmao = arco;
                        anterior = arco; 
                    }
                }
            }
            }
        }
    }
    

    public String BellmanFord (grafo grafo, int chaveInical, int chaveFinal){
        return "pao";
    }
    public String Dijkstra(grafo grafo, int chaveInical, int chaveFinal){
        int infinito = grafo.k *grafo.n;
        Vertice verticeInicial = grafo.vertices[chaveInical];
        verticeInicial.pesoAtual = 0;
        Arco arcos = verticeInicial.primeiroArco;
        for (int i =0 ; i<grafo.vertices.length; i++){
            if(i!=chaveInical){
                grafo.vertices[i].pesoAtual = infinito;
            }
        }

        while (arcos.irmao != null){
            
        }
        return "batata";
    }
    // public String BellmanFordDAG (Vertice verticeInicial, Vertice verticeFinal){
    //     return "sufle";
    // }
}

}