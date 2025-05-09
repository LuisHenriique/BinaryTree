import java.util.Objects;

public class BinaryTree {

    //Attributes
    private String[] heapTree;
    private int tam;
    private int tam_atual;


    //Special methods
    public BinaryTree(int tam) {
        this.tam = tam;
        this.heapTree = new String[this.get_tam()]; // Inicializa um vetor de ‘strings’ de tamanho 'n'
        this.tam_atual = 0; // tamanho atual da árvore
    }

    public int get_tam() {
        return tam;
    }

    public void set_tam(int tam) {
        this.tam = tam;
    }

    public int get_tam_atual() {
        return tam_atual;
    }

    public void set_tam_atual(int tam_atual) {
        this.tam_atual = tam_atual;
    }

    public String get_valor_binaryTree(int i) {
        return this.heapTree[i];
    }

    public void set_valor_binaryTree(int i, String value) {
        this.heapTree[i] = value;
    }

    //Protected methods

    protected void zerarHeapTree() {
        for (int i = 0; i < this.heapTree.length; i++) {
            this.heapTree[i] = null;
        }
    }


    // método auxiliar que pega árvore atual, percorre em ordem e para cada elemento e add no array auxiliar
    protected void percurso_em_ordem(int i, String[] aux, int pos[]) {

        // Caso base, condição de parada.
        if (i >= this.heapTree.length || this.heapTree[i] == null) {
            return;
        }
        // Verifica filho esquerdo
        int esq = 2 * i + 1;
        if (esq < this.tam) {
            percurso_em_ordem(esq, aux, pos);
        }

        aux[pos[0]] = this.heapTree[i];
        pos[0]++;
        // Verifica filho direito
        int dir = 2 * i + 2;
        if (dir < this.tam) {
            percurso_em_ordem(dir, aux, pos);
        }
    }

    // Método responsável por calcular a quantidade de nós da sub-árvore direita e esquerda a partir de um nó i
    protected int count_nodes(int i) {
        int count = 0;
        //Caso base, nó null return 0.
        if (i >= this.get_tam() || this.heapTree[i] == null) return 0;

        // Soma-se 1 para o próprio nó e chama recursivamente para os nós das sub-árvores da esquerda e direita.
        return 1 + count_nodes(2 * i + 1) + count_nodes(2 * i + 2); // 1 + esquerda + direita

    }

    protected boolean is_balance() {
        return false;
    }

    protected int left_node(int i) {
        int left = 2 * i + 1;
        if (this.heapTree[i] != null && left < this.tam) {
            return left;
        }
        return -1;
    }

    protected int right_node(int i) {
        int right = 2 * i + 2;
        if (this.heapTree[i] != null && right < this.tam) {
            return right;
        }
        return -1;
    }

    protected String get_node(int i) {
        if (this.heapTree[i] != null) {
            return this.heapTree[i];
        }
        return null;
    }

    protected void setNode(int i, String value) {

        if (this.heapTree[i] != null) {
            this.heapTree[i] = value;
            //Preciso fazer a verificação para após a alteração para reordenar ele

        }

    }

//Public methods

    // Insert node
    public void append_node(String value) {
        int i = 0;
        // Se a árvore estiver vazia, insere na primeira posição
        if (Objects.equals(this.heapTree[0], null)) {
            this.heapTree[0] = value;
            this.tam_atual++;

            return;
        }

        // Agora procura o lugar correto para o novo nó
        while (i < this.get_tam() && this.heapTree[i] != null) {

            // Se o valor for menor, tenta o filho esquerdo
            if (value.compareTo(this.heapTree[i]) < 0) {
                // Verifica se o filho esquerdo está vazio
                i = 2 * i + 1;  // Muda para o filho esquerdo

            }
            // Se o valor for maior, tenta o filho direito
            else if (value.compareTo(this.heapTree[i]) > 0) {
                i = 2 * i + 2;  // Muda para o filho direito

            }
            // Se o valor já estiver na árvore, não insere (não duplicado)
            else {
                return;
            }
        }

        // Insere o valor na posição adequada
        this.heapTree[i] = value;
        this.tam_atual++;
    }


