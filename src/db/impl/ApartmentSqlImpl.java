package db.impl;

import db.DBConnection;
import db.dao.ApartmentsDAO;
import db.dao.DistrictDAO;
import entity.Apartment;
import entity.District;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApartmentSqlImpl implements ApartmentsDAO {
    private DistrictDAO districtDAO = new DistrictSqlImpl();
    private static final String SELECT_BEGIN = "SELECT a.id, d.id as districtId, d.name as districtName, a.address, a.square, a.flats_count, a.price " +
                                                "FROM apartments as a " +
                                                "LEFT JOIN districts as d ON a.district_id = d.id " +
                                                "WHERE a.";
    private PreparedStatement statement;
    private Connection connection;

    public void addApartment(Apartment apartment) {
        try {
            getDistrict(apartment);

            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO apartments (district_id, address, square, flats_count, price)" +
                    " VALUES (?, ?, ?, ?, ?)");

            statement.setInt(1, apartment.getDistrict().getId());
            statement.setString(2, apartment.getAddress());
            statement.setInt(3, apartment.getSquare());
            statement.setInt(4, apartment.getFlatsCount());
            statement.setInt(5, apartment.getPrice());
            statement.executeUpdate();

            statement.close();
//            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Apartment> getByDistrict(String name) {
        ArrayList<Apartment> apartments = new ArrayList<Apartment>();
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT * " +
                                                        "FROM apartments " +
                                                        "WHERE district_id = " +
                                                            "(SELECT id FROM districts where name = ?)");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            int districtId = resultSet.getInt("district_id");
            District district = new District(districtId, name);

            while (resultSet.next()) {
                Apartment apartment = new Apartment(
                        resultSet.getInt("id"),
                        district,
                        resultSet.getString("address"),
                        resultSet.getInt("square"),
                        resultSet.getInt("flats_count"),
                        resultSet.getInt("price"));

                apartments.add(apartment);
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return apartments;
    }

    public ArrayList<Apartment> getBySquare(String logicOperator, int square) {
        ArrayList<Apartment> apartments = new ArrayList<Apartment>();
        try {
            connection = DBConnection.getConnection();
            String sqlQuery = SELECT_BEGIN + "square " + logicOperator + " ?";

            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1,square);

            ResultSet resultSet = statement.executeQuery();
            apartments = initApartments(resultSet);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return apartments;
    }

    public ArrayList<Apartment> getByPrice(String logicOperator, int price) {
        ArrayList<Apartment> apartments = new ArrayList<Apartment>();
        try {
            connection = DBConnection.getConnection();
            String sqlQuery = SELECT_BEGIN + "price " + logicOperator + " ?";

            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, price);

            ResultSet resultSet = statement.executeQuery();
            apartments = initApartments(resultSet);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return apartments;
    }

    public ArrayList<Apartment> getByFlatsCount(String logicOperator, int flatsCount) {
        ArrayList<Apartment> apartments = new ArrayList<Apartment>();
        try {
            connection = DBConnection.getConnection();
            String sqlQuery = SELECT_BEGIN + "flats_count " + logicOperator + " ?";

            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, flatsCount);

            ResultSet resultSet = statement.executeQuery();
            apartments = initApartments(resultSet);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return apartments;
    }

    private void getDistrict(Apartment apartment) throws SQLException {
        if (apartment.getDistrict() != null
                && apartment.getDistrict().getName() != null) {
            District district = districtDAO.getDistrictByName(apartment.getDistrict().getName());
            if (district.getId() != -1) {
                apartment.setDistrict(district);
            } else {
                apartment.getDistrict().setId(districtDAO.addDistrict(apartment.getDistrict()));
            }
        }
    }

    private ArrayList<Apartment> initApartments(ResultSet resultSet) throws SQLException {
        ArrayList<Apartment> apartments = new ArrayList<Apartment>();
        while (resultSet.next()){
            District district = new District(resultSet.getInt("districtId"), resultSet.getString("districtName"));
            Apartment apartment = new Apartment(
                    resultSet.getInt("id"),
                    district,
                    resultSet.getString("address"),
                    resultSet.getInt("square"),
                    resultSet.getInt("flats_count"),
                    resultSet.getInt("price")
            );
            apartments.add(apartment);
        }
        return apartments;
    }
}
