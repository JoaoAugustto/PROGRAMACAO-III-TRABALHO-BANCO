import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class ContaSer {
    private PessoaFisicaSer pessoaFisicaSer = new PessoaFisicaSer();
    private Hashtable<String, AbstConta> hashConta = new Hashtable<String, AbstConta>();
    private Scanner scanner = new Scanner(System.in);

    public void cadastrarConta(AbstConta conta) throws Exception{
        hashConta.put(conta.getCpf(), conta);
    }

    public void cadastrarContaCorrente() throws Exception{
        Integer agencia = getAgencia();
        Integer senha = verificarSenha();
        PessoaFisica pessoaFisica = verificarPessoaFisica();
        cadastrarConta(new ContaCorrente(agencia, senha, pessoaFisica));
    }

    public void cadastrarContaPoupanca() throws Exception{
        Integer agencia = getAgencia();
        Integer senha = verificarSenha();
        PessoaFisica pessoaFisica = verificarPessoaFisica();
        cadastrarConta(new ContaPoupanca(agencia, senha, pessoaFisica));
    }

    private Integer getAgencia() throws NumberFormatException, IOException{
        System.out.print("Coloque o numero da sua agencia: ");
        int agencia = Integer.parseInt(scanner.nextLine());
        return agencia;
    }

    public Integer verificarSenha() throws Exception{
        System.out.println("Entre com a sua senha de 4 digitos");
        String teste = scanner.nextLine();
        if (!teste.matches("[0-9]+") && teste.length() != 4){
            System.out.println("Senha invalidos");
            verificarSenha();
            return 0;
        }
        else {
            int senha = Integer.parseInt(teste);
            return senha;
        }
    }

    public PessoaFisica verificarPessoaFisica() throws IOException{
        System.out.println("Entre com o cpf");
        String cpf = scanner.nextLine();
        if(!pessoaFisicaSer.hashPF.containsKey(cpf)){
            System.out.println("Cpf nao cadastrado");
            verificarPessoaFisica();
        }
        return pessoaFisicaSer.obterPorCPF(cpf);
    }

}
