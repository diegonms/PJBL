import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class UserInputForm extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextField cpfField;
    private JButton proceedButton;

    public UserInputForm() {
        setTitle("Dados do Cliente");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        nameField = new JTextField(15);
        ageField = new JTextField(15);
        cpfField = new JTextField(15);
        proceedButton = new JButton("Prosseguir com Reserva");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Idade:"));
        panel.add(ageField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel());
        panel.add(proceedButton);

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String age = ageField.getText();
                String cpf = cpfField.getText();

                if (name.isEmpty() || age.isEmpty() || cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                } else {
                    new ReservationForm(name, age, cpf);
                    dispose(); // Fecha a janela atual
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserInputForm::new);
    }
}

