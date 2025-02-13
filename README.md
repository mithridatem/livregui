# livregui
1 Ajouter un fichier **Env.java** dans le dossier **Database** en remplacant les valeurs par vos informations de connexion à la BDD
```java
package Database;

public class Env {
    public static final String DB_URL = "jdbc:mysql://localhost/nom_bdd";
    public static final String DB_LOGIN = "login bdd";
    public static final String DB_PASSWORD = "mot de passe";
}
```
