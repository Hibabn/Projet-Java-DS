import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Acceuil extends JFrame implements ActionListener {

    JButton B1 = new JButton("Admin");
    JButton B2 = new JButton("User");
    JLabel welcomeLabel = new JLabel("Welcome:");

    public Acceuil() {
        super("Acceuil");
        setSize(400, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Définition des couleurs
        Color backgroundColor = Color.decode("#076386");
        Color buttonColor = new Color(220, 220, 220); // Couleur gris clair pour les boutons
        Color textColor = Color.WHITE; // Couleur du texte

        // Configuration du label "Welcome"
        welcomeLabel.setBounds(20, 10, 200, 30);
        welcomeLabel.setForeground(textColor); // Texte blanc
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel);

        // Configuration des boutons
        int buttonWidth = 150;
        int buttonHeight = 30;

        B1.setBounds(125, 40, buttonWidth, buttonHeight);
        B1.setBackground(buttonColor);
        B1.setForeground(backgroundColor); // Utilisation de la couleur de fond comme couleur de texte
        B1.setFocusPainted(false);
        B1.setFont(new Font("Arial", Font.PLAIN, 14));
        B1.addActionListener(this);
        add(B1);

        B2.setBounds(125, 90, buttonWidth, buttonHeight);
        B2.setBackground(buttonColor);
        B2.setForeground(backgroundColor); // Utilisation de la couleur de fond comme couleur de texte
        B2.setFocusPainted(false);
        B2.setFont(new Font("Arial", Font.PLAIN, 14));
        B2.addActionListener(this);
        add(B2);

        // Configuration de la couleur de fond de la JFrame
        getContentPane().setBackground(backgroundColor);

        setLayout(null);
    }

    public static void main(String[] args) {
        Acceuil a = new Acceuil();
        a.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == B2) {
            Formulaire f = new Formulaire();
            f.setVisible(true);
            this.setVisible(false);
        } else {
            AdminLogin adminLogin = new AdminLogin();
            adminLogin.setVisible(true);
            this.setVisible(false);
        }
    }
}
