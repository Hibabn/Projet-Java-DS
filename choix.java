import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class choix extends JFrame implements ActionListener {

    JRadioButton B1 = new JRadioButton("Club");
    JRadioButton B2 = new JRadioButton("Specialité");
    ButtonGroup g = new ButtonGroup();
    JButton backToAcceuilButton = new JButton("Back");

    public choix() {
        super("Choix");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Définition des couleurs
        Color backgroundColor = Color.decode("#076386");

        // Create a JPanel and set its background color
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(backgroundColor);

        B1.addActionListener(this);
        B2.addActionListener(this);
        backToAcceuilButton.addActionListener(this);

        g.add(B1);
        g.add(B2);

        // Set the background color for each component
        B1.setBackground(backgroundColor);
        B1.setForeground(Color.WHITE);
        B1.setFocusPainted(false);

        B2.setBackground(backgroundColor);
        B2.setForeground(Color.WHITE);
        B2.setFocusPainted(false);

        backToAcceuilButton.setBackground(backgroundColor);
        backToAcceuilButton.setForeground(Color.WHITE);
        backToAcceuilButton.setFocusPainted(false);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 0, 0)); // Reduce vertical spacing between components
        buttonsPanel.add(B1);
        buttonsPanel.add(B2);

        buttonsPanel.setBackground(backgroundColor);

        p.add(buttonsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backToAcceuilButton);

        bottomPanel.setBackground(backgroundColor);

        p.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(p);
        setLocationRelativeTo(null); // Center the JFrame on the screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            choix c = new choix();
            c.setVisible(true);
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == B1) {
            Club cl = new Club();
            cl.setVisible(true);
            this.setVisible(false);
        } else if (e.getSource() == B2) {
            Specialite s = new Specialite();
            s.setVisible(true);
            this.setVisible(false);
        } else if (e.getSource() == backToAcceuilButton) {
            Acceuil acceuil = new Acceuil();
            acceuil.setVisible(true);
            dispose(); // Fermer la fenêtre "choix"
        }
    }
}
