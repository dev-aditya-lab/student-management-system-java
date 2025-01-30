import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class AboutPage extends JFrame {

    public AboutPage() {
        setTitle("About - Student Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Section
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Profile Picture
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\ad112\\Coding\\OopsProject\\StudentManagementSystem\\src\\profile.jpg");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel profileLabel = new JLabel(scaledIcon);
        profileLabel.setPreferredSize(new Dimension(100, 100)); // Adjust size

        // Author Details
        JPanel authorDetailsPanel = new JPanel();
        authorDetailsPanel.setLayout(new BoxLayout(authorDetailsPanel, BoxLayout.Y_AXIS));

        JLabel authorName = new JLabel("Author: Aditya Kumar Gupta");
        authorName.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel authorContact = new JLabel("Email: ad1123itya@gmail.com");
        JLabel authorContact2 = new JLabel("Phone: XXXXXXXXXX");

        authorDetailsPanel.add(authorName);
        authorDetailsPanel.add(authorContact);
        authorDetailsPanel.add(authorContact2);

        headerPanel.add(profileLabel, BorderLayout.WEST);
        headerPanel.add(authorDetailsPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Subject and Project Description Section
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Project Title and Subject
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2, 1));

        JLabel projectTitle = new JLabel("Project: Student Management System");
        projectTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel projectSubject = new JLabel("Subject: Object Oriented Programming System");
        projectSubject.setFont(new Font("Arial", Font.PLAIN, 14));

        titlePanel.add(projectTitle);
        titlePanel.add(projectSubject);

        // Project Description
        JTextArea projectDescription = new JTextArea();
        projectDescription.setText(
                "This project is a Student Management System developed using Java Swing. "
                        + "It allows users to add, view, update, and delete student records.\n\n"
                        + "The system provides an intuitive GUI with real-time database integration "
                        + "using MySQL, ensuring a smooth user experience for managing student data. \n\n"
                        + "Authentication is implemented for secure access, and the system is designed "
                        + "to follow Object-Oriented Programming principles."
        );
        projectDescription.setWrapStyleWord(true);
        projectDescription.setLineWrap(true);
        projectDescription.setCaretPosition(0);
        projectDescription.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(projectDescription);
        scrollPane.setPreferredSize(new Dimension(550, 100));

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Technologies Used Section
        JPanel techPanel = new JPanel();
        techPanel.setLayout(new GridLayout(5, 1));

        JLabel techLabel = new JLabel("Technologies Used:");
        techLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel tech1 = new JLabel("✔ Java (Swing) - GUI Development");
        JLabel tech2 = new JLabel("✔ MySQL - Database Management");
        JLabel tech3 = new JLabel("✔ JDBC - Database Connectivity");
        JLabel tech4 = new JLabel("✔ IntelliJ IDEA - Development Environment");
        JLabel tech5 = new JLabel("✔ DataGrip - Database Management Tool");

        techPanel.add(techLabel);
        techPanel.add(tech1);
        techPanel.add(tech2);
        techPanel.add(tech3);
        techPanel.add(tech4);
        techPanel.add(tech5);

        contentPanel.add(techPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Footer (Close Button)
        JPanel footerPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());  // Close the about page
        footerPanel.add(closeButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AboutPage().setVisible(true));
    }
}
