import java.util.Scanner;

/**
 * Indexacao_multinivel
 */
public class Indexacao_multinivel {

    static Scanner leitor = new Scanner(System.in);

    public static double log(double base, double valor) {
        return Math.log(valor) / Math.log(base);
    }

    public static void titulo(){
        System.out.println("|-------- INDEXAÇÃO MULTINIVEL --------|\n             desenvolvido por Lucas Bottrel Lopes de Moura durante a disciplina de Banco de Dados\n");

        System.out.println("Esse programa leva como base a seguinte estrutura:\n    - Indices primários usando indices multiniveis estáticos\n    - Indices secundários usando indices multiniveis dinâmicos com árvore B+\n\n");

    }

    public static void calculoIndicePrimario(){
        
        System.out.print("Digite o numero de registros da tabela: ");
        int numReg = leitor.nextInt();

        System.out.print("Digite o tamanho do registro (em B): ");
        int tamReg = leitor.nextInt();

        System.out.print("Digite o tamanho da chave primária (em B): ");
        int tamCP = leitor.nextInt();

        System.out.print("Digite o tamanho do ponteiro de bloco (em B): ");
        int tamPB = leitor.nextInt();

        System.out.print("Digite o tamanho dos blocos de disco (em KB): ");
        int tamBD = leitor.nextInt();

        double fatorBloco = Math.floor(tamBD * 1024 / tamReg);
        double numBlocos = Math.ceil(numReg / fatorBloco);
        double aux = numBlocos;

        double fatorBlocoM = Math.floor((tamBD * 1024) / (tamCP + tamPB));
        double numBlocosM[] = new double[100];
        double somaBlocos = 0;

        int j = 0;
        for (; numBlocos > 1; j++){
            numBlocos = Math.ceil(numBlocos / fatorBlocoM);
            numBlocosM[j] = numBlocos;
            somaBlocos += numBlocos;
        }

        double espacoM = somaBlocos * tamBD;

        double acessoM = Math.ceil(log(fatorBlocoM, aux)) + 1;

        System.out.println("\n\n ------- RESULTADOS DO INDICE PRIMARIO ------- \n");
        System.out.println("FATOR DE BLOCO (FM): " + fatorBlocoM);
        System.out.print("NUMERO DE BLOCOS: ");
        for (int i = 0; i < j; i++){
            System.out.print("BLOCO M" + (i+1) + ": " + numBlocosM[i] + "   ");
        }
        System.out.println("\nESPAÇO GASTO (SM): " + (int)espacoM + "KB");
        System.out.println("NUMERO DE ACESSOS À MEMÓRIA (AM): " + (int)acessoM);     
        
    }

    public static void main(String[] args) {
        
        titulo();
        
        calculoIndicePrimario();
        //calculoIndiceSecundario();



    }
}