package io.jonashackt.lectures.exercises.storage;

import io.jonashackt.lectures.exercises.domain.Person;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressbookRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AddressbookRepository.class);
    private JdbcDataSource dataSource;
    private String databaseName = "addressbook";

    public AddressbookRepository() throws StorageError {
        dataSource = new JdbcDataSource();
        // see http://www.h2database.com/html/features.html#in_memory_databases
        // because we want to persist our in-memory H2 database as long as our JVM is alive (or tests run)
        // we need to add ;DB_CLOSE_DELAY=-1 here. With that we also have the same behaviour like a 'real' DB
        // and can use multiple JDBC connections with dataSource.getConnection()
        // otherwise every connection would also get a new in-memory database, without the pre-created tables
        // from other connections
        dataSource.setURL("jdbc:h2:mem:" + databaseName + ";DB_CLOSE_DELAY=-1");
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

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
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
            LOG.error(e.getMessage());
            throw new StorageError(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
                throw new StorageError(e.getMessage());
            }
        }
    }

    private void executeStatement(String sqlStatement) throws StorageError {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sqlStatement);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new StorageError(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
                throw new StorageError(e.getMessage());
            }
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
            LOG.error(e.getMessage());
            throw new StorageError(e.getMessage());
        }
    }
}
