package EmployeeManagement.util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import EmployeeManagement.model.Employee;
import java.io.*;
import java.util.*;
import java.nio.file.Paths;
/**
 *
 * @author erick
 */
public class EmployeeStorage {

    private static final String FILE_NAME = "employees.dat";
    private static final String FILE_PATH = Paths.get(System.getProperty("user.dir"), FILE_NAME).toString();

    public static TreeMap<Integer, Employee> loadEmployees() {
        TreeMap<Integer, Employee> employees = new TreeMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                @SuppressWarnings("unchecked")
                List<Employee> employeeList = (List<Employee>) obj;
                for (Employee employee : employeeList) {
                    if (employee != null) {
                        employees.put(employee.getId(), employee);
                    }
                }
                System.out.println("Employees loaded successfully from: " + FILE_PATH);
            } else {
                System.out.println("The file does not contain a valid list of employees. Starting with an empty list.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing employee data found at: " + FILE_PATH + ". Starting with an empty list.");
        } catch (EOFException e) {
            System.out.println("The employee data file is empty. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading employees from " + FILE_PATH + ": " + e.getMessage());
        }
        return employees;
    }

    public static void saveEmployees(TreeMap<Integer, Employee> employees) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            List<Employee> employeeList = new ArrayList<>(employees.values());
            oos.writeObject(employeeList);
            System.out.println("Employees saved successfully to: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error saving employees to " + FILE_PATH + ": " + e.getMessage());
        }
    }
}
