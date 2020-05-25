package io.jonashackt.lectures.exercises.storage;

import io.jonashackt.lectures.exercises.Person;
import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressbookRepository {

    private Connection connection;
    private String databaseName = "addressbook";

    public AddressbookRepository() throws StorageError {

        try {
            //Same as Connection connection = DriverManager.getConnection("jdbc:h2:mem:" + databaseName);
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:mem:" + databaseName);
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new StorageError(e.getMessage());
        }
    }

    public void createTablePerson() throws StorageError {
        executeStatement(resourceFile2String("createTable.sql"));
    }

    public void savePerson(Person person) throws StorageError {
        String insertSql = "INSERT INTO PERSON (FIRST_NAME, LAST_NAME, EMAIL)\n" +
                        "VALUES ('" + person.getFirstName() + "', '" + person.getLastName() + "', '" + person.geteMail() + "')";
        executeStatement(insertSql);
    }

    public Person readPerson() throws StorageError {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from PERSON");
            String firstName = "";
            String lastName = "";
            String eMail = "";
            while(resultSet.next()) {
                firstName = resultSet.getString("FIRST_NAME");
                lastName = resultSet.getString("LAST_NAME");
                eMail = resultSet.getString("EMAIL");
            }
            return new Person(firstName, lastName, eMail);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new StorageError(e.getMessage());
        }
    }

    private void executeStatement(String sqlStatement) throws StorageError {
        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new StorageError(e.getMessage());
        }
    }

    /**
     * Do the gory file reading from src/main/resources directory and simply return the contents as String, stupid!
     *
     * @param fileName
     * @return
     * @throws StorageError
     */
    private String resourceFile2String(String fileName) throws StorageError {
        try {
            return Files.readString(Paths.get("src/main/resources/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageError(e.getMessage());
        }
    }
}
