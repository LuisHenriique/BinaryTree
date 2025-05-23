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
        if (i < this.get_tam())
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
        int i = 0;
        while (i < this.get_tam() && this.heapTree[i] != null) {
            if (Objects.equals(this.heapTree[i], node)) {
                removeNodeAt(i);
                this.set_tam_atual(this.get_tam_atual() - 1);
                return true;
            } else if (node.compareTo(this.heapTree[i]) < 0) {
                i = 2 * i + 1;
            } else {
                i = 2 * i + 2;
            }
        }
        return false;
    }

    private void removeNodeAt(int index) {
        int lastIndex = findLastNodeIndex();

        // Caso 1: Nó folha
        if (2 * index + 1 >= this.get_tam() ||
                (this.heapTree[2 * index + 1] == null &&
                        this.heapTree[2 * index + 2] == null)) {
            this.heapTree[index] = null;
        }
        // Caso 2: Apenas um filho
        else if (this.heapTree[2 * index + 1] == null || this.heapTree[2 * index + 2] == null) {
            int childIndex = (this.heapTree[2 * index + 1] != null) ? 2 * index + 1 : 2 * index + 2;
            shiftSubtreeUp(childIndex, index);
        }
        // Caso 3: Dois filhos
        else {
            // Encontrar o maior elemento da subárvore esquerda
            int predecessor = 2 * index + 1;
            while (2 * predecessor + 2 < this.get_tam() && this.heapTree[2 * predecessor + 2] != null) {
                predecessor = 2 * predecessor + 2;
            }

            this.heapTree[index] = this.heapTree[predecessor];
            removeNodeAt(predecessor);
        }
    }

    private void shiftSubtreeUp(int subtreeRoot, int newPosition) {
        if (subtreeRoot >= this.get_tam() || this.heapTree[subtreeRoot] == null) {
            return;
        }

        this.heapTree[newPosition] = this.heapTree[subtreeRoot];
        this.heapTree[subtreeRoot] = null;

        // Mover filhos recursivamente
        shiftSubtreeUp(2 * subtreeRoot + 1, 2 * newPosition + 1);
        shiftSubtreeUp(2 * subtreeRoot + 2, 2 * newPosition + 2);
    }

    private int findLastNodeIndex() {
        for (int i = this.get_tam() - 1; i >= 0; i--) {
            if (this.heapTree[i] != null) {
                return i;
            }
        }
        return -1;
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
