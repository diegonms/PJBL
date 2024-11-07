import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sistemahoteis extends JFrame {
    private JTextField campoNome, campoCpf, campoEmail, campoDataNascimento;
    private JComboBox<String> comboTipoQuarto;
    private JTextField campoCheckin, campoCheckout;
    private GerenciadorHotel gerenciadorHotel;

    public Sistemahoteis() {
        configurarJanela();
        gerenciadorHotel = new GerenciadorHotel();
        mostrarTelaCadastroCliente();
    }

    private void configurarJanela() {
        setTitle("Sistema de Reservas do Hotel");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void mostrarTelaCadastroCliente() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(240, 240, 240));

        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        painelFormulario.setBackground(new Color(240, 240, 240));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        campoNome = new JTextField();
        campoCpf = new JTextField();
        campoEmail = new JTextField();
        campoDataNascimento = new JTextField();

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);
        painelFormulario.add(new JLabel("CPF:"));
        painelFormulario.add(campoCpf);
        painelFormulario.add(new JLabel("Email:"));
        painelFormulario.add(campoEmail);
        painelFormulario.add(new JLabel("Data de Nascimento (DD-MM-AAAA):"));
        painelFormulario.add(campoDataNascimento);

        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);

        JButton botaoAvancar = new JButton("Avançar");
        estilizarBotao(botaoAvancar);
        botaoAvancar.addActionListener(e -> avancarParaEscolhaQuarto());

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(new Color(240, 240, 240));
        painelBotao.add(botaoAvancar);
        painelPrincipal.add(painelBotao, BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
        setVisible(true);
    }

    private void avancarParaEscolhaQuarto() {
        try {
            String nome = campoNome.getText();
            String cpf = campoCpf.getText();
            String email = campoEmail.getText();
            LocalDate dataNascimento = LocalDate.parse(campoDataNascimento.getText());

            if (LocalDate.now().getYear() - dataNascimento.getYear() >= 18) {
                mostrarTelaEscolhaQuarto(nome, cpf, email, dataNascimento);
            } else {
                JOptionPane.showMessageDialog(this, "Você deve ser maior de 18 anos para fazer a reserva.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira uma data válida no formato DD-MM-AAAA.");
        }
    }

    private void mostrarTelaEscolhaQuarto(String nome, String cpf, String email, LocalDate dataNascimento) {
        getContentPane().removeAll();
        setSize(500, 450);

        JPanel painelEscolhaQuarto = new JPanel(new BorderLayout());
        painelEscolhaQuarto.setBackground(new Color(240, 240, 240));

        JPanel painelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        painelFormulario.setBackground(new Color(240, 240, 240));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        comboTipoQuarto = new JComboBox<>(new String[]{"Normal", "Familia", "Suite"});
        campoCheckin = new JTextField();
        campoCheckout = new JTextField();

        painelFormulario.add(new JLabel("Tipo de Quarto:"));
        painelFormulario.add(comboTipoQuarto);
        painelFormulario.add(new JLabel("Data de Check-in (DD-MM-AAAA):"));
        painelFormulario.add(campoCheckin);
        painelFormulario.add(new JLabel("Data de Check-out (DD-MM-AAAA):"));
        painelFormulario.add(campoCheckout);

        painelEscolhaQuarto.add(painelFormulario, BorderLayout.CENTER);

        JButton botaoAvancar = new JButton("Avançar");
        estilizarBotao(botaoAvancar);
        botaoAvancar.addActionListener(e -> avancarParaPagamento(nome, cpf, email, dataNascimento));

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(new Color(240, 240, 240));
        painelBotao.add(botaoAvancar);
        painelEscolhaQuarto.add(painelBotao, BorderLayout.SOUTH);

        setContentPane(painelEscolhaQuarto);
        revalidate();
        repaint();
    }

    private void avancarParaPagamento(String nome, String cpf, String email, LocalDate dataNascimento) {
        String tipoQuartoSelecionado = (String) comboTipoQuarto.getSelectedItem();
        LocalDate checkin = LocalDate.parse(campoCheckin.getText());
        LocalDate checkout = LocalDate.parse(campoCheckout.getText());

        getContentPane().removeAll();
        setSize(500, 450);

        JPanel painelPagamento = new JPanel(new BorderLayout());
        painelPagamento.setBackground(new Color(240, 240, 240));

        JButton botaoPix = new JButton("Pagar com Pix");
        JButton botaoCartao = new JButton("Pagar com Cartão");

        estilizarBotao(botaoPix);
        estilizarBotao(botaoCartao);

        botaoPix.addActionListener(e -> finalizarReserva(nome, cpf, email, dataNascimento, tipoQuartoSelecionado, checkin, checkout, "Pix"));
        botaoCartao.addActionListener(e -> finalizarReserva(nome, cpf, email, dataNascimento, tipoQuartoSelecionado, checkin, checkout, "Cartão"));

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(botaoPix);
        painelBotoes.add(botaoCartao);
        painelPagamento.add(painelBotoes, BorderLayout.CENTER);

        setContentPane(painelPagamento);
        revalidate();
        repaint();
    }

    private void finalizarReserva(String nome, String cpf, String email, LocalDate dataNascimento, String tipoQuarto, LocalDate checkin, LocalDate checkout, String metodoPagamento) {
        String codigoPagamento = (metodoPagamento.equals("Pix") ? "PIX-" : "RESERVA-") + new Random().nextInt(999999);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reserva.txt", true))) {
            writer.write("Nome: " + nome);
            writer.write("\nCPF: " + cpf);
            writer.write("\nEmail: " + email);
            writer.write("\nData de Nascimento: " + dataNascimento);
            writer.write("\nTipo de Quarto: " + tipoQuarto);
            writer.write("\nCheck-in: " + checkin);
            writer.write("\nCheck-out: " + checkout);
            writer.write("\nMétodo de Pagamento: " + metodoPagamento);
            writer.write("\nCódigo de Pagamento: " + codigoPagamento + "\n\n");
            JOptionPane.showMessageDialog(this, "Reserva finalizada com sucesso!");
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar reserva: " + e.getMessage());
        }
    }

    private void estilizarBotao(JButton botao) {
        botao.setBackground(new Color(63, 81, 181));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setFocusPainted(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sistemahoteis::new);
    }
}

class GerenciadorHotel {
    private List<Quarto> quartos;
    private List<Cliente> clientes;

    public GerenciadorHotel() {
        try {
            LeitorDeArquivos leitor = new LeitorDeArquivos();
            quartos = leitor.lerQuartos("quartos.txt");
            clientes = leitor.lerClientes("clientes.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Quarto> getQuartosDisponiveis() {
        return quartos;
    }

    public List<Cliente> getClientes() {
        return clientes;
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
                        case "Normal" -> quarto = new QuartoNormal(numero, disponivel, valorDiaria);
                        case "Familia" -> quarto = new QuartoFamilia(numero, disponivel, valorDiaria);
                        case "Suite" -> quarto = new QuartoSuite(numero, disponivel, valorDiaria);
                        default -> throw new IllegalArgumentException("Tipo de quarto inválido: " + tipo);
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
class SistemaHotelException extends Exception {
    public SistemaHotelException(String message) {
        super(message);
        }
}

class QuartoIndisponivelException extends SistemaHotelException {
    public QuartoIndisponivelException(int numeroQuarto) {
        super("O quarto número " + numeroQuarto + " está indisponível para reserva.");
    }
}
