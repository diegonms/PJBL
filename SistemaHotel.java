import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.time.LocalDate;
import java.util.Random;

public class SistemaHotel extends JFrame {
    private JTextField campoNome, campoCpf, campoEmail, campoDataNascimento;
    private JComboBox<String> comboTipoQuarto;
    private JTextField campoCheckin, campoCheckout;
    private JTextField campoNumeroCartao, campoNomeTitular, campoCvv, campoValidade;

    public SistemaHotel() {
        configurarJanela();
        mostrarTelaInicial();
    }

    private void configurarJanela() {
        setTitle("Sistema de Reservas do Hotel");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void mostrarTelaInicial() {
        JPanel painelInicial = new JPanel(new BorderLayout());
        painelInicial.setBackground(new Color(240, 240, 240));

        JLabel logo = new JLabel("Logo do Hotel"); // Adicione seu logotipo aqui
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        painelInicial.add(logo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 1, 10, 10));
        painelBotoes.setBackground(new Color(240, 240, 240));

        JButton botaoReserva = new JButton("Realizar Reserva");
        estilizarBotao(botaoReserva);
        botaoReserva.addActionListener(e -> mostrarTelaCadastroCliente());

        JButton botaoAdm = new JButton("Visualizar Reservas (ADM)");
        estilizarBotao(botaoAdm);
        botaoAdm.addActionListener(e -> mostrarTelaVisualizarReservas());

        painelBotoes.add(botaoReserva);
        painelBotoes.add(botaoAdm);

        painelInicial.add(painelBotoes, BorderLayout.CENTER);

        setContentPane(painelInicial);
        revalidate();
        repaint();
    }

    private void mostrarTelaVisualizarReservas() {
        getContentPane().removeAll();
        setSize(600, 500);

        JPanel painelVisualizar = new JPanel(new BorderLayout());
        painelVisualizar.setBackground(new Color(240, 240, 240));

        JTextArea textAreaReservas = new JTextArea(20, 50);
        textAreaReservas.setEditable(false);
        carregarReservas(textAreaReservas);

        JScrollPane scrollPane = new JScrollPane(textAreaReservas);
        painelVisualizar.add(scrollPane, BorderLayout.CENTER);

        JButton botaoVoltar = new JButton("Voltar");
        estilizarBotao(botaoVoltar);
        botaoVoltar.addActionListener(e -> mostrarTelaInicial());

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(new Color(240, 240, 240));
        painelBotao.add(botaoVoltar);
        painelVisualizar.add(painelBotao, BorderLayout.SOUTH);

        setContentPane(painelVisualizar);
        revalidate();
        repaint();
    }

