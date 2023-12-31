import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Club extends JFrame {
    private static final Color backgroundColor = Color.decode("#076386");
    private static final Color buttonColor = new Color(220, 220, 220);
    private static final Color textColor = Color.WHITE;

    public Club() {
        JButton back = new JButton("Back to AdminSpace");
        back.setBounds(300, 385, 200, 40);
        back.setFont(new Font("Arial", Font.ITALIC, 16));
        back.setBackground(buttonColor);
        back.setForeground(backgroundColor);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel clubLabel = new JLabel("Select by Club:");
        clubLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        clubLabel.setBounds(420, 20, 150, 30);
        clubLabel.setForeground(textColor);

        String[] clubs = { "", "Orenda Junior Entreprise", "Boubli", "Robotique", "Club Isamm Microsoft",
                "Jeunes ingenieurs isamm", "Radio", "Engineers Spark Isamm" };
        JComboBox<String> clubComboBox = new JComboBox<>(clubs);
        clubComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        clubComboBox.setBounds(550, 20, 180, 30);
        clubComboBox.setForeground(Color.BLACK);

        JButton showClientsButton = new JButton("Show Clients");
        showClientsButton.setBounds(300, 70, 200, 40);
        showClientsButton.setFont(new Font("Arial", Font.BOLD, 18));
        showClientsButton.setBackground(buttonColor);
        showClientsButton.setForeground(backgroundColor);

        add(clubLabel);
        add(clubComboBox);
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
                SwingUtilities.invokeLater(() -> new choix());
            }
        });

        showClientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedClub = (String) clubComboBox.getSelectedItem();
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    String selectQuery;

                    if (selectedClub.isEmpty()) {
                        selectQuery = "SELECT * FROM inscription";
                    } else {
                        selectQuery = "SELECT * FROM inscription WHERE club = ?";
                    }

                    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                        if (!selectedClub.isEmpty()) {
                            preparedStatement.setString(1, selectedClub);
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
                    } finally {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
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

                // Création de l'instance de la classe Club
                new Club();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
