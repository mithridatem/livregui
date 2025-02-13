package Gui;

import Model.Genre;
import Repository.GenreRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class GenreForm extends JDialog {
    /*-------------------------------
                Attributs
    -------------------------------*/
    private JPanel jpGenreMain;
    private JTextField tfName;
    private JLabel jlGenre;
    private JLabel jlName;
    private JButton btAdd;

    /*-------------------------------
            Constructeur
    -------------------------------*/

    public GenreForm() {
        setTitle("Ajouter un genre");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(jpGenreMain);
        setMinimumSize(new Dimension(600, 400));
        setMaximumSize(new Dimension(600, 400));
        setModal(false);
        setVisible(true);
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addGenre();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /*-------------------------------
                Méthodes
    -------------------------------*/

    //Méthode pour ajouter un genre en BDD avec le formulaire
    public void addGenre() throws SQLException {
        if(!tfName.getText().isEmpty()) {
            Genre genre = new Genre(tfName.getText());
            boolean exists = GenreRepository.isExist(genre.getName());
            if(!exists) {
                GenreRepository.add(genre);
                JOptionPane.showMessageDialog(this,
                        "Le genre a été ajouté en BDD",
                        "Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "Le genre existe déja en BDD",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);

            }
        }
    }
}
