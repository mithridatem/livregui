package Model;

import java.util.ArrayList;

public class Livre {
    //Attributs
    private Integer id;
    private String titre;
    private String datePublication;
    private ArrayList<Genre> genres;

    //Constructeurs
    public Livre(){}

    public Livre(String titre, String datePublication) {
        this.titre = titre;
        this.datePublication = datePublication;
    }

    public Livre(Integer id, String titre, String datePublication) {
        this.id = id;
        this.titre = titre;
        this.datePublication = datePublication;
    }

    //Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }
    //Accesseurs Jointures livre_genre
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }

    //Méthodes
    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", datePublication='" + datePublication + '\'' +
                '}';
    }
}
