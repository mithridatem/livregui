package Repository;

import Database.Bdd;
import Model.Genre;
import Model.Livre;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LivreRepository {
    /*-------------------------------
                Attributs
    -------------------------------*/

    private static Connection connection = Bdd.connection;

    /*-------------------------------
               Méthodes CRUD
    -------------------------------*/

    //Méthode pour ajouter un livre en BDD
    public static void add(@NotNull Livre livre) throws SQLException {
        try{
            String sql = "INSERT INTO genre(titre, date_publication)" +
                    "VALUE(?,?)";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Binds des paramètres titre et date_publication
            prepare.setString(1, livre.getTitre());
            prepare.setString(2, livre.getDatePublication().toString());
            //Exécution de la requête
            int rows = prepare.executeUpdate();
            if(rows > 0){
                System.out.println("Le livre a ajouté en BDD");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Méthode qui retourne un livre en BDD depuis son titre
    public static Livre findByTitre(@NotNull  String titre) throws SQLException {
        Livre livre = null;
        try{
            String sql = "SELECT id, titre, date_publication FROM livre WHERE titre = ?";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Bind du paramètre titre
            prepare.setString(1, livre.getTitre());
            //Exécution de la requête
            ResultSet resultat = prepare.executeQuery();
            while(resultat.next()){
                livre = new Livre(
                        resultat.getInt("id"),
                        resultat.getString("titre"),
                        resultat.getString("date_publication")
                    );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return livre;
    }

    //Méthode qui vérifie si un livre existe en BDD
    public static boolean isExist(@NotNull String titre) throws SQLException {
        boolean exist = false;
        try{
            String sql = "SELECT id FROM livre WHERE titre = ?";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Bind du paramètre titre
            prepare.setString(1, titre);
            //Exécution de la requête
            ResultSet resultat = prepare.executeQuery();
            while(resultat.next()){
                if(resultat.getInt("id") > 0){
                    exist = true;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return exist;
    }

    //Méthode pour assigner des genres à un livre
    public static Livre addGenres(Livre livre, ArrayList<Genre> genres) throws SQLException {
        Livre newLivre = null;
        try{
            //requête SQL
            String sql = "INSERT INTO livre_genre(livre_id, genre_id) VALUES";
            for (Genre genre : genres) {
                sql += "(?,?), ";
            }
            //Préparation de la requête
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //Bind des paramètres
            int cpt = 1;
            //Bind
            for (Genre genre : genres) {
                cpt++;
                preparedStatement.setInt(1, livre.getId());
                preparedStatement.setInt(cpt, genre.getId());
            }
            //Exécution de la requête
            int addedRows = preparedStatement.executeUpdate();
            //test si l'enregistrement est ok
            if (addedRows > 0) {
                newLivre = new Livre(livre.getId(), livre.getTitre(), livre.getDatePublication());
                for (Genre genre : genres) {
                    newLivre.addGenre(genre);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return newLivre;
    }

    //Méthode pour assigner un genre à un livre
    public static boolean addGenre(@NotNull Livre livre, @NotNull Genre genre) throws SQLException {
        boolean status = false;
        try{
            //requête SQL
            String sql = "INSERT INTO livre_genre(livre_id, genre_id) VALUE(?,?)";
            //Préparation de la requête
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //Bind des paramètres
            preparedStatement.setInt(1, livre.getId());
            preparedStatement.setInt(2, genre.getId());
            //Exécution de la requête
            int addedRows = preparedStatement.executeUpdate();
            //test si l'enregistrement est ok
            if (addedRows > 0) {
                status = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return status;
    }

    //Méthode qui retourne la liste de livres en BDD
    public static ArrayList<Livre> findAll() throws SQLException {
        ArrayList<Livre> livres = new ArrayList<>();
        try{
            String sql = "SELECT l.id, l.titre, l.date_publication, \n" +
                    "GROUP_CONCAT(g.id) AS genres_id,\n" +
                    "GROUP_CONCAT(g.`name`) AS genres_name FROM livre AS l\n" +
                    "INNER JOIN livre_genre AS lg ON l.id = lg.livre_id\n" +
                    "INNER JOIN genre AS g ON lg.genre_id = g.id\n" +
                    "GROUP BY l.id;";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Exécution de la requête
            ResultSet resultat = prepare.executeQuery();
            while(resultat.next()){
                if(resultat.getString(1) != null){
                    Livre newLivre = new Livre();
                    newLivre.setId(resultat.getInt(1));
                    newLivre.setTitre(resultat.getString(2));
                    newLivre.setDatePublication(resultat.getString(3));
                    String[] genresName = resultat.getString("genres_name").split(",");
                    String[] genresId = resultat.getString("genres_id").split(",");
                    for (int i = 0; i < genresName.length ; i++) {
                        Genre newGenre = new Genre(Integer.parseInt(genresId[i]), genresName[i]);
                        newLivre.addGenre(newGenre);
                    }
                    livres.add(newLivre);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return livres;
    }
}
