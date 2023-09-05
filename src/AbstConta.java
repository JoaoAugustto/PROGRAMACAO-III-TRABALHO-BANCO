import java.util.Scanner;

public abstract class AbstConta {
    private int agencia;
    private int senha;
    private PessoaFisica pessoaFisica;
    private int numeroConta;
    Scanner scanner = new Scanner(System.in);

    public AbstConta(int agencia, int senha, PessoaFisica pessoaFisica){
       this.agencia = agencia;
       this.senha = senha;
       this.pessoaFisica = pessoaFisica;
       this.numeroConta = scanner.nextInt() * -1;
    }

    public String getCPF(){
        return pessoaFisica.getCPF();
    }
}
