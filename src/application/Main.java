package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findByID =====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println();

        System.out.println("=== TEST 2: seller findByDepartment =====");
        Department department = new Department(11, null);
        List<Seller> sellers = sellerDao.findByDepartment(department);

        for (Seller s : sellers) {
            System.out.println(s);
        }


    }
}