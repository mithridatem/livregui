package Repository;

import Database.Bdd;
import Model.Genre;
import org.jetbrains.annotations.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenreRepository {
    /*-------------------------------
                Attributs
    -------------------------------*/

    private static Connection connection = Bdd.connection;

    /*-------------------------------
               Méthodes CRUD
    -------------------------------*/

    //Méthode pour ajouter une genre en BDD
    public static void add(@NotNull Genre genre) throws SQLException {
        try{
            String sql = "INSERT INTO genre(name)" +
                    "VALUE(?)";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Bind du paramètre name
            prepare.setString(1, genre.getName());
            //Exécution de la requête
            int rows = prepare.executeUpdate();
            if(rows > 0){
                System.out.println("Genre ajouté en BDD");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Méthode pour supprimer un genre en BDD
    public static void remove(@NotNull Genre genre) throws SQLException {
        try{
            String sql = "DELETE FROM genre WHERE name = ?";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Bind du paramètre name
            prepare.setString(1, genre.getName());
            //Exécution de la requête
            int rows = prepare.executeUpdate();
            if(rows > 0){
                System.out.println("Genre supprimé en BDD");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Méthode qui retourne la liste des genres en BDD
    public static @NotNull ArrayList<Genre> findAll() throws SQLException {
        ArrayList<Genre> genres = new ArrayList<>();
        try{
            String sql = "SELECT id, name FROM genre";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Exécution de la requête
            ResultSet resultat = prepare.executeQuery();
            while(resultat.next()){
                genres.add(new Genre(resultat.getInt("id"), resultat.getString("name")));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return genres;
    }

    //Méthode qui vérifie si une genre existe en BDD
    public static boolean isExist(String name) throws SQLException {
        boolean exist = false;
        try{
            String sql = "SELECT id FROM genre WHERE name = ?";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Bind du paramètre name
            prepare.setString(1, name);
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

    //Méthode qui retourne un Genre depuis son name en BDD
    public static Genre findByName(@NotNull String name) throws SQLException {
        Genre genre = null;
        try{
            String sql = "SELECT id, name FROM genre WHERE name = ?";
            //Préparation de la requête
            PreparedStatement prepare = connection.prepareStatement(sql);
            //Bind des paramètres
            prepare.setString(1, name);
            //Exécution de la requête
            ResultSet resultat = prepare.executeQuery();
            while(resultat.next()){
                genre = new Genre(resultat.getInt("id"), resultat.getString("name"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return genre;
    }
}
