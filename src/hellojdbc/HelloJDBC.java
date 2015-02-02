package hellojdbc;

import java.sql.*;

/**
 * Created by Василий on 28.01.2015.
 */

public class HelloJDBC {

    public static void main(String[] args) {

        String dropTable = "DROP TABLE USERS";
        String createTable = "CREATE TABLE USERS (" +
                "ID INT NOT NULL AUTO_INCREMENT, " +
                "FIRSTNAME VARCHAR(45) NOT NULL, " +
                "LASTNAME VARCHAR(45) NOT NULL, " +
                "LOGIN VARCHAR(45) NOT NULL, " +
                "PRIMARY KEY (ID), UNIQUE INDEX `ID_UNIQUE` (ID ASC), UNIQUE INDEX `LOGIN_UNIQUE` (LOGIN ASC))";
        String insertUsers[] = {
                "'Vasiliy', 'Kleshevnikov', 'vasiliy'",
                "'Andrey', 'Visotskiy', 'andrey'",
                "'Nika', 'Stroganova', 'nika'",
                "'Mikhail', 'Pimenov', 'mihael'",
                "'Vladislav', 'Volkov', 'vlad'"};

        System.out.println("Starting App ...");
        try {
            System.out.println("Регистрация драйвера СУБД MySQL");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Подключение к базе данных");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "admin", "password");
            Statement statement = connection.createStatement();

            // Удаление старой, создание новой таблицы в БД
            statement.execute(dropTable);
            statement.execute(createTable);

            // Заполнение таблицы USERS
            for (int i = 0; i < insertUsers.length; i++) {
                statement.execute("INSERT INTO users (FIRSTNAME, LASTNAME, LOGIN) VALUES (" +
                        insertUsers[i] + ")");
            }

            // Получение данных из таблицы USERS
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            String tableRow;
            System.out.println("ID \tNAME \tLASTNAME \tLOGIN");
            while (result.next()) {
                tableRow = result.getInt("ID") + "\t" +
                        result.getString("FIRSTNAME") + "\t" +
                        result.getString("LASTNAME") + "\t" +
                        result.getString("LOGIN");
                System.out.println(tableRow);
            }

            System.out.println("Закрытие соединения с СУБД");
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
