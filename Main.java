import java.io.PrintWriter;
import java.io.IOException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in); // Criando o objeto Scanner, para ler do usuário.
        BinaryTree abb = new BinaryTree(150); // ABB
        BalanTree apb = new BalanTree(150); // ABB PB
        AVLTree abAVL = new AVLTree(150);

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();

            // Se a linha lida for vazia para o loop
            if (linha == null || linha.trim().isEmpty()) {
                break;
            }

            // Entrada de dados na árvore
            if (linha.startsWith("i ")) {
                String value = linha.substring(2);
                abb.append_node(value);
                abAVL.append_node(value);      // AVL
                apb.append_node(value);


                abAVL.balancear_arvoreAVL(0);  // Balanceia a partir da raiz

            }


            if (linha.startsWith("d ")) {
                String value = linha.substring(2);
                if (abb.remove_node(value))

                apb.remove_node(value);

                abAVL.remove_node(value);     // AVL
                abAVL.balancear_arvoreAVL(0);  // Rebalanceia após remover
            }

        }

        // Balanceamento da Árvore perfeitamente balanceada após as remoções
        if (!apb.is_balance()) apb.create_balance_tree();

        //Execução da chamada do toString();
        try (PrintWriter writer = new PrintWriter("abb.dot")) {
            writer.println(abb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Saídas visuais no terminal

        System.out.println(abb.toString());
        System.out.println();


        System.out.println(apb.toString());
        System.out.println();


        System.out.println(abAVL.toString());
        System.out.println();

        scanner.close();
    }
}
