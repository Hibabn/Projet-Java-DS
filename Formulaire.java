import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Formulaire extends JFrame implements ActionListener {

    private JLabel nomLabel = new JLabel("Nom");
    private JLabel prenomLabel = new JLabel("Prenom");
    private JLabel specialiteLabel = new JLabel("Specialité");
    private JLabel clubLabel = new JLabel("Club");
    private JTextField nomTextField = new JTextField("Enter your name");
    private JTextField prenomTextField = new JTextField("Enter your last name");
    private JComboBox<String> specialiteComboBox = new JComboBox<>();
    private JComboBox<String> clubComboBox = new JComboBox<>();
    private JButton inscriptionButton = new JButton("Inscription");
    private JButton annulerButton = new JButton("Annuler");

    public Formulaire() {
        super("Formulaire");
        setSize(500, 250); // Increased the size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ajout des éléments dans les JComboBox
        String[] specialites = { "", "Informatique et Multimedia", "Big Data", "Communication et Multimedia",
                "Audio visuel", "Jeux Video", "Coco-3D" };
        String[] clubs = { "", "Orenda Junior Entreprise", "Boubli", "Robotique", "Club Isamm Microsoft",
                "Jeunes ingenieurs isamm", "Radio", "Engineers Spark Isamm" };

        specialiteComboBox.setModel(new DefaultComboBoxModel<>(specialites));
        clubComboBox.setModel(new DefaultComboBoxModel<>(clubs));

        // Ajout des action listeners aux boutons
        inscriptionButton.addActionListener(this);
        annulerButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10)); // Increased the spacing

        panel.add(createStyledLabel(nomLabel));
        panel.add(createStyledTextField(nomTextField));
        panel.add(createStyledLabel(prenomLabel));
        panel.add(createStyledTextField(prenomTextField));
        panel.add(createStyledLabel(specialiteLabel));
        panel.add(createStyledComboBox(specialiteComboBox));
        panel.add(createStyledLabel(clubLabel));
        panel.add(createStyledComboBox(clubComboBox));
        panel.add(createStyledButton(inscriptionButton));
        panel.add(createStyledButton(annulerButton));

        Color backgroundColor = Color.decode("#076386");
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        setContentPane(panel);
    }

    private JLabel createStyledLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Increased the font size
        return label;
    }

    private JTextField createStyledTextField(JTextField textField) {
        textField.setText("Enter your name");
        textField.setForeground(Color.GRAY);
        textField.setFont(new Font("Arial", Font.PLAIN, 14)); // Increased the font size
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("Enter your name")
                        || textField.getText().equals("Enter your last name")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    if (textField.equals(nomTextField)) {
                        textField.setText("Enter your name");
                    } else if (textField.equals(prenomTextField)) {
                        textField.setText("Enter your last name");
                    }
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textField;
    }

    private JComboBox<String> createStyledComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14)); // Increased the font size
        return comboBox;
    }

    private JButton createStyledButton(JButton button) {
        Color buttonColor = new Color(220, 220, 220);
        Color textColor = Color.BLACK;

        button.setBackground(buttonColor);
        button.setForeground(textColor);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Formulaire formulaire = new Formulaire();
            formulaire.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inscriptionButton) {
            String nom = nomTextField.getText();
            String prenom = prenomTextField.getText();
            String specialite = (String) specialiteComboBox.getSelectedItem();
            String club = (String) clubComboBox.getSelectedItem();

            insererDonnees(nom, prenom, specialite, club);

            nomTextField.setText("Enter your name");
            prenomTextField.setText("Enter your last name");
            specialiteComboBox.setSelectedIndex(0);
            clubComboBox.setSelectedIndex(0);

        } else if (e.getSource() == annulerButton) {
            nomTextField.setText("Enter your name");
            prenomTextField.setText("Enter your last name");
            specialiteComboBox.setSelectedIndex(0);
            clubComboBox.setSelectedIndex(0);
        }
    }

    private void insererDonnees(String nom, String prenom, String specialite, String club) {
        try {
            // Informations de connexion à la base de données MySQL
            String url = "jdbc:mysql://localhost:3307/projet_java";
            String user = "root";
            String password = "hiba1009*";

            // Établir la connexion à la base de données
            try (Connection connection = DriverManager.getConnection(url, user, password)) {

                // Requête SQL d'insertion avec des paramètres préparés
                String sql = "INSERT INTO Inscription (nom, prenom, specialité, club) VALUES (?, ?, ?, ?)";

                // Préparer la requête
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, nom);
                    statement.setString(2, prenom);
                    statement.setString(3, specialite);
                    statement.setString(4, club);

                    // Exécuter la requête d'insertion
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Inscription réussie !");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription : " + ex.getMessage(), "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
