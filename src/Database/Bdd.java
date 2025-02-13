package Database;

import java.sql.*;

public final class Bdd {
    //Connexion à la BDD
    public static Connection connection;

    //Méthode de connexion à la BDD
    static {
        try {
            connection = DriverManager.getConnection(Env.DB_URL, Env.DB_LOGIN, Env.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
