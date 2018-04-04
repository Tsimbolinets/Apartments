import db.dao.ApartmentsDAO;
import db.impl.ApartmentSqlImpl;
import entity.Apartment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ApartmentsDAO apartmentsDAO = new ApartmentSqlImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            String s = scanner.nextLine();
            switch (s) {
                case "1":
                    addApartment(scanner);
                    break;
                case "2":
                    getByDistrict(scanner);
                    break;
                case "3":
                    getBySquare(scanner);
                    break;
                case "4":
                    getByFlatsCount(scanner);
                    break;
                case "5":
                    getByPrice(scanner);
                    break;
                case "close":
                    return;
            }
        }
    }

    private static void getByDistrict(Scanner scanner) {
        System.out.println("Input district:");
        showApartments(apartmentsDAO.getByDistrict(scanner.nextLine()));
    }

    private static void getBySquare(Scanner scanner) {
        System.out.println("Input logic operator and square 'Example: '> 25' or '>= 31' :");
        String params = scanner.nextLine();
        if (!"back".equals(params)) {
            int index = params.indexOf(" ");
            showApartments(apartmentsDAO.getBySquare(params.substring(0, index), Integer.parseInt(params.substring(index + 1))));
            System.out.println();
        }
    }

    private static void getByFlatsCount(Scanner scanner) {
        System.out.println("Input count of flats. Example: '> 5' or '= 31' :");
        String params = scanner.nextLine();
        if (!"back".equals(params)) {
            int index = params.indexOf(" ");
            showApartments(apartmentsDAO.getByFlatsCount(params.substring(0, index), Integer.parseInt(params.substring(index + 1))));
            System.out.println();
        }
    }

    private static void getByPrice(Scanner scanner) {
        System.out.println("Input price. Example: '> 5' or '= 31' :");
        String params = scanner.nextLine();
        if (!"back".equals(params)) {
            int index = params.indexOf(" ");
            showApartments(apartmentsDAO.getByPrice(params.substring(0, index), Integer.parseInt(params.substring(index + 1))));
            System.out.println();
        }
    }

    private static void showApartments(ArrayList<Apartment> apartments) {
        for (Apartment apartment : apartments) {
            System.out.println(apartment);
        }
    }

    private static void addApartment(Scanner scanner) {
        System.out.println("Input District:");
        String district = scanner.nextLine();
        System.out.println("Input address:");
        String address = scanner.nextLine();
        System.out.println("Input square:");
        String square = scanner.nextLine();
        System.out.println("Input count of flats:");
        String flats = scanner.nextLine();
        System.out.println("Input price ($):");
        String price = scanner.nextLine();

        Apartment apartment = new Apartment(district,
                address,
                Integer.parseInt(square),
                Integer.parseInt(flats),
                Integer.parseInt(price)
        );

        try {
            apartmentsDAO.addApartment(apartment);
            System.out.println("Was added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showMenu() {
        System.out.println("Press 1 to add new apartment.\n" +
                "Press 2 to get apartments by district.\n" +
                "Press 3 to get apartments by square.\n" +
                "Press 4 to get apartments by count of flats.\n" +
                "Press 5 to get apartments by price.");
    }
}
