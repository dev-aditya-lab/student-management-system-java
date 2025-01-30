import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateStudentForm extends JFrame {

    private JTextField txtName, txtEmail, txtPhone, txtDepartment;
    private JButton btnUpdate, btnClear;
    private int studentId;

    public UpdateStudentForm(int studentId) {
        this.studentId = studentId;

        setTitle("Update Student");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Initialize components
        JLabel lblName = new JLabel("Student Name:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblPhone = new JLabel("Phone:");
        JLabel lblDepartment = new JLabel("Department:");

        txtName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtDepartment = new JTextField();

        btnUpdate = new JButton("Update");
        btnClear = new JButton("Clear");

        // Add components to the frame
        add(lblName);
        add(txtName);
        add(lblEmail);
        add(txtEmail);
        add(lblPhone);
        add(txtPhone);
        add(lblDepartment);
        add(txtDepartment);
        add(btnUpdate);
        add(btnClear);

        // Load current student data
        loadStudentData();

        // Button Actions
        btnUpdate.addActionListener(e -> updateStudentInDatabase());
        btnClear.addActionListener(e -> clearFields());
    }

    private void loadStudentData() {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM students WHERE id = ?")) {

            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("name"));
                txtEmail.setText(rs.getString("email"));
                txtPhone.setText(rs.getString("phone"));
                txtDepartment.setText(rs.getString("department"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading student data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudentInDatabase() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String department = txtDepartment.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("UPDATE students SET name = ?, email = ?, phone = ?, department = ? WHERE id = ?")) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, department);
            pst.setInt(5, studentId);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
                dispose();  // Close the update form
            } else {
                JOptionPane.showMessageDialog(this, "Error updating student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating student in database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtDepartment.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateStudentForm(1).setVisible(true));
    }
}
