import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewStudentsForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, deleteButton, updateButton;

    public ViewStudentsForm() {
        setTitle("Student Management System - View Students");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel (Search Bar)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search 🔍");

        topPanel.add(new JLabel("Search (ID or Name):"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // Table Setup
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null); // Prevents direct table editing

        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Department");

        loadStudentsFromDatabase(""); // Load all students initially

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel (Buttons)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        addButton = new JButton("➕ Add Student");
        deleteButton = new JButton("✖️ Delete Selected");
        updateButton = new JButton("✏️ Update Selected");

        addButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        deleteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        updateButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(updateButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Event Listeners
        searchButton.addActionListener(e -> loadStudentsFromDatabase(searchField.getText()));
        addButton.addActionListener(e -> {
            new AddStudentForm().setVisible(true);
            refreshTable();
        });
        deleteButton.addActionListener(e -> deleteStudent());
        updateButton.addActionListener(e -> updateStudent());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double-click for update
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int studentId = (int) table.getValueAt(selectedRow, 0);
                        new UpdateStudentForm(studentId).setVisible(true);
                        refreshTable();
                    }
                }
            }
        });
    }

    private void loadStudentsFromDatabase(String searchQuery) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM students WHERE id LIKE ? OR phone LIKE ?")) {

            stmt.setString(1, "%" + searchQuery + "%");
            stmt.setString(2, "%" + searchQuery + "%");

            ResultSet rs = stmt.executeQuery();
            tableModel.setRowCount(0);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("department")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int studentId = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this student?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE id = ?")) {

                stmt.setInt(1, studentId);
                int rowsDeleted = stmt.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                    loadStudentsFromDatabase(""); // Refresh the table after deletion
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting student: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int studentId = (int) table.getValueAt(selectedRow, 0);
        new UpdateStudentForm(studentId).setVisible(true);
        refreshTable();
    }

    private void refreshTable() {
        SwingUtilities.invokeLater(() -> loadStudentsFromDatabase(""));
    }
}
