package Database;

import java.sql.*;

public class Bdd {
    //Connexion à la BDD
    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(Env.DB_URL, Env.DB_LOGIN, Env.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
