package elvira.noelle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    private JTable table;

    public Main() {
        setTitle("Gestion de Collection d'Albums");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 700);
        setLocationRelativeTo(null);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Titre", "Artiste"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Boutons
        JButton ajouterButton = new JButton("Ajouter");
        JButton supprimerButton = new JButton("Supprimer");
        JButton modifierButton = new JButton("Modifier");
        JButton rafraichirButton = new JButton("Rafraîchir");

        ajouterButton.addActionListener(this::ajouterAlbum);
        supprimerButton.addActionListener(this::supprimerAlbum);
        modifierButton.addActionListener(this::modifierAlbum);
        rafraichirButton.addActionListener(this::rafraichirListe);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ajouterButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(rafraichirButton);

        // Layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        rafraichirListe(null);
    }

    private void ajouterAlbum(ActionEvent e) {
        String titre = JOptionPane.showInputDialog(this, "Titre de l'album:");
        String artiste = JOptionPane.showInputDialog(this, "Artiste de l'album:");

        if (titre != null && artiste != null) {
            try {
                AlbumVerification.ajouterAlbum(new Album(titre, artiste));
                rafraichirListe(null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerAlbum(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String titre = (String) tableModel.getValueAt(selectedRow, 0);
            try {
                AlbumVerification.supprimerAlbum(titre);
                rafraichirListe(null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un album.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void modifierAlbum(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String ancienTitre = (String) tableModel.getValueAt(selectedRow, 0);
            String ancienArtiste = (String) tableModel.getValueAt(selectedRow, 1);

            String nouveauTitre = JOptionPane.showInputDialog(this, "Nouveau titre :", ancienTitre);
            String nouveauArtiste = JOptionPane.showInputDialog(this, "Nouvel artiste :", ancienArtiste);

            if (nouveauTitre != null && nouveauArtiste != null) {
                try {
                	AlbumVerification.modifierAlbum(ancienTitre, new Album(nouveauTitre, nouveauArtiste));
                    rafraichirListe(null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un album à modifier.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void rafraichirListe(ActionEvent e) {
        try {
            List<Album> albums = AlbumVerification.lireAlbums();
            tableModel.setRowCount(0); // Clear existing rows
            for (Album album : albums) {
                tableModel.addRow(new Object[]{album.getTitre(), album.getArtiste()});
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
