/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package EmployeeManagement;

import EmployeeManagement.util.EmployeeStorage;
import EmployeeManagement.model.Employee;
import java.util.TreeMap;
import java.util.Scanner;

/**
 *
 * @author erick
 */
public class EmployeeManagementApp {

    private static TreeMap<Integer, Employee> employees;

    public static void main(String[] args) {
        employees = new TreeMap<>(EmployeeStorage.loadEmployees());
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n\n//===============================================================//");
            System.out.println("Employee Management System:");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Print Employee List");
            System.out.println("4. Update Employee Details");
            System.out.println("5. Search for Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println("//===============================================================//\n");

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    removeEmployee(scanner);
                    break;
                case 3:
                    printEmployeeList();
                    break;
                case 4:
                    updateEmployeeDetails(scanner);
                    break;
                case 5:
                    searchEmployee(scanner);
                    break;
                case 6:
                    EmployeeStorage.saveEmployees(new TreeMap<>(employees));
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void addEmployee(Scanner scanner) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (employees.containsKey(id)) {
                throw new IllegalArgumentException("Employee ID already exists. Please use a unique ID.");
            }

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Employee name cannot be empty.");
            }

            System.out.print("Enter Employee Salary (use ',' or '.' for decimals): ");
            String salaryInput = scanner.nextLine().replace(',', '.');
            double salary = Double.parseDouble(salaryInput);
            if (salary < 0) {
                throw new IllegalArgumentException("Salary cannot be negative.");
            }

            employees.put(id, new Employee(id, name, salary));
            System.out.println("Employee added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a valid number for ID or salary.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void removeEmployee(Scanner scanner) {
        try {
            System.out.print("Enter Employee ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (employees.remove(id) != null) {
                System.out.println("Employee removed successfully!");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a valid number.");
        }
    }

    private static void printEmployeeList() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("\nEmployee List (Sorted by ID):");
            for (Employee employee : employees.values()) {
                System.out.println(employee);
            }
        }
    }

    private static void updateEmployeeDetails(Scanner scanner) {
        try {
            System.out.print("Enter Employee ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Employee employee = employees.get(id);
            if (employee == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.print("Enter new name (leave blank to keep current): ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                employee.setName(name);
            }

            System.out.print("Enter new salary (enter -1 to keep current): ");
            String salaryInput = scanner.nextLine();
            if (!salaryInput.equals("-1")) {
                double salary = Double.parseDouble(salaryInput);
                if (salary < 0) {
                    throw new IllegalArgumentException("Salary cannot be negative.");
                }
                employee.setSalary(salary);
            }

            System.out.println("Employee details updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a valid number for ID or salary.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void searchEmployee(Scanner scanner) {
        try {
            System.out.print("Enter Employee ID to search: ");
            int id = Integer.parseInt(scanner.nextLine());

            Employee employee = employees.get(id);
            if (employee != null) {
                System.out.println("\nEmployee Details:");
                System.out.println(employee);
            } else {
                System.out.println("Employee not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a valid number.");
        }
    }
}