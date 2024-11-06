import java.io.*;
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
    public QuartoNormal(int numero, boolean disponivel, int valorDiaria) {
        super("Normal", numero, disponivel, valorDiaria);
    }

    @Override
    public double calcularValorTotal(int dias) {
        return dias * 100;
    }
}

class QuartoFamilia extends Quarto {
    public QuartoFamilia(int numero, boolean disponivel, int valorDiaria) {
        super("Familia", numero, disponivel, valorDiaria);
    }

    @Override
    public double calcularValorTotal(int dias) {
        return dias * 200;
    }
}

class QuartoSuite extends Quarto {
    public QuartoSuite(int numero, boolean disponivel, int valorDiaria) {
        super("Suite", numero, disponivel, valorDiaria);
    }

    @Override
    public double calcularValorTotal(int dias) {
        return dias * 500;
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
}

class ClienteDiamante extends Cliente {
    private double pontos;
    private double desconto;

    public ClienteDiamante(String cpf, String nome, double pontos, double desconto) {
        super(cpf, nome);
        this.pontos = pontos;
        this.desconto = desconto;
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

class LeitorDeArquivos {
    public List<Quarto> lerQuartos(String caminhoArquivo) throws IOException {
        List<Quarto> quartos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                String tipo = dados[0];
                int numero = Integer.parseInt(dados[1]);
                boolean disponivel = Boolean.parseBoolean(dados[2]);
                int valorDiaria = Integer.parseInt(dados[3]);

                Quarto quarto;
                switch (tipo) {
                    case "Normal":
                        quarto = new QuartoNormal(numero, disponivel, valorDiaria);
                        break;
                    case "Familia":
                        quarto = new QuartoFamilia(numero, disponivel, valorDiaria);
                        break;
                    case "Suite":
                        quarto = new QuartoSuite(numero, disponivel, valorDiaria);
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de quarto inválido: " + tipo);
                }
                quartos.add(quarto);
            }
        }
        return quartos;
    }

    public List<Cliente> lerClientes(String caminhoArquivo) throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                String tipo = dados[0];
                String cpf = dados[1];
                String nome = dados[2];
                double pontos = Double.parseDouble(dados[3]);

                Cliente cliente;
                if (tipo.equals("VIP")) {
                    cliente = new ClienteVIP(cpf, nome, pontos);
                } else if (tipo.equals("Diamante")) {
                    double desconto = Double.parseDouble(dados[4]);
                    cliente = new ClienteDiamante(cpf, nome, pontos, desconto);
                } else {
                    throw new IllegalArgumentException("Tipo de cliente inválido: " + tipo);
                }
                clientes.add(cliente);
            }
        }
        return clientes;
    }
}

public class SistemaHotel {
    public static void main(String[] args) {
        try {
            Hotel hotel = new Hotel();
            LeitorDeArquivos leitor = new LeitorDeArquivos();

            List<Quarto> quartos = leitor.lerQuartos("quartos.txt");
            List<Cliente> clientes = leitor.lerClientes("clientes.txt");

            for (Quarto quarto : quartos) {
                hotel.adicionarQuarto(quarto);
            }

            for (Cliente cliente : clientes) {
                hotel.cadastrarCliente(cliente);
            }

            Estadia estadia = new Estadia(5);
            Pagamento pagamento = new Pagamento("Cartão de Crédito");

            hotel.criarReserva(clientes.get(0), quartos.get(0), estadia, pagamento);
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivos: " + e.getMessage());
        }
    }
}