    private void carregarReservas(JTextArea textArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader("reserva.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                textArea.append(linha + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Erro ao carregar as reservas.");
        }
    }

    private void mostrarTelaCadastroCliente() {
        getContentPane().removeAll();
        setSize(500, 450);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(240, 240, 240));

        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        painelFormulario.setBackground(new Color(240, 240, 240));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel labelNome = new JLabel("Nome:");
        JLabel labelCpf = new JLabel("CPF:");
        JLabel labelEmail = new JLabel("Email:");
        JLabel labelDataNascimento = new JLabel("Data de Nascimento (DD-MM-AAAA):");

        campoNome = new JTextField();
        campoCpf = new JTextField();
        campoEmail = new JTextField();
        campoDataNascimento = new JTextField();

        painelFormulario.add(labelNome);
        painelFormulario.add(campoNome);
        painelFormulario.add(labelCpf);
        painelFormulario.add(campoCpf);
        painelFormulario.add(labelEmail);
        painelFormulario.add(campoEmail);
        painelFormulario.add(labelDataNascimento);
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
        revalidate();
        repaint();
    }

    private void avancarParaEscolhaQuarto() {
        try {
            String nome = campoNome.getText();
            String cpf = campoCpf.getText();
            String email = campoEmail.getText();
            LocalDate dataNascimento = LocalDate.parse(campoDataNascimento.getText());

            if (isMaiorDeIdade(dataNascimento)) {
                mostrarTelaEscolhaQuarto(nome, cpf, email, dataNascimento);
            } else {
                JOptionPane.showMessageDialog(this, "Você deve ser maior de 18 anos para fazer a reserva.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira uma data válida no formato DD-MM-AAAA.");
        }
    }

    private boolean isMaiorDeIdade(LocalDate dataNascimento) {
        return LocalDate.now().getYear() - dataNascimento.getYear() >= 18;
    }

    private void mostrarTelaEscolhaQuarto(String nome, String cpf, String email, LocalDate dataNascimento) {
        getContentPane().removeAll();
        setSize(500, 450);

        JPanel painelEscolhaQuarto = new JPanel(new BorderLayout());
        painelEscolhaQuarto.setBackground(new Color(240, 240, 240));

        JPanel painelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        painelFormulario.setBackground(new Color(240, 240, 240));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel labelTipoQuarto = new JLabel("Tipo de Quarto:");
        JLabel labelCheckin = new JLabel("Data de Check-in (DD-MM-AAAA):");
        JLabel labelCheckout = new JLabel("Data de Check-out (DD-MM-AAAA):");

        comboTipoQuarto = new JComboBox<>(new String[]{"Quarto Normal", "Quarto Família", "Quarto Suíte"});
        campoCheckin = new JTextField();
        campoCheckout = new JTextField();

        painelFormulario.add(labelTipoQuarto);
        painelFormulario.add(comboTipoQuarto);
        painelFormulario.add(labelCheckin);
        painelFormulario.add(campoCheckin);
        painelFormulario.add(labelCheckout);
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
        String tipoQuarto = (String) comboTipoQuarto.getSelectedItem();
        LocalDate checkin = LocalDate.parse(campoCheckin.getText());
        LocalDate checkout = LocalDate.parse(campoCheckout.getText());

        getContentPane().removeAll();
        setSize(500, 450);

        JPanel painelPagamento = new JPanel(new BorderLayout());
        painelPagamento.setBackground(new Color(240, 240, 240));

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.setBackground(new Color(240, 240, 240));

        JButton botaoPix = new JButton("Pagar com Pix");
        estilizarBotao(botaoPix);
        botaoPix.addActionListener(e -> {
            String codigoPix = gerarCodigoPix();
            JOptionPane.showMessageDialog(this, "Valor da reserva: R$ 1.000,00\nCódigo Pix: " + codigoPix);
            realizarReserva(nome, cpf, email, dataNascimento, tipoQuarto, checkin, checkout, "Pix", codigoPix);
        });

        JButton botaoCartao = new JButton("Pagar com Cartão");
        estilizarBotao(botaoCartao);
        botaoCartao.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Valor da reserva: R$ 1.000,00");
            mostrarTelaPagamentoCartao(nome, cpf, email, dataNascimento, tipoQuarto, checkin, checkout);
        });

        painelBotoes.add(botaoPix);
        painelBotoes.add(botaoCartao);

        painelPagamento.add(painelBotoes, BorderLayout.CENTER);

        setContentPane(painelPagamento);
        revalidate();
        repaint();
    }

    private void realizarReserva(String nome, String cpf, String email, LocalDate dataNascimento, String tipoQuarto, LocalDate checkin, LocalDate checkout, String pix, String codigoPix) {
    }

    private void mostrarTelaPagamentoCartao(String nome, String cpf, String email, LocalDate dataNascimento, String tipoQuarto, LocalDate checkin, LocalDate checkout) {
        getContentPane().removeAll();
        setSize(500, 450);

        JPanel painelCartao = new JPanel(new BorderLayout());
        painelCartao.setBackground(new Color(240, 240, 240));

        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        painelFormulario.setBackground(new Color(240, 240, 240));

        JLabel labelNumeroCartao = new JLabel("Número do Cartão:");
        JLabel labelNomeTitular = new JLabel("Nome do Titular:");
        JLabel labelCvv = new JLabel("CVV:");
        JLabel labelValidade = new JLabel("Validade (MM/AAAA):");

        campoNumeroCartao = new JTextField();
        campoNomeTitular = new JTextField();
        campoCvv = new JTextField();
        campoValidade = new JTextField();

        painelFormulario.add(labelNumeroCartao);
        painelFormulario.add(campoNumeroCartao);
        painelFormulario.add(labelNomeTitular);
        painelFormulario.add(campoNomeTitular);
        painelFormulario.add(labelCvv);
        painelFormulario.add(campoCvv);
        painelFormulario.add(labelValidade);
        painelFormulario.add(campoValidade);

        painelCartao.add(painelFormulario, BorderLayout.CENTER);

        JButton botaoFinalizar = new JButton("Finalizar Reserva");
        estilizarBotao(botaoFinalizar);
        botaoFinalizar.addActionListener(e -> {
            String numeroCartao = campoNumeroCartao.getText();
            String nomeTitular = campoNomeTitular.getText();
            String cvv = campoCvv.getText();
            String validade = campoValidade.getText();
            finalizarReserva(nome, cpf, email, dataNascimento, tipoQuarto, checkin, checkout, "Cartão", numeroCartao);
        });

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(new Color(240, 240, 240));
        painelBotao.add(botaoFinalizar);
        painelCartao.add(painelBotao, BorderLayout.SOUTH);

        setContentPane(painelCartao);
        revalidate();
        repaint();
    }

    private void finalizarReserva(String nome, String cpf, String email, LocalDate dataNascimento, String tipoQuarto, LocalDate checkin, LocalDate checkout, String metodoPagamento, String codigoPagamento) {
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
            e.printStackTrace();
        }
    }

    private String gerarCodigoPix() {
        Random random = new Random();
        return "PIX-" + random.nextInt(1000000);
    }

    private void estilizarBotao(JButton botao) {
        botao.setBackground(new Color(60, 60, 255));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaHotel().setVisible(true));
    }
}
