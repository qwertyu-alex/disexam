package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import utils.Config;

public class DatabaseController {

  private Connection connection;
  private long created;
  private final long DB_ttl = Config.getDbTtl();

  public DatabaseController() {
    connection = getConnection();
  }

  /**
   * Get database connection
   *
   * @return a Connection object
   */
  public Connection getConnection() {
    try {
      created = (System.currentTimeMillis() / 1000L);

      // Set the dataabase connect with the data from the config
      String url =
          "jdbc:mysql://"
              + Config.getDatabaseHost()
              + ":"
              + Config.getDatabasePort()
              + "/"
              + Config.getDatabaseName()
              + "?serverTimezone=CET";

      String user = Config.getDatabaseUsername();
      String password = Config.getDatabasePassword();

      // Register the driver in order to use it
      DriverManager.registerDriver(new com.mysql.jdbc.Driver());

      // create a connection to the database
      connection = DriverManager.getConnection(url, user, password);

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return connection;
  }

  private void checkConnection()throws SQLException{

    if (connection == null){
      connection = getConnection();
      System.out.println("connecting");
    }

    if (created > (DB_ttl + created)){
      System.out.println("Denne kører");
      connection.close();
      this.connection = getConnection();
      System.out.println("connecting");
    }
  }

  /**
   * Do a query in the database
   *
   * @return a ResultSet or Null if Empty
   */
  public ResultSet query(String sql) throws SQLException{

    // Check if we have a connection
    checkConnection();

    // We set the resultset as empty.
    ResultSet rs;

    // Build the statement as a prepared statement
    PreparedStatement stmt = connection.prepareStatement(sql);

    // Actually fire the query to the DB
    rs = stmt.executeQuery();

    // Return the results
    return rs;

  }

  public int insert(String sql) throws SQLException{

    // Set key to 0 as a start
    int result = 0;

    // Check if we have a connection
    checkConnection();

    System.out.println(sql);


      // Build the statement up in a safe way
      PreparedStatement statement =
          connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


        // Execute query
        result = statement.executeUpdate();



      // Get our key back in order to update the user
      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getInt(1);
      }


    // Return the resultset which at this point will be null
    return result;
  }


  /**
   * A method which sends an update to the database.
   * @param sql Takes in sql statements as a string
   * @throws SQLException if anything happens sends an SQLException object to the caller
   */
  public void update (String sql) throws SQLException{

    // Check if we have a connection
    checkConnection();

      // Build the statement as a prepared statement
      PreparedStatement stmt = connection.prepareStatement(sql);

      // Actually fire the query to the DB
      stmt.executeUpdate();

  }

}
