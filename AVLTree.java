public class AVLTree extends BinaryTree {


    public AVLTree(int tam) {
        super(tam);
    }


//    protected boolean is_balance() {
//      int fb =  altura(2 * i + 1) - altura(2 * i + 2);
    //      return Math.abs(fb) <= 1;
    //}

    //Função que reposiciona os valores dos filhos na posição correta

    private void copiarSubarvore(int origem, int destino) {
        if (origem >= this.get_tam() || this.get_valor_binaryTree(origem) == null) return;

        this.set_valor_binaryTree(destino, this.get_valor_binaryTree(origem));
        copiarSubarvore(2 * origem + 1, 2 * destino + 1);
        copiarSubarvore(2 * origem + 2, 2 * destino + 2);
    }

    private void limparSubarvore(int i) {
        if (i >= this.get_tam() || this.get_valor_binaryTree(i) == null) return;

        limparSubarvore(2 * i + 1);
        limparSubarvore(2 * i + 2);
        this.set_valor_binaryTree(i, null);
    }


    private void rotacionar_esquerda(int i) {

        int indice = i;
        int indiceEsq = 2 * i + 1;
        int indiceDir = 2 * i + 2;

        if (indiceDir >= this.get_tam() || this.get_valor_binaryTree(indiceDir) == null) return;

        // 1. Guardar os indices das subárvores da esquerda e direita de B
        int filhoEsqDeB = 2 * indiceDir + 1;
        int filhoDirDeB = 2 * indiceDir + 2;

        // 2. Guardar valor de A e B
        String valorA = this.get_valor_binaryTree(indice);     // A
        String valorB = this.get_valor_binaryTree(indiceDir);  // B
        System.out.println("\nvalor de a:"+valorA+"\n");
        System.out.println("\nvalor de b:"+valorB+"\n");

        System.out.println("\nvalor de filho esquerdo de b:"+this.get_valor_binaryTree(2 *indiceDir + 1)+"\n");


        // 3. Trocar A por B (B sobe)
        this.set_valor_binaryTree(indice, valorB);

        // 4. A vira filho esquerdo de B
        this.set_valor_binaryTree(indiceEsq, valorA);

        // 5. Copiar subárvore esquerda de B para a direita de novo A
        copiarSubarvore(filhoEsqDeB, 2 * indiceEsq + 2);

        // 6. Copiar subárvore direita de B para direita de novo B (em indice)
        copiarSubarvore(filhoDirDeB, 2 * indice + 2);

        set_valor_binaryTree(2 * indiceEsq +1, this.get_valor_binaryTree(indiceEsq) ) ;



        // 7. Limpar antiga posição de B

        if(this.get_valor_binaryTree(filhoDirDeB) == null)
            limparSubarvore(2*indice+2);
        else
            limparSubarvore(2 * indiceDir + 2);
    }

    private void rotacionar_direita(int i) {
        int indice = i;
        int indiceEsq = 2 * i + 1;
        int indiceDir = 2 * i + 2;

        if (indiceEsq >= this.get_tam() || this.get_valor_binaryTree(indiceEsq) == null) return;

        // 1. Guardar os indices das subárvores da esquerda e direita de B
        int filhoEsqDeB = 2 * indiceEsq + 1;
        int filhoDirDeB = 2 * indiceEsq + 2;

        // 2. Guardar valor de A e B
        String valorA = this.get_valor_binaryTree(indice);     // A
        String valorB = this.get_valor_binaryTree(indiceEsq);  // B
        System.out.println("\nvalor de a:"+valorA+"\n");
        System.out.println("\nvalor de b:"+valorB+"\n");
        System.out.println("\nvalor de filho esquerdo de b:"+this.get_valor_binaryTree(filhoEsqDeB)+"\n");
        // 3. Trocar A por B (B sobe)
        this.set_valor_binaryTree(indice, valorB);

        // 4. A vira filho direito de B
        this.set_valor_binaryTree(indiceDir, valorA);

        // 5. Copiar subárvore direita de B para a esquerda de novo A
        copiarSubarvore(filhoDirDeB, 2 * indiceDir + 1);

        // 6. Copiar subárvore esquerda de B para esquerda de novo B (em indice)
        copiarSubarvore(filhoEsqDeB, 2 * indice + 1);


        System.out.println("\n verifica:\n" + this.get_valor_binaryTree(2 * indice+ 1));
        System.out.println("\n verifica\n" + this.get_valor_binaryTree(2 * indiceEsq+ 1));

        if(this.get_valor_binaryTree(filhoEsqDeB) == null)
            limparSubarvore(2*indice+1);
        else
            limparSubarvore(2 * indiceEsq+ 1);
    }


    // Rotação dupla esquerda-direita (LR)
    private void rotacionar_esquerda_direita(int i) {
        int filhoEsq = 2 * i + 1;
        rotacionar_esquerda(filhoEsq);  // Rotação à esquerda no filho esquerdo
     System.out.println("\n----------------------------------Apos rotação esquerda--------------a \n");
       this.status();

        System.out.println("\n---------------------------------- rotação direita abaixo--------------a \n");
        rotacionar_direita(i);          // Rotação à direita no nó atual
        this.status();

    }

    // Rotação dupla direita-esquerda (RL)
    private void rotacionar_direita_esquerda(int i) {
        int filhoDir = 2 * i + 2;
        rotacionar_direita(filhoDir);   // Rotação à direita no filho direito
        System.out.println("\n----------------------------------Apos rotação direita--------------a \n");
        this.status();

        rotacionar_esquerda(i);         // Rotação à esquerda no nó atual
        System.out.println("\n----------------------------------Apos rotação esquerda--------------a \n");
        this.status();
    }


    // Função que calcula a altura de um nó
    int altura(int i) {
        if (i >= this.get_tam() || this.get_valor_binaryTree(i) == null) return 0;
        return 1 + Math.max(altura(2 * i + 1), altura(2 * i + 2));
    }
    public void balancear_arvoreAVL(int i) {
        if (i >= this.get_tam() || this.get_valor_binaryTree(i) == null) return;

        // Primeiro balanceia os filhos
        balancear_arvoreAVL(2 * i + 1);
        balancear_arvoreAVL(2 * i + 2);

        int fb = altura(2 * i + 1) - altura(2 * i + 2);

        if (fb > 1) { // pesada à esquerda
            int filhoEsq = 2 * i + 1;
            int fbFilho = altura(2 * filhoEsq + 1) - altura(2 * filhoEsq + 2);

            if (fbFilho >= 0) {
                // LL: Rotação simples à direita
                this.rotacionar_direita(i);
                System.out.println("\n----------------------------------Apos rotação direita simples--------------a \n");
                this.status();
            } else {
                // LR: Rotação dupla esquerda-direita
                this.rotacionar_esquerda_direita(i);
            }
        } else if (fb < -1) { // pesada à direita
            int filhoDir = 2 * i + 2;
            int fbFilho = altura(2 * filhoDir + 1) - altura(2 * filhoDir + 2);

            if (fbFilho <= 0) {
                // RR: Rotação simples à esquerda
                this.rotacionar_esquerda(i);
                System.out.println("\n----------------------------------Apos rotação esquerda simples--------------a \n");
                this.status();
            } else {
                // RL: Rotação dupla direita-esquerda
                this.rotacionar_direita_esquerda(i);
            }
        }
    }

}