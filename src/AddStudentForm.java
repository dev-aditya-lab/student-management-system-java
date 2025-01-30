import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.IDN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudentForm extends JFrame {
    private JTextField ID,nameField, emailField, phoneField, departmentField;
    private JButton addButton, cancelButton;

    public AddStudentForm() {
        setTitle("Add Student");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("Student ID:"));
        ID = new JTextField();
        panel.add(ID);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Department:"));
        departmentField = new JTextField();
        panel.add(departmentField);

        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        panel.add(addButton);
        panel.add(cancelButton);

        add(panel);

        // Button Actions
        addButton.addActionListener(e -> addStudentToDatabase());
        cancelButton.addActionListener(e -> dispose());
    }

    private void addStudentToDatabase() {
        int ID = Integer.parseInt(this.ID.getText());
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String department = departmentField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO students (ID, name, email, phone, department) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, department);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
