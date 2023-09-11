import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ContaSer{
    private PessoaFisicaSer pessoaFisicaService = new PessoaFisicaSer();
    private HashMap<String, AbstConta> hashContaPoupanca = new HashMap<String, AbstConta>();
    HashMap<String, AbstConta> hashContaCorrente = new HashMap<String, AbstConta>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public ContaSer(PessoaFisicaSer pessoaFisicaService) {
        this.pessoaFisicaService = pessoaFisicaService;
    }

    public void cadastrarContaPoupanca(AbstConta conta) throws Exception{
        if (hashContaPoupanca.containsKey(conta.getCPF())){
            System.out.println("Nao pode exister mais de uma conta poupanca com o mesmo cpf");
            return;
        }
        hashContaPoupanca.put(conta.getCPF(), conta);
    }

    public void cadastrarContaCorrente(AbstConta conta) throws Exception{
        if (hashContaCorrente.containsKey(conta.getCPF())){
            System.out.println("Nao pode exister mais de uma conta corrente com o mesmo cpf");
            return;
        }
        hashContaCorrente.put(conta.getCPF(), conta);
    }

    public void cadastrarContaCorrente() throws Exception{
        PessoaFisica pessoaFisica = verificarPessoaFisica();
        Integer agencia = getAgencia();
        Integer senha = verificarSenha();
        cadastrarContaCorrente(new ContaCorrente(agencia, senha, pessoaFisica));
    }

    public void cadastrarContaPoupanca() throws Exception{
        Integer agencia = getAgencia();
        Integer senha = verificarSenha();
        PessoaFisica pessoaFisica = verificarPessoaFisica();
        cadastrarContaPoupanca(new ContaPoupanca(agencia, senha, pessoaFisica));

    }

    private Integer getAgencia() throws NumberFormatException, IOException{
        System.out.println("Entre com o numero da sua agencia");
        int agencia = Integer.parseInt(reader.readLine());
        return agencia;
    }


    private Integer verificarSenha() throws Exception{
        System.out.println("Entre com a sua senha de 4 digitos");
        String teste = reader.readLine();
        if (!teste.matches("[0-9]+") || teste.length() != 4){
            System.out.println("Senha com parametros invalidos");
            verificarSenha();
            return 0;
        }
        else {
            int senha = Integer.parseInt(teste);
            return senha;
        }

    }

    private PessoaFisica verificarPessoaFisica() throws IOException{
        System.out.println("Entre com o cpf");
        String cpf = reader.readLine();
        if(pessoaFisicaService.hashPF.containsKey(cpf) == true){
            return pessoaFisicaService.obterPorCPF(cpf);
        } else{
            System.out.println("Cpf nao cadastrado, entre novamente com o cpf");
            return verificarPessoaFisica();
        }
    }


    private AbstConta verificarContaPoupanca() throws IOException{
        System.out.println("Entre com o cpf do dono da conta");
        String cpf = reader.readLine();
        if(hashContaPoupanca.containsKey(cpf) == true){
            return hashContaPoupanca.get(cpf);
        } else{
            System.out.println("Cpf nao cadastrado, entre novamente com o cpf");
            return verificarContaPoupanca();
        }
    }

    private AbstConta verificarContaCorrente() throws IOException{
        System.out.println("Entre com o cpf do dono da conta");
        String cpf = reader.readLine();
        if(hashContaCorrente.containsKey(cpf) == true){
            return hashContaCorrente.get(cpf);
        } else{
            System.out.println("Cpf nao cadastrado, entre novamente com o cpf");
            return verificarContaCorrente();
        }
    }

    public void efetuarDeposito() throws NumberFormatException, IOException{
        System.out.println("Deseja efetuar o deposito em uma conta CORRENTE ou POUPANCA?");
        System.out.println("1 - POUPANCA");
        System.out.println("2 - CORRENTE");
        int opcao = Integer.parseInt(reader.readLine());
        switch (opcao){
            case 1:
                efetuarDepositoPoupanca();
            break;

            case 2:
                efetuarDepositoCorrente();
            break;

            default:
                System.out.println("Opcao invalida. Selecione novamente");
                efetuarDeposito();
            break;
        }
    }

    private AbstConta verificarContaOrigem() throws IOException{
        AbstConta conta;
        System.out.println("A conta de origem é POUPANCA ou CORRENTE?");
        System.out.println("1 - POUPANCA");
        System.out.println("2 - CORRENTE");
        int opcao = Integer.parseInt(reader.readLine());
        switch (opcao){
            case 1:
                conta = verificarContaPoupanca();
                if (conta != null){
                    return conta;
                } else {
                    System.out.println("Conta nao encontrada");
                    return verificarContaOrigem();
                }

            case 2:
                conta = verificarContaCorrente();
                if (conta != null){
                    return conta;
                } else {
                    System.out.println("Conta nao encontrada");
                    return verificarContaOrigem();
                }

            default:
                System.out.println("Opcao invalida. Selecione novamente");
                return verificarContaOrigem();
        }
    }


    private int getValorTransacao() throws NumberFormatException, IOException{
        System.out.println("Insira o valor inteiro desjado");
        String teste = reader.readLine();
        if (!teste.matches("[0-9]+")){
            System.out.println("Invalido. Nao pode inserir letras");
            return getValorTransacao();
        } else if (Integer.parseInt(teste) <= 0){
            System.out.println("Valor nao pode ser igual ou menor a 0");
            return getValorTransacao();
        } else{
            int valor = Integer.parseInt(teste);
            return valor;
        }
    }

    private void efetuarDepositoCorrente() throws IOException {
        AbstConta conta = verificarContaCorrente();
        AbstConta contaOrigem = verificarContaOrigem();
        verificarAgencia(contaOrigem);
        int valor = getValorTransacao();
        if (verificarSenha(contaOrigem)){
            conta.saldo = conta.saldo + valor;
            hashContaCorrente.replace(conta.getCPF(), conta);
        }

    }

    private void efetuarDepositoPoupanca() throws IOException{
        AbstConta conta = verificarContaPoupanca();
        AbstConta contaOrigem = verificarContaOrigem();
        verificarAgencia(contaOrigem);
        int valor = getValorTransacao();
        if (verificarSenha(contaOrigem)){
            conta.saldo = conta.saldo + valor;
            hashContaPoupanca.replace(conta.getCPF(), conta);
        }
    }

    public void efetuarSaque() throws NumberFormatException, IOException{
        System.out.println("Deseja efetuar o saque em uma conta CORRENTE ou POUPANCA?");
        System.out.println("1 - POUPANCA");
        System.out.println("2 - CORRENTE");
        int opcao = Integer.parseInt(reader.readLine());
        switch (opcao){
            case 1:
                efetuarSaquePoupanca();
            break;

            case 2:
                efetuarSaqueCorrente();
            break;

            default:
                System.out.println("Opcao invalida. Selecione novamente");
                efetuarSaque();
            break;
        }
    }

    private void efetuarSaqueCorrente() throws IOException {
        AbstConta conta = verificarContaCorrente();
        AbstConta contaOrigem = verificarContaOrigem();
        verificarAgencia(contaOrigem);
        int valor = getValorTransacao();
        if (verificarSenha(contaOrigem)){
            if(!verificarSaldo(conta, valor)){
                System.out.println("Saque nao pode ser concluido. Saldo insuficiente");
            } else {
                conta.saldo = conta.saldo - valor;
                hashContaCorrente.replace(conta.getCPF(), conta);
            }
        }
    }

    private void efetuarSaquePoupanca() throws NumberFormatException, IOException {
        AbstConta conta = verificarContaPoupanca();
        AbstConta contaOrigem = verificarContaOrigem();
        verificarAgencia(contaOrigem);
        int valor = getValorTransacao();
        if (verificarSenha(contaOrigem)){
            if(!verificarSaldo(conta, valor)){
                System.out.println("Saque nao pode ser concluido. Saldo insuficiente");
            } else {
                conta.saldo = conta.saldo - valor;
                hashContaPoupanca.replace(conta.getCPF(), conta);
            }
        }
    }

    private Boolean verificarSaldo(AbstConta conta, int valor){
        if (conta.saldo - valor < 0){
            return false;
        } else{
            return true;
        }
    }

    public void efetuarTransferencia() throws IOException{
        System.out.println("Escolha a transferencia desejada");
        System.out.println("1 - Poupanca para Poupanca");
        System.out.println("2 - Poupanca para Corrente");
        System.out.println("3 - Corrente para Poupanca");
        System.out.println("4 - Corrente para Corrente");
        int opcao = Integer.parseInt(reader.readLine());
        switch (opcao){
            case 1:
                efetuarTransferenciaPoupancaParaPoupanca();
            break;

            case 2:
                efetuarTransferenciaPoupancaParaCorrente();
            break;

            case 3:
                efetuarTransferenciaCorrenteParaPoupanca();
            break;

            case 4:
                efetuarTransferenciaCorrenteParaCorrente();
            break;

            default:
                System.out.println("Opcao invalida");
                efetuarTransferencia();
            break;
        }

    }
    
    private void efetuarTransferenciaPoupancaParaPoupanca() throws IOException{
        System.out.println("CONTA ORIGEM");
        AbstConta contaOrigem = verificarContaPoupanca();
        verificarAgencia(contaOrigem);
        AbstConta conta = verificarContaPoupanca();
        System.out.println("CONTA DESTINO");
        verificarAgencia(conta);
        int valor = getValorTransacao();
        if (!verificarSenha(contaOrigem)){
            System.out.println("Senha invalida");
            return;
        }
        if (!verificarSaldo(contaOrigem, valor)){
            System.out.println("Transferencia nao pode ser concluido. Saldo insuficiente");
            return;    
        } 
                contaOrigem.saldo = conta.saldo - valor;
                conta.saldo = conta.saldo + valor;
                hashContaPoupanca.replace(conta.getCPF(), conta);
                hashContaPoupanca.replace(contaOrigem.getCPF(), contaOrigem);     
    }

    private void efetuarTransferenciaCorrenteParaPoupanca() throws IOException{
        System.out.println("CONTA ORIGEM");
        AbstConta contaOrigem = verificarContaCorrente();
        verificarAgencia(contaOrigem);
        AbstConta conta = verificarContaPoupanca();
        System.out.println("CONTA DESTINO");
        verificarAgencia(conta);
        int valor = getValorTransacao();
        if (!verificarSenha(contaOrigem)){
            System.out.println("Senha invalida");
            return;
        }
        if (!verificarSaldo(contaOrigem, valor)){
            System.out.println("Transferencia nao pode ser concluido. Saldo insuficiente");
            return;    
        } 
        contaOrigem.saldo = conta.saldo - valor;
        conta.saldo = conta.saldo + valor;
        hashContaPoupanca.replace(conta.getCPF(), conta);
        hashContaCorrente.replace(contaOrigem.getCPF(), contaOrigem);

    }

    private void efetuarTransferenciaPoupancaParaCorrente() throws IOException{
        System.out.println("CONTA ORIGEM");
        AbstConta contaOrigem = verificarContaPoupanca();
        verificarAgencia(contaOrigem);
        System.out.println("CONTA DESTINO");
        AbstConta conta = verificarContaCorrente();
        verificarAgencia(conta);
        int valor = getValorTransacao();
        if (!verificarSenha(contaOrigem)){
            System.out.println("Senha invalida");
            return;
        }
        if (!verificarSaldo(contaOrigem, valor)){
            System.out.println("Transferencia nao pode ser concluido. Saldo insuficiente");
            return;    
        } 
        contaOrigem.saldo = conta.saldo - valor;
        conta.saldo = conta.saldo + valor;
        hashContaCorrente.replace(conta.getCPF(), conta);
        hashContaPoupanca.replace(contaOrigem.getCPF(), contaOrigem);
    }


    private void efetuarTransferenciaCorrenteParaCorrente() throws IOException{
        System.out.println("CONTA ORIGEM");
        AbstConta contaOrigem = verificarContaCorrente();
        verificarAgencia(contaOrigem);
        AbstConta conta = verificarContaCorrente();
        System.out.println("CONTA DESTINO");
        verificarAgencia(conta);
        int valor = getValorTransacao();
        if (!verificarSenha(contaOrigem)){
            System.out.println("Senha invalida");
            return;
        }
        if (!verificarSaldo(contaOrigem, valor)){
            System.out.println("Transferencia nao pode ser concluido. Saldo insuficiente");
            return;    
        }        
        contaOrigem.saldo = conta.saldo - valor;
        conta.saldo = conta.saldo + valor;
        hashContaCorrente.replace(conta.getCPF(), conta);
        hashContaCorrente.replace(contaOrigem.getCPF(), contaOrigem);
            
        
    }

    private Boolean verificarSenha(AbstConta conta) throws NumberFormatException, IOException{
        System.out.println("Insira sua senha");
        if (conta.getSenha() == Integer.parseInt(reader.readLine())){
            return true;
        } else {
            return false;
        }
    }

    private Boolean verificarAgencia(AbstConta conta) throws NumberFormatException, IOException{
        if (conta.getAgencia() == getAgencia()){
            return true;
        } else {
            System.out.println("Agencia Invalida");
            return verificarAgencia(conta);
        }
    }
    
}