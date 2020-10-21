package logic.service;

import config.DbConfig;

import java.sql.*;

public class DataBaseService {

    public ResultSet executeSql(String sqlt){

        Connection connection = null;
        Statement statement = null;

        try{
            Class.forName(DbConfig.driver);

            connection = DriverManager.getConnection(DbConfig.url, DbConfig.user, DbConfig.password);

            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sqlt);

            statement.close();
            connection.close();

            return result;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error in sql: " + sqlt);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return null;

    }
}
