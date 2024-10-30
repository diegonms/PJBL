import java.util.ArrayList;
import java.util.List;

abstract class Quarto {
    private String classe;
    private int numero;
    private boolean disponivel;
    private int valorDiaria;

    public Quarto(String classe, int numero, boolean disponivel, int valorDiaria) {
        this.classe = classe;
        this.numero = numero;
        this.disponivel = disponivel;
        this.valorDiaria = valorDiaria;
    }

    public void reservar() {
        this.disponivel = false;
    }

    public void liberar() {
        this.disponivel = true;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public abstract double calcularValorTotal(int dias);
}

class QuartoNormal extends Quarto {
    public QuartoNormal(String classe, int numero,boolean disponivel, int valorDiaria) {
        super("Normal", 50, true, 100);
    }

    @Override
    public double calcularValorTotal(int dias) {
        return 100 * dias;
    }
}

class QuartoFamilia extends Quarto {
    public QuartoFamilia(String classe, int numero,boolean disponivel, int valorDiaria) {
        super("Familia", 51, true, 200);
    }

    @Override
    public double calcularValorTotal(int dias) {
        return 200 * dias;
    }
}

class QuartoSuite extends Quarto {
    public QuartoSuite(String classe, int numero,boolean disponivel, int valorDiaria) {
        super("Suite",52, true, 500);
    }

    @Override
    public double calcularValorTotal(int dias) {
        return 500 * dias;
    }
}

class Unidades {
    private String local;
    private List<String> locais = new ArrayList<>();

    public Unidades(String local) {
        this.local = local;
    }

    public void adicionarLocal(String novoLocal) {
        locais.add(novoLocal);
    }

    public List<String> getLocais() {
        return locais;
    }
}

class Estadia {
    private int dias;

    public Estadia(int dias) {
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }
}

abstract class Cliente {
    private String cpf;
    private String nome;

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

class ClienteVIP extends Cliente {
    private double pontos;

    public ClienteVIP(String cpf, String nome, double pontos) {
        super(cpf, nome);
        this.pontos = pontos;
    }

    public void adicionarPontos(double valor) {
        pontos += valor;
    }
}

class ClienteDiamante extends Cliente {
    private double pontos;
    private double desconto;

    public ClienteDiamante(String cpf, String nome, double pontos, double desconto) {
        super(cpf, nome);
        this.pontos = pontos;
        this.desconto = desconto;
    }

    public double aplicarDesconto(double valor) {
        return valor - (valor * desconto);
    }
}

class Pagamento {
    private String metodo;

    public Pagamento(String metodo) {
        this.metodo = metodo;
    }

    public void realizarPagamento(double valor) {
        System.out.println("Pagamento de R$" + valor + " realizado via " + metodo + ".");
    }
}

class Hotel {
    private List<Quarto> quartos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public void adicionarQuarto(Quarto quarto) {
        quartos.add(quarto);
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void criarReserva(Cliente cliente, Quarto quarto, Estadia estadia, Pagamento pagamento) {
        if (quarto.isDisponivel()) {
            quarto.reservar();
            double valorTotal = quarto.calcularValorTotal(estadia.getDias());
            pagamento.realizarPagamento(valorTotal);
            System.out.println("Reserva feita para " + cliente.getNome() + " no quarto " + quarto.getClass().getSimpleName());
        } else {
            System.out.println("Quarto indisponível.");
        }
    }
}

public class SistemaHotel {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        QuartoNormal q1 = new QuartoNormal("normal", 50, true, 500);
        QuartoFamilia q2 = new QuartoFamilia("familia", 51, true, 700);
        QuartoSuite q3 = new QuartoSuite("Suite", 53, true, 1000);

        hotel.adicionarQuarto(q1);
        hotel.adicionarQuarto(q2);
        hotel.adicionarQuarto(q3);

        ClienteVIP cliente1 = new ClienteVIP("12345678900", "Alice", 100);
        ClienteDiamante cliente2 = new ClienteDiamante("09876543211", "Bob", 500, 0.1);

        hotel.cadastrarCliente(cliente1);
        hotel.cadastrarCliente(cliente2);

        Estadia estadia = new Estadia(5);
        Pagamento pagamento = new Pagamento("Cartão de Crédito");

        hotel.criarReserva(cliente1, q1, estadia, pagamento);
        hotel.criarReserva(cliente2, q3, estadia, pagamento);
    }
}