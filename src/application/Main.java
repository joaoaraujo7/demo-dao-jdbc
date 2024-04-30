package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findByID =====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println();

        System.out.println("=== TEST 2: seller findByDepartment =====");
        Department department = new Department(2, null);
        List<Seller> sellers = sellerDao.findByDepartment(department);

        for (Seller s : sellers) {
            System.out.println(s);
        }
        System.out.println();

        System.out.println("=== TEST 3: seller findAll =====");
        sellers = sellerDao.findAll();

        for (Seller s : sellers) {
            System.out.println(s);
        }

        System.out.println("=== TEST 4: seller insert =====");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", LocalDate.now(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());
        System.out.println();

        System.out.println("=== TEST 5: seller update =====");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        System.out.println(seller);
        System.out.println("Update completed");

        System.out.println("=== TEST 6: seller delete =====");
        System.out.println("Enter id for delete test: ");
        int id = scan.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete completed");

        scan.close();
    }
}