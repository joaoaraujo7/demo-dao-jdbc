package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql =
                    "select s.*, d.Name as DepName " +
                            "from seller s " +
                            "inner join department d " +
                            "on s.Id = d.Id " +
                            "where s.id = ?;";
            st = conn.prepareStatement(sql);

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Department department = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, department);

                return seller;
            }

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setDepartment(department);

        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("DepName"));

        return department;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql =
                    "select s.*, d.name as DepName " +
                    "from seller s " +
                    "inner join department d " +
                    "on s.DepartmentId = d.Id " +
                    "where s.DepartmentId = ? " +
                    "order by name;";

            st = conn.prepareStatement(sql);
            st.setInt(1, department.getId());

            rs = st.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (rs.next()) {
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    departmentMap.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }
            return sellers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