    public boolean remove_node(String node) {
        //Remoção é divida em 3 casos, sendo 1 caso o nó não possuí filhos, 2 caso é quando o nó tem um filho e 3 e último caso é quando o nó tem os 2 filhos

        int i = 0;
        while (i < this.get_tam() && this.heapTree[i] != null) {
            //Encontrei o node
            if (Objects.equals(this.heapTree[i], node)) {
                //verifico o filho esquerdo ou o direito se são nulos

                int filhoEsq = 2 * i + 1;
                int filhoDir = 2 * i + 2;
                int novaPosicaoDoFilhoDoRemovido = 0 ;


                //1º caso
                if ((Objects.equals(this.heapTree[2 * i + 1], null)) && (Objects.equals(this.heapTree[2 * i + 2], null))) {

                    this.heapTree[i] = null;
                    this.set_tam_atual(this.get_tam_atual() - 1);
                    return true;
                }
                //2º caso

                // esquerda nulo e direita != nulo
                if ((Objects.equals(this.heapTree[2 * i + 1], null)) && this.heapTree[2 * i + 2] != null) {
                    this.heapTree[i] = this.heapTree[filhoDir];
                    novaPosicaoDoFilhoDoRemovido = i;
                    ordena_sub_arvores(filhoDir, novaPosicaoDoFilhoDoRemovido);

                    this.set_tam_atual(this.get_tam_atual() - 1);
                    return true;


                }
                // direita nulo e esquerda != nulo
                else if ((Objects.equals(this.heapTree[2 * i + 2], null)) && this.heapTree[2 * i + 1] != null) {
                    int filhoEsquerdo = 2 * i + 1;
                    this.heapTree[i] = this.heapTree[filhoEsquerdo];
                    novaPosicaoDoFilhoDoRemovido = i;

                    ordena_sub_arvores(filhoEsq, novaPosicaoDoFilhoDoRemovido);

                    this.set_tam_atual(this.get_tam_atual() - 1);
                    return true;

                }
                //3º caso
                if (this.heapTree[2 * i + 1] != null && this.heapTree[2 * i + 2] != null) {

                    int substituto = filhoEsq;// vai para a subárvore esquerda
                    while (substituto < this.get_tam() && substituto * 2 + 2 < this.get_tam() && this.heapTree[2 * substituto + 2] != null) {
                        substituto = 2 * substituto + 2; // procuro os filhos a direita do nó, para pegar o maior nó da sub-árvore esquerda
                    }

                    this.heapTree[i] = this.heapTree[substituto];
                    novaPosicaoDoFilhoDoRemovido = i;

                    ordena_sub_arvores(substituto, novaPosicaoDoFilhoDoRemovido);

                    this.set_tam_atual(this.get_tam_atual() - 1);
                    return true;

                }


                return false;
            }
            // elemento procurado é menor que o atual
            else if (node.compareTo(this.heapTree[i]) < 0) i = 2 * i + 1;
            else// elemento procurado é maior que o atual
                i = 2 * i + 2;
        }
        return false;

    }

