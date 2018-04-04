package db.impl;

import db.DBConnection;
import db.dao.DistrictDAO;
import entity.District;

import java.sql.*;

public class DistrictSqlImpl implements DistrictDAO {
    private Connection connection;
    private PreparedStatement statement;

    public int addDistrict(District district) throws SQLException {
        try {
            connection = DBConnection.getConnection();

            statement = connection.prepareStatement("INSERT INTO districts (name) (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, district.getName());
            statement.executeUpdate();

            int id = statement.getGeneratedKeys().getInt("id");
            statement.close();
            connection.commit();

            return id;
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.close();
        }
        return -1;
    }

    public void updateDistrict(String oldName, String newName) throws SQLException {
        connection = DBConnection.getConnection();

        statement = connection.prepareStatement("UPDATE districts SET name = ? WHERE name = ?");
        statement.setString(1, newName);
        statement.setString(2, oldName);

        statement.executeUpdate();

        connection.commit();
        statement.close();
        if (connection != null)
            connection.close();
    }

    public void deleteDistrict(String name) throws SQLException {
        connection = DBConnection.getConnection();

        statement = connection.prepareStatement("DELETE FROM districts WHERE name = ?");
        statement.setString(1, name);

        statement.executeUpdate();
        connection.commit();
        connection.close();
    }

    public District getDistrictByName(String name) throws SQLException {
        connection = DBConnection.getConnection();

        statement = connection.prepareStatement("SELECT id FROM districts WHERE name = ?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();

        int id = -1;
        if (resultSet.next())
            id = resultSet.getInt(1);

        resultSet.close();
        statement.close();

        if (connection != null)
            connection.close();

        return new District(id, name);
    }
}
