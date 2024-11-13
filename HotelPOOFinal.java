import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class HotelPOOFinal {

    public static void main(String[] args) {
        // Inicia a interface inicial do sistema
        new TelaInicial();
    }

    // Exceção personalizada para CPF inválido
    static class CPFInvalidoException extends Exception {
        public CPFInvalidoException() {
            super("CPF contém caracteres inválidos. Somente números são permitidos.");
        }
    }

    // Classe que representa uma reserva
    static class Reserva {
        private String nome;
        private String cpf;
        private LocalDate nascimento;
        private String tipoQuarto;
        private String checkin;
        private String checkout;
        private double valor;

        // Construtor para a criação de uma nova reserva
        public Reserva(String nome, String cpf, LocalDate nascimento, String tipoQuarto, String checkin, String checkout, double valor) {
            this.nome = nome;
            this.cpf = cpf;
            this.nascimento = nascimento;
            this.tipoQuarto = tipoQuarto;
            this.checkin = checkin;
            this.checkout = checkout;
            this.valor = valor;
        }

        // Método toString() para exibir as informações da reserva
        @Override
        public String toString() {
            return "Nome: " + nome + "\n" +
                    "CPF: " + cpf + "\n" +
                    "Data de Nascimento: " + nascimento + "\n" +
                    "Tipo de Quarto: " + tipoQuarto + "\n" +
                    "Data de Check-in: " + checkin + "\n" +
                    "Data de Check-out: " + checkout + "\n" +
                    "Valor: R$ " + valor + "\n" +
                    "------------------------\n";
        }
    }

    // Classe que manipula o arquivo de reservas
    static class ArquivoReserva {
        private static final String CAMINHO_ARQUIVO = "reservas.txt";  // Caminho do arquivo de reservas
        private static ArrayList<Reserva> reservasList = new ArrayList<>();

        // Método para salvar a reserva no arquivo
        public static void salvarReserva(Reserva reserva) {
            reservasList.add(reserva);  // Adiciona a reserva à lista em memória
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
                // Escreve as informações da reserva no arquivo
                writer.write(reserva.toString());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Erro ao gravar a reserva: " + e.getMessage());
            }
        }

        // Método para ler as reservas salvas no arquivo
        public static String lerReservas() {
            StringBuilder reservas = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    reservas.append(linha).append("\n");
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo de reservas: " + e.getMessage());
            }
            return reservas.toString();
        }

        // Retorna a lista de reservas armazenadas em memória
        public static ArrayList<Reserva> getReservasList() {
            return reservasList;
        }
    }

    // Classe abstrata para tela (utilizada para estruturação de diferentes telas)
    static class Tela {
        public void inicializarTela() {
        }
    }

    // Tela inicial do sistema (onde o usuário pode escolher entre fazer uma reserva ou acessar a área administrativa)
    static class TelaInicial extends Tela {
        public TelaInicial() {
            inicializarTela();
        }

        @Override
        public void inicializarTela() {
            JFrame frame = new JFrame("Sistema de Reservas de Hotel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.getContentPane().setBackground(Color.BLACK);  // Cor de fundo preta
            frame.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel("HOTEL POO", JLabel.CENTER);  // Título
            titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
            titleLabel.setForeground(Color.WHITE);  // Cor do texto (branca)
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            frame.add(titleLabel, BorderLayout.NORTH);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            panel.setBackground(Color.BLACK);

            // Botões de ação
            JButton btnReservar = new JButton("Realizar Reserva");
            btnReservar.setPreferredSize(new Dimension(200, 50));
            btnReservar.setBackground(new Color(34, 193, 195));  // Cor do botão
            btnReservar.setForeground(Color.WHITE);
            btnReservar.setFont(new Font("Arial", Font.BOLD, 16));
            btnReservar.setFocusPainted(false);
            btnReservar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            JButton btnAdm = new JButton("ADM");
            btnAdm.setPreferredSize(new Dimension(200, 50));
            btnAdm.setBackground(new Color(34, 193, 195));
            btnAdm.setForeground(Color.WHITE);
            btnAdm.setFont(new Font("Arial", Font.BOLD, 16));
            btnAdm.setFocusPainted(false);
            btnAdm.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            panel.add(btnReservar);
            panel.add(btnAdm);
            frame.add(panel, BorderLayout.CENTER);

            frame.setLocationRelativeTo(null);  // Centraliza a tela
            frame.setVisible(true);

            // Ações ao clicar nos botões
            btnReservar.addActionListener(e -> new TelaCadastro());
            btnAdm.addActionListener(e -> new TelaAdm());
        }
    }

    // Tela administrativa, onde o administrador pode visualizar todas as reservas realizadas
    static class TelaAdm extends Tela {
        public TelaAdm() {
            inicializarTela();
        }

        @Override
        public void inicializarTela() {
            JFrame frame = new JFrame("Área Administrativa");
            frame.setSize(600, 400);
            JTextArea textArea = new JTextArea(ArquivoReserva.lerReservas());
            textArea.setEditable(false);  // Não permite edição do texto
            frame.add(new JScrollPane(textArea));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    // Tela de cadastro de dados do cliente, como nome, CPF e data de nascimento
    static class TelaCadastro {
        public TelaCadastro() {
            JFrame frame = new JFrame("Cadastro");
            frame.setSize(600, 400);
            frame.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            // Componentes da tela
            JLabel nomeLabel = new JLabel("Nome:");
            JTextField nomeField = new JTextField(20);
            JLabel cpfLabel = new JLabel("CPF:");
            JTextField cpfField = new JTextField(20);
            JLabel nascimentoLabel = new JLabel("Data de Nascimento (DD/MM/AAAA):");
            JTextField nascimentoField = new JTextField(20);
            JButton btnContinuar = new JButton("Continuar");

            nomeField.setPreferredSize(new Dimension(250, 30));
            cpfField.setPreferredSize(new Dimension(250, 30));
            nascimentoField.setPreferredSize(new Dimension(250, 30));
            btnContinuar.setPreferredSize(new Dimension(200, 50));
            btnContinuar.setBackground(new Color(34, 193, 195));  // Cor do botão
            btnContinuar.setForeground(Color.WHITE);
            btnContinuar.setFont(new Font("Arial", Font.BOLD, 16));
            btnContinuar.setFocusPainted(false);
            btnContinuar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            // Organizando os componentes no layout
            gbc.gridx = 0;
            gbc.gridy = 0;
            frame.add(nomeLabel, gbc);

            gbc.gridx = 1;
            frame.add(nomeField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            frame.add(cpfLabel, gbc);

            gbc.gridx = 1;
            frame.add(cpfField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            frame.add(nascimentoLabel, gbc);

            gbc.gridx = 1;
            frame.add(nascimentoField, gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            frame.add(btnContinuar, gbc);

            // Ação ao clicar em continuar
            btnContinuar.addActionListener(e -> {
                try {
                    // Verifica se o CPF contém apenas números
                    String cpf = cpfField.getText().trim();
                    if (!cpf.matches("[0-9]+")) {
                        throw new CPFInvalidoException();  // Lança exceção se o CPF for inválido
                    }

                    // Valida a data de nascimento
                    LocalDate nascimento = LocalDate.parse(nascimentoField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (nascimento.isAfter(LocalDate.now().minusYears(18))) {
                        JOptionPane.showMessageDialog(frame, "Você deve ter pelo menos 18 anos para continuar.");
                    } else {
                        // Chama a próxima tela para escolher o quarto
                        new TelaEscolherQuarto(nomeField.getText(), cpfField.getText(), nascimento);
                        frame.dispose();  // Fecha a tela de cadastro
                    }
                } catch (CPFInvalidoException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Data inválida! Por favor, insira no formato DD/MM/AAAA.");
                }
            });

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    // Tela onde o usuário escolhe o tipo de quarto
    static class TelaEscolherQuarto {
        public TelaEscolherQuarto(String nome, String cpf, LocalDate nascimento) {
            JFrame frame = new JFrame("Escolha de Quarto");
            frame.setSize(600, 400);
            frame.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            // Cor de fundo da tela
            frame.getContentPane().setBackground(new Color(211, 211, 211));  // Cor cinza claro

            // Definição dos tipos de quartos
            JRadioButton quartoSimples = new JRadioButton("Quarto Simples (3 pessoas) - R$150/dia");
            JRadioButton quartoLuxo = new JRadioButton("Quarto Luxo (2 pessoas) - R$300/dia");
            JRadioButton quartoPremium = new JRadioButton("Quarto Premium (1 pessoa) - R$450/dia");

            // Agrupando os radio buttons para garantir que apenas um seja selecionado
            ButtonGroup grupoQuartos = new ButtonGroup();
            grupoQuartos.add(quartoSimples);
            grupoQuartos.add(quartoLuxo);
            grupoQuartos.add(quartoPremium);

            // Posicionando os radio buttons
            gbc.gridx = 0;
            gbc.gridy = 0;
            frame.add(quartoSimples, gbc);

            gbc.gridy = 1;
            frame.add(quartoLuxo, gbc);

            gbc.gridy = 2;
            frame.add(quartoPremium, gbc);

            // Botão para confirmar a escolha
            JButton btnConfirmar = new JButton("Confirmar");
            btnConfirmar.setPreferredSize(new Dimension(200, 50));
            btnConfirmar.setBackground(new Color(34, 193, 195)); // Cor azul
            btnConfirmar.setForeground(Color.WHITE);
            btnConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
            btnConfirmar.setFocusPainted(false);
            btnConfirmar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            frame.add(btnConfirmar, gbc);

            // Ação ao clicar no botão de confirmação
            btnConfirmar.addActionListener(e -> {
                String tipoQuarto = "";
                double valor = 0;

                // Define o tipo e valor do quarto selecionado
                if (quartoSimples.isSelected()) {
                    tipoQuarto = "Simples";
                    valor = 150;
                } else if (quartoLuxo.isSelected()) {
                    tipoQuarto = "Luxo";
                    valor = 300;
                } else if (quartoPremium.isSelected()) {
                    tipoQuarto = "Premium";
                    valor = 450;
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, selecione um quarto.");
                    return;
                }

                // Tela para inserção das datas de check-in e check-out
                JTextField checkinField = new JTextField("DD/MM/AAAA", 10);
                JTextField checkoutField = new JTextField("DD/MM/AAAA", 10);

                // Painel para as datas
                JPanel panelData = new JPanel();
                panelData.setLayout(new GridLayout(2, 2, 10, 10));
                panelData.setBackground(new Color(211, 211, 211));  // Cor cinza claro
                panelData.add(new JLabel("Data de Check-in:"));
                panelData.add(checkinField);
                panelData.add(new JLabel("Data de Check-out:"));
                panelData.add(checkoutField);

                // Exibe a janela para escolher as datas
                int option = JOptionPane.showConfirmDialog(frame, panelData, "Escolha datas", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option == JOptionPane.OK_OPTION) {
                    String checkin = checkinField.getText();
                    String checkout = checkoutField.getText();

                    // Criação da reserva
                    Reserva reserva = new Reserva(nome, cpf, nascimento, tipoQuarto, checkin, checkout, valor);

                    // Salva a reserva no arquivo
                    ArquivoReserva.salvarReserva(reserva);

                    // Exibe a confirmação
                    JOptionPane.showMessageDialog(frame, "Reserva confirmada!\n" + reserva);
                    frame.dispose();  // Fecha a tela de escolha de quarto
                    new TelaPagamento();  // Avança para a tela de pagamento
                }
            });

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    // Tela de pagamento
    static class TelaPagamento {
        public TelaPagamento() {
            JFrame frame = new JFrame("Tela de Pagamento");
            frame.setSize(600, 400);
            frame.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            // Cor de fundo da tela
            frame.getContentPane().setBackground(Color.GRAY);

            JLabel label = new JLabel("Escolha a forma de pagamento:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            frame.add(label, gbc);

            // Botões para escolher a forma de pagamento
            JButton btnPagamentoPix = new JButton("Pagamento via PIX");
            btnPagamentoPix.setPreferredSize(new Dimension(200, 50));
            btnPagamentoPix.setBackground(new Color(34, 193, 195));  // Cor azul
            btnPagamentoPix.setForeground(Color.WHITE);
            btnPagamentoPix.setFont(new Font("Arial", Font.BOLD, 16));
            btnPagamentoPix.setFocusPainted(false);
            btnPagamentoPix.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            JButton btnPagamentoCartao = new JButton("Pagamento via Cartão");
            btnPagamentoCartao.setPreferredSize(new Dimension(200, 50));
            btnPagamentoCartao.setBackground(new Color(34, 193, 195)); // Azul
            btnPagamentoCartao.setForeground(Color.WHITE);
            btnPagamentoCartao.setFont(new Font("Arial", Font.BOLD, 16));
            btnPagamentoCartao.setFocusPainted(false);
            btnPagamentoCartao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            // Posicionando os botões de pagamento
            gbc.gridy = 1;
            frame.add(btnPagamentoPix, gbc);

            gbc.gridy = 2;
            frame.add(btnPagamentoCartao, gbc);

            // Ações ao clicar nos botões de pagamento
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            btnPagamentoPix.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Pagamento via PIX realizado com sucesso!"));
            btnPagamentoCartao.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Pagamento via Cartão realizado com sucesso!"));
        }
    }
}