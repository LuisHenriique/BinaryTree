public class BalanTree extends BinaryTree {
    private String[] aux;

    public BalanTree(int tam) {
        super(tam);
    }


    protected int count_nodes(int i) {
        int count = 0;
        //Caso base, nó null return 0.
        if (i >= this.get_tam() || this.get_valor_binaryTree(i) == null) return 0;

        // Soma-se 1 para o próprio nó e chama recursivamente para os nós das sub-árvores da esquerda e direita.
        return 1 + count_nodes(2 * i + 1) + count_nodes(2 * i + 2); // 1 + esquerda + direita

    }

    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    protected boolean is_balance() {
        int qtd_esq = this.count_nodes(2 * 0 + 1); // filho esquerdo
        int qtd_dir = this.count_nodes(2 * 0 + 2); // filho direito

        return Math.abs(qtd_esq - qtd_dir) <= 1;
    }


    private void append_node_balanceado(String [] list, int begin, int end, int index){
        if (begin > end || index >= this.get_tam()){
            return;
        }

        int middle = (begin + end ) / 2 ;

        // insere o valor do meio no index atual da heap
        this.set_valor_binaryTree(index, list[middle]);

        // Calcula os índices dos filhos e chama recursivamente
        append_node_balanceado(list, begin, middle - 1, 2 * index + 1); // filho esquerdo
        append_node_balanceado(list, middle + 1, end, 2 * index + 2);   // filho direito

    }

    protected void create_balance_tree() {
        aux = new String[this.get_tam_atual()];

        int[] pos = {0};
        this.percurso_em_ordem(0, aux, pos); // coleta dados da árvore original

        this.zerarHeapTree(); // limpa a árvore atual (a balanceada)
        this.append_node_balanceado(aux, 0, aux.length - 1, 0); // insere de forma balanceada
    }

}
