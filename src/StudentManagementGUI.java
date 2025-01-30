import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagementGUI extends JFrame {
    public StudentManagementGUI() {
        setTitle("Student Management System");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel title = new JLabel("Student Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(50, 50, 150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Buttons
        String[] buttonLabels = {"Add Student", "View Students", "Update Student", "Delete Student", "Exit", "LogOut","About"};
        JButton[] buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            buttons[i].setBackground(new Color(70, 130, 180));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 2;
            panel.add(buttons[i], gbc);
        }

        buttons[0].addActionListener(e -> new AddStudentForm().setVisible(true));
        buttons[1].addActionListener(e -> new ViewStudentsForm().setVisible(true));
        buttons[2].addActionListener(e -> new ViewStudentsForm().setVisible(true));
        buttons[3].addActionListener(e -> new ViewStudentsForm().setVisible(true));
        buttons[4].addActionListener(e -> System.exit(0));
        buttons[5].addActionListener(e -> {
            new AdminLoginForm().setVisible(true);
            dispose();
        });

        buttons[6].addActionListener(e -> new AboutPage().setVisible(true));

        JLabel footer = new JLabel("Developed by: Aditya Kr. Gupta (249011001185)", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 12));
        footer.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = buttonLabels.length + 1;;
        gbc.gridwidth = 2;
        panel.add(footer, gbc);

        add(panel);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new AdminLoginForm().setVisible(true));

        // Test Database Connection
        DatabaseManager.getConnection();

    }
}
