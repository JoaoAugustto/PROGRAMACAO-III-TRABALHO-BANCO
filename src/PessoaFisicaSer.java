import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class PessoaFisicaSer{
    protected Hashtable <String, PessoaFisica> hashPF = new Hashtable<String, PessoaFisica>();
    Scanner scanner = new Scanner(System.in);

    public void Cadastrar() throws IOException{
        System.out.print("Coloque o seu NOME: ");
        String nome = scanner.nextLine();
        System.out.print("Coloque o seu CPF: ");
        String cpf = scanner.nextLine();

        hashPF.put(cpf, new PessoaFisica(nome, cpf));
    }

    public PessoaFisica obterPorCPF(String cpf){
        return hashPF.get(cpf);
    }
}