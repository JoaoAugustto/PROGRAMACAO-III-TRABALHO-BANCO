import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class PessoaFisicaSer{
    protected Hashtable <String, PessoaFisica> hashPF = new Hashtable<String, PessoaFisica>();
    Scanner scanner = new Scanner(System.in);

    public void Cadastrar() throws IOException{
         System.out.println("Digite o seu cpf: ");
        String cpf = scanner.nextLine();
        if (hashPF.containsKey(cpf)){
            System.out.println("Nao pode existir o mesmo cpf para duas pessoas");
            return;
        }
        System.out.println("Digite seu nome");
        String nome = scanner.nextLine();

        hashPF.put(cpf, new PessoaFisica(nome,  cpf));
        System.out.println(hashPF.get(cpf));
    }

    public PessoaFisica obterPorCPF(String cpf){
        return hashPF.get(cpf);
    }
}