import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

public class SistemaHotel extends JFrame {
    private JTextField campoNome, campoCpf, campoEmail, campoDataNascimento;
    private JComboBox<String> comboTipoQuarto;
    private JTextField campoCheckin, campoCheckout;
    private JTextField campoNumeroCartao, campoNomeTitular, campoCvv, campoValidade;

    public SistemaHotel() {
        configurarJanela();
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

        JLabel logo = new JLabel(new ImageIcon("hotel_logo.png")); // Adicione seu logotipo aqui
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(logo, BorderLayout.NORTH);

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
        setVisible(true);
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
        JButton botaoCartao = new JButton("Pagar com Cartão");
        estilizarBotao(botaoPix);
        estilizarBotao(botaoCartao);

        botaoPix.addActionListener(e -> finalizarReserva(nome, cpf, email, dataNascimento, tipoQuarto, checkin, checkout, "Pix", gerarCodigoPix()));
        botaoCartao.addActionListener(e -> mostrarTelaPagamentoCartao(nome, cpf, email, dataNascimento, tipoQuarto, checkin, checkout));

        painelBotoes.add(botaoPix);
        painelBotoes.add(botaoCartao);

        painelPagamento.add(painelBotoes, BorderLayout.CENTER);
        setContentPane(painelPagamento);
        revalidate();
        repaint();
    }

    private void mostrarTelaPagamentoCartao(String nome, String cpf, String email, LocalDate dataNascimento, String tipoQuarto, LocalDate checkin, LocalDate checkout) {
        getContentPane().removeAll();
        setSize(500, 450);

        JPanel painelCartao = new JPanel(new BorderLayout());
        painelCartao.setBackground(new Color(240, 240, 240));

        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        painelFormulario.setBackground(new Color(240, 240, 240));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel labelNumeroCartao = new JLabel("Número do Cartão:");
        JLabel labelNomeTitular = new JLabel("Nome do Titular:");
        JLabel labelCvv = new JLabel("CVV:");
        JLabel labelValidade = new JLabel("Data de Validade:");

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

        JButton botaoFinalizar = new JButton("Finalizar Pagamento");
        estilizarBotao(botaoFinalizar);
        botaoFinalizar.addActionListener(e -> finalizarReserva(nome, cpf, email, dataNascimento, tipoQuarto, checkin, checkout, "Cartão", gerarNumeroReserva()));

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
        return "PIX-" + new Random().nextInt(999999);
    }

    private String gerarNumeroReserva() {
        return "RESERVA-" + new Random().nextInt(999999);
    }

    private void estilizarBotao(JButton botao) {
        botao.setBackground(new Color(63, 81, 181));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(10), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
    }

    public static void main(String[] args) {
        new SistemaHotel();
    }

    // Classe para borda arredondada
    class RoundedBorder extends AbstractBorder {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(new Color(63, 81, 181).darker());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
