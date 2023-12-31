import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Specialite extends JFrame {
    private static final Color backgroundColor = Color.decode("#076386");
    private static final Color buttonColor = new Color(220, 220, 220);
    private static final Color textColor = Color.WHITE;

    public Specialite() {
        JButton back = new JButton("Back to AdminSpace");
        back.setBounds(300, 385, 200, 40);
        back.setFont(new Font("Arial", Font.ITALIC, 16));
        back.setBackground(buttonColor);
        back.setForeground(backgroundColor);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel specialiteLabel = new JLabel("Select by Specialite:");
        specialiteLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        // Increase the width for better visibility
        specialiteLabel.setBounds(350, 20, 200, 30);
        specialiteLabel.setForeground(textColor);

        String[] specialites = { "", "Informatique et Multimedia", "Big Data", "Communication et Multimedia",
                "Audio visuel", "Jeux Video", "Coco-3D" };
        JComboBox<String> specialiteComboBox = new JComboBox<>(specialites);
        specialiteComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        specialiteComboBox.setBounds(550, 20, 180, 30);
        specialiteComboBox.setForeground(Color.BLACK);

        JButton showClientsButton = new JButton("Show Clients");
        showClientsButton.setBounds(300, 70, 200, 40);
        showClientsButton.setFont(new Font("Arial", Font.BOLD, 18));
        showClientsButton.setBackground(buttonColor);
        showClientsButton.setForeground(backgroundColor);

        add(specialiteLabel);
        add(specialiteComboBox);
        add(showClientsButton);
        add(back);

        JTable clientTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.setBounds(10, 125, 760, 250);
        add(scrollPane);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                choix a = new choix();
                a.setVisible(true);
            }
        });

        showClientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedSpecialite = (String) specialiteComboBox.getSelectedItem();
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    String selectQuery;

                    if (selectedSpecialite.isEmpty()) {
                        selectQuery = "SELECT * FROM inscription";
                    } else {
                        selectQuery = "SELECT * FROM inscription WHERE specialité = ?";
                    }

                    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                        if (!selectedSpecialite.isEmpty()) {
                            preparedStatement.setString(1, selectedSpecialite);
                        }

                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            DefaultTableModel tableModel = new DefaultTableModel();
                            tableModel.addColumn("Last Name");
                            tableModel.addColumn("First Name");
                            tableModel.addColumn("Speciality");
                            tableModel.addColumn("Club");

                            while (resultSet.next()) {
                                String nom = resultSet.getString("nom");
                                String prenom = resultSet.getString("prenom");
                                String spec = resultSet.getString("specialité");
                                String club = resultSet.getString("club");
                                tableModel.addRow(new Object[] { nom, prenom, spec, club });
                            }

                            clientTable.setModel(tableModel);
                            revalidate();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setSize(800, 500);
        getContentPane().setBackground(backgroundColor);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Vérification de la connexion à la base de données
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    System.out.println("Connexion à la base de données réussie.");
                } else {
                    System.err.println("La connexion à la base de données a échoué.");
                    // Arrêter l'exécution si la connexion a échoué
                    return;
                }

                // Création de l'instance de la classe Specialite
                new Specialite();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
