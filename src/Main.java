import java.util.Scanner;

public class Main {
    private static PessoaFisicaSer pessoaFisicaSer = new PessoaFisicaSer();
    private static ContaSer contaSer = new ContaSer();
        public static void main(String[] args) {
            int OpcaoMenu = 1;
            Scanner scanner = new Scanner(System.in);

            System.out.println("Bem vindo ao sitema Bancario");
            while (OpcaoMenu != 0) {
            try{
                System.out.println("escolha uma opção abaixo");
                System.out.println(" ");
                System.out.println("1 - Cadastrar PESSOA FISICA");
                System.out.println("2 - Conta Corrente");
                System.out.println("3 - Conta Poupança");
                System.out.println("4 - Efetuar Deposito");
                System.out.println("5 - Efetuar Saque");
                System.out.println("6 - Efetuar Transferência");
                System.out.println("0 - !SAIR!");
                System.out.println(" ");
                System.out.print("Sua opção: ");
                OpcaoMenu = scanner.nextInt();

                switch (OpcaoMenu) {

                    case 1:
                        pessoaFisicaSer.Cadastrar();
                        break;
                    case 2:
                        contaSer.cadastrarContaCorrente();
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 0:
                        System.out.println("OBRIGADO POR USSAR O SISTEMA");
                        System.out.println("SAINDO......");
                        break;

                    default:
                        System.out.println("!ATENÇÃO OPÇÃO INVALIDA!");
                        scanner = new Scanner(System.in);
                        break;
                }
            }catch(Exception ex){
                 System.out.println("!ATENÇÃO OPÇÃO INVALIDA!");
                        scanner = new Scanner(System.in);
            }finally{
                scanner = new Scanner(System.in);
            }
        }
    }
}