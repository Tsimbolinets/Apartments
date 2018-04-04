package db.dao;

import entity.District;

import java.sql.SQLException;

public interface DistrictDAO {
    int addDistrict(District district) throws SQLException;
    void updateDistrict(String oldName, String newName) throws SQLException;
    void deleteDistrict(String name) throws SQLException;

    District getDistrictByName(String name) throws SQLException;
}