    private void ordena_sub_arvores(int filhoDoNoRemovido, int novaPosicaoDoFilhoDoRemovido) {
        int filhoEsq = 2 * filhoDoNoRemovido + 1;
        int filhoDir = 2 * filhoDoNoRemovido + 2;


        int filhoEsqNovaPosicao = 2 * novaPosicaoDoFilhoDoRemovido + 1;
        int filhoDirNovaPosicao = 2 * novaPosicaoDoFilhoDoRemovido + 2;

        // Caso o nó não tenha filhos, define ele como null
        if (filhoEsq < this.get_tam() && this.heapTree[filhoEsq] == null && filhoDir < this.get_tam() && this.heapTree[filhoDir] == null) {
            this.heapTree[filhoDoNoRemovido] = null;
        }
        // Caso o nó tenha os dois filhos
        if(filhoEsq < this.get_tam() && this.heapTree[filhoEsq] != null && filhoDir < this.get_tam() && this.heapTree[filhoDir] != null){
            this.heapTree[filhoDirNovaPosicao]  = this.heapTree[filhoDir];
            this.heapTree[filhoDoNoRemovido] = this.heapTree[filhoEsq];
            this.heapTree[filhoDir] = this.heapTree[2 * filhoEsq + 2 ];
        }
        // Verifica se o filho esquerdo do filhoDoNoRemovido está dentro do limite da árvore
        if (filhoEsq < this.get_tam() && this.heapTree[filhoEsq] != null) {
            // Coloco o filho esquerdo do filhoDireitoDoNoRemovido na esquerda da novaPosicaoDoNoRemovido
            this.heapTree[filhoEsqNovaPosicao] = this.heapTree[filhoEsq];
            this.heapTree[filhoDoNoRemovido] = null;
            if(2 * filhoEsq + 1 < this.get_tam() && this.heapTree[2 * filhoEsq + 1] == null)
                this.heapTree[filhoEsq] = null ;

        }

        // Verifica se o filho direito está dentro do limite da árvore
        if (filhoDir < this.get_tam() && this.heapTree[filhoDir] != null) {
            // Coloco o filho direito do filhoEsquerdoDoNoRemovido na direita da novaPosicaoDoNoRemovido
            this.heapTree[filhoDirNovaPosicao] = this.heapTree[filhoDir];
            this.heapTree[filhoDoNoRemovido] = null;
            if(2 * filhoDir + 2 < this.get_tam() && this.heapTree[2 * filhoDir + 2] == null)
                this.heapTree[filhoDir] = null ;
        }

        // Chama recursivamente para garantir que a subárvore á baixo também esteja em ordem
        if (filhoEsq < this.get_tam() && this.heapTree[filhoEsq] != null) {
            ordena_sub_arvores(filhoEsq, filhoEsqNovaPosicao);
        }
        if (filhoDir < this.get_tam() && this.heapTree[filhoDir] != null) {
            ordena_sub_arvores(filhoDir, filhoDirNovaPosicao);
        }
    }

    public boolean search_node(String node) {
        int i = 0;
        while (i < this.get_tam() && this.heapTree[i] != null) {
            //Encontrei o node
            if (Objects.equals(this.heapTree[i], node)) {
                return true;
            }
            // elemento procurado é menor que o atual
            else if (node.compareTo(this.heapTree[i]) < 0) i = 2 * i + 1;
            else// elemento procurado é maior que o atual
                i = 2 * i + 2;
        }
        return false;
    }

    public int len_tree() {
        return this.get_tam_atual();
    }

    public void status() {
        System.out.println("Estado atual da árvore:");

        for (int i = 0; i < this.tam; i++) {
            if (this.heapTree[i] != null) {
                System.out.println(i + " " + this.heapTree[i]);
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if (left < this.tam && this.heapTree[left] != null)
                    System.out.println(" | Esquerda: " + this.heapTree[left]);
                if (right < this.tam && this.heapTree[right] != null)
                    System.out.println(" | Direita: " + this.heapTree[right]);

            }

        }
        System.out.println("Tamanho atual da árvore: " + tam_atual);
    }

    //Entender mais como funciona esta função
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        if (this.get_tam_atual() == 1 ){
            sb.append("\"0 ").append(this.heapTree[0]).append("\" ");
        }
        else {
            for (int i = 0; i < tam; i++) {
                if (heapTree[i] != null) {
                    String parent = "\"" + i + " " + heapTree[i] + "\"";

                    int left = 2 * i + 1;
                    int right = 2 * i + 2;

                    if (left < tam && heapTree[left] != null) {
                        sb.append(parent).append(" ->").append("\"").append(left).append(" ").append(heapTree[left]).append("\"\n");
                    }

                    if (right < tam && heapTree[right] != null) {
                        sb.append(parent).append(" ->").append("\"").append(right).append(" ").append(heapTree[right]).append("\"\n");
                    }
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
