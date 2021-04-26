import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Indexacao_multinivel
 */
public class Indexacao_multinivel {

    static Scanner leitor = new Scanner(System.in);
    static BufferedReader ler = new BufferedReader(new InputStreamReader(System.in));

    public static double log(double base, double valor) {
        return Math.log(valor) / Math.log(base);
    }

    public static void titulo(){
        System.out.println("|-------- INDEXAÇÃO MULTINIVEL --------|\n             desenvolvido por Lucas Bottrel Lopes de Moura durante a disciplina de Banco de Dados\n");

        System.out.println("Esse programa leva como base a seguinte estrutura:\n    - Indices primários usando indices multiniveis estáticos\n    - Indices secundários usando indices multiniveis dinâmicos com árvore B+");

    }

    public static void calculoIndicePrimario(){

        try{
            System.out.println("\n\n------------------- CALCULO DO INDICE PRIMARIO");

            System.out.print("\nDigite a nome da chave: ");
            String chavePrimaria = ler.readLine();

            System.out.print("\nDigite o numero de registros da tabela: ");
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
            int controle = 0;
                if (espacoM >= 1000) {
                    espacoM = espacoM / 1024.00;
                    controle = 1;
                }

            double acessoM = Math.ceil(log(fatorBlocoM, aux)) + 1;

            DecimalFormat df = new DecimalFormat("#.00");

            System.out.println("\n\n ------- RESULTADOS DO INDICE PRIMARIO - " + (chavePrimaria) + " ------- \n");
            System.out.println("FATOR DE BLOCO : " + (int)fatorBlocoM);
            System.out.print("NUMERO DE BLOCOS: ");
            int soma = 0;
            for (int i = 0; i < j; i++){
                System.out.print("BLOCO M" + (i+1) + ": " + (int)numBlocosM[i] + "   ");
                soma += (int)numBlocosM[i];
            }
            System.out.print("= " + soma);
            System.out.println("\nESPAÇO GASTO : " + df.format(espacoM) + (controle == 0 ? "KB" : "MB"));
            System.out.println("NUMERO DE ACESSOS À MEMÓRIA : " + (int)acessoM);   

        } catch (Exception e){
            System.out.println(e.getMessage());
        }  
            
    }

    public static void calculoIndiceSecundario(){
        
        try {    
            String continuar = "S";

            while (continuar.equals("S") || continuar.equals("s")){

                System.out.println("\n\n------------------- CALCULO DO INDICE SECUNDARIO");

                System.out.print("\nDigite a nome da chave: ");
                String chaveSecundaria = ler.readLine();

                System.out.print("\n\nDigite o numero de registros da tabela: ");
                int numReg = leitor.nextInt();

                System.out.print("Digite o tamanho da chave (em B): ");
                int tamCH = leitor.nextInt();

                System.out.print("Digite o tamanho do ponteiro de nó (em B): ");
                int tamPN = leitor.nextInt();

                System.out.print("Digite o tamanho do ponteiro de bloco (em B): ");
                int tamPB = leitor.nextInt();

                System.out.print("Digite a taxa de ocupação (em %): ");
                int ocupacao = leitor.nextInt();

                System.out.print("Digite o tamanho dos blocos de disco (em KB): ");
                int tamBD = leitor.nextInt();

                // Nó Indice
                double elemPorNoI = Math.floor(((tamBD*1024) - tamPN) / (tamCH + tamPN));
                double ordemArvore = elemPorNoI + 1;
                double h = Math.ceil(log(ordemArvore, numReg));
                double hNos = h - 1;

                // Nó Registro
                double elemPorNoR = Math.floor(((tamBD * 1024) - tamPN) / (tamCH + tamPB));

                double fatorBlocoBMais = Math.ceil(elemPorNoR * ((float)ocupacao / 100));
                double numBlocosBMais = Math.ceil(numReg / fatorBlocoBMais);
                double numNos = Math.ceil(numBlocosBMais / Math.ceil(elemPorNoI * ((float)ocupacao / 100) + hNos));
                double espacoBMais = (numBlocosBMais + numNos) * tamBD;

                int controle = 0;
                if (espacoBMais >= 1000) {
                    espacoBMais = espacoBMais / 1024.00;
                    controle = 1;
                }
                double acessosBMais = h + 1;

                DecimalFormat df = new DecimalFormat("#.00");

                System.out.println("\n\n ------- RESULTADOS DO INDICE SECUNDARIO - " + chaveSecundaria + " ------- \n");
                System.out.println("FATOR DE BLOCO : " + (int)fatorBlocoBMais);
                System.out.print("NUMERO DE BLOCOS: " + (int)numBlocosBMais);
                System.out.println("\nESPAÇO GASTO : " + df.format(espacoBMais) + (controle == 0 ? "KB" : "MB"));
                System.out.println("NUMERO DE ACESSOS À MEMÓRIA : " + (int)acessosBMais);

                System.out.print("\n\n -> DESEJA REALIZAR NOVO CALCULO DE CHAVE SECUNDARIA (S/N)? : ");
                continuar = leitor.next();
            }
        } catch (Exception e){

        }

    }

    public static void main(String[] args) {
        
        titulo();
        String continuar = "S";

        while (continuar.equals("S") || continuar.equals("s")){
            calculoIndicePrimario();

            String resp = "";
            System.out.print("\n -> DESEJA CALCULAR O INDICE SECUNDARIO? (S/N) : ");
            resp = leitor.next();
            if (resp.equals("S") || resp.equals("s")) calculoIndiceSecundario();

            System.out.print("\n\n -> DESEJA REALIZAR NOVO CALCULO (S/N)? : ");
            continuar = leitor.next();
        }

    }
}