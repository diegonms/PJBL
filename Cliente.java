abstract class Cliente {
    private String cpf;
    private String nome;
    private String tempoEstadia;

    public Cliente (String cpf, String nome, String tempoEstadia) {
        this.cpf = cpf;
        this.nome = nome;
        this.tempoEstadia = tempoEstadia;
    }
}

class ClienteVIP extends Cliente {
    protected double pontos;

    public ClienteVIP(String cpf, String nome, String tempoEstadia, double pontos) {
        super(cpf, nome, tempoEstadia);
        this.pontos = pontos;
    }
}

class ClienteDiamante extends Cliente {
    protected double pontos;
    protected double desconto;

    public ClienteDiamante(String cpf, String nome, String tempoEstadia, double pontos, double desconto) {
        super(cpf, nome, tempoEstadia);
        this.pontos = pontos;
        this.desconto = desconto;
    }
}

