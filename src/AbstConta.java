import java.util.Random;
import java.util.Scanner;

public abstract class AbstConta {
    private int agencia;
    private int senha;
    private PessoaFisica pessoaFisica;
    private int numeroConta;
    protected float saldo;
    Scanner scanner = new Scanner(System.in);

    public AbstConta(int agencia, int senha, PessoaFisica pessoaFisica){
        this.agencia = agencia;
        this.senha = senha;
        this.pessoaFisica = pessoaFisica;
        this.saldo = 0;
        this.numeroConta = setNumeroConta(scanner);
    }

    public String getCPF(){
        return pessoaFisica.getCPF();
    }

    private int setNumeroConta(Scanner scanner) {
        int teste = scanner.nextInt();
        if (teste < 0){
            this.numeroConta = teste * -1;
        } else {
            this.numeroConta =  teste;
        }
        return teste;
    }

    protected int getSenha(){
        return senha;
    }

    protected int getAgencia(){
        return agencia;
    }
}
