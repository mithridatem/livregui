package Repository;

import Database.Bdd;
import Model.Genre;
import org.jetbrains.annotations.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GenreRepository {
    //Attributs
    private static Connection connection = Bdd.connection;

    //Méthodes
    //Ajouter une catégorie en BDD
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

    //Supprimer une catégorie en BDD
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
}
