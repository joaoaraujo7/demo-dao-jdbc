package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: findById ======");
        Department department = departmentDao.findById(1);
        System.out.println(department);
        System.out.println();

        System.out.println("=== TEST 2: findAll ======");
        List<Department> departments = departmentDao.findAll();
        for (Department d : departments) {
            System.out.println(d);
        }
        System.out.println();

        System.out.println("=== TEST 3: insert ======");
        Department newDepartment = new Department(null, "Music");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id: " + newDepartment.getId());
        System.out.println();

        System.out.println("=== TEST 4: update ======");
        Department dep2 = departmentDao.findById(6);
        dep2.setName("Food");
        departmentDao.update(dep2);
        System.out.println("Update completed");
        System.out.println();

        System.out.println("=== TEST 5: delete ======");
        System.out.println("Enter id for delete test: ");
        int id = scan.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete completed");

        scan.close();
    }
}
