import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLogin() {
        setTitle("Admin Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        Color backgroundColor = Color.decode("#076386");
        Color buttonColor = new Color(220, 220, 220);
        Color textColor = Color.WHITE;

        // Set the same background color for the panel
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(textColor);
        usernameField = new JTextField("Enter your username");
        usernameField.setForeground(Color.GRAY); // Set default text color
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter your username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("Enter your username");
                }
            }
        });
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(textColor);
        passwordField = new JPasswordField("Enter your password");
        passwordField.setForeground(Color.GRAY); // Set default text color
        passwordField.setEchoChar((char) 0); // Make the text visible initially
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Enter your password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*'); // Set echo character for password
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("Enter your password");
                }
            }
        });
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.setBackground(buttonColor);
        loginButton.setForeground(backgroundColor);
        cancelButton.setBackground(buttonColor);
        cancelButton.setForeground(backgroundColor);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToAcceuil();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(cancelButton);

        // Use BorderLayout to center the panel
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    private void authenticate() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        if (AdminCredentials.authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            choix choix = new choix();
            choix.setVisible(true);
            dispose(); // Fermer la fenêtre de connexion admin
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Clear password field for security
        passwordField.setText("");
        passwordField.setEchoChar((char) 0);
    }

    private void backToAcceuil() {
        Acceuil acceuil = new Acceuil();
        acceuil.setVisible(true);
        dispose(); // Fermer la fenêtre de connexion admin
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminLogin adminLogin = new AdminLogin();
                adminLogin.setVisible(true);
            }
        });
    }
}
