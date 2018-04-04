package db.dao;

import entity.Apartment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ApartmentsDAO {
    void addApartment(Apartment apartment) throws SQLException;
    ArrayList<Apartment> getByDistrict(String name);
    ArrayList<Apartment> getBySquare(String logicOperator, int square);
    ArrayList<Apartment> getByPrice(String logicOperator, int price);
    ArrayList<Apartment> getByFlatsCount(String logicOperator, int flatsCount);
}
