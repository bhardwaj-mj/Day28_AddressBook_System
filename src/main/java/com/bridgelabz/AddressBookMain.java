package com.bridgelabz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressBookMain {

    public static void main(String[] args) {
        System.out.println("Welcome to Address Book program");
        Scanner scanner = new Scanner(System.in);
        HashMap<String, AddressBook> addressBookHashMap = new HashMap<>();

        AddressBook addressBook = new AddressBook();
        int ch;
        do {
            System.out.println("1.Add AddressBook\t" +
                    "2.Display all addressBooks\t" +
                    "3.Search by city 4\t" +
                    "4.Search by state\n " +
                    "5.Add contact in AddressBook\t" +
                    "6.Count Contacts\t" +
                    "7.Sort Entries in AddressBook alphabetically\t" +
                    "8.Display Contacts ");
            ch = scanner.nextInt();
            switch (ch) {
                case 1:
                    System.out.println("Enter address book name to add : ");
                    String name = scanner.next();
                    if (addressBookHashMap.containsKey(name)) {
                        System.out.println("AddressBook already exists.");
                    } else {
                        addressBookHashMap.put(name, addressBook);
                    }
                    break;
                case 2:
                    System.out.println(addressBookHashMap.keySet());
                    break;
                case 3:
                    System.out.println("Enter AddressBook name : ");
                    String addBookName = scanner.next();
                    if (addressBookHashMap.containsKey(addBookName)) {
                        System.out.println("Enter city name : ");
                        String cityName = scanner.next();
                        addressBook = addressBookHashMap.get(addBookName);
                        addressBook.viewPersonByCity(cityName);
                    } else {
                        System.out.println("AddressBook not found.");
                    }
                    break;
                case 4:
                    System.out.println("Enter AddressBook name : ");
                    String addBookName1 = scanner.next();
                    if (addressBookHashMap.containsKey(addBookName1)) {
                        System.out.println("Enter state name : ");
                        String stateName = scanner.next();
                        addressBook = addressBookHashMap.get(addBookName1);
                        addressBook.viewPersonByCity(stateName);
                        addressBook.printHashMap();

                    } else {
                        System.out.println("AddressBook not found.");
                    }
                case 5:
                    System.out.println("Enter addressBook name to add contact : ");
                    String searchName1 = scanner.next();
                    if (addressBookHashMap.containsKey(searchName1)) {
                        addressBook = addressBookHashMap.get(searchName1);
                        addressBook.addContact();

                    } else {
                        System.out.println("AddressBook not exists.");
                    }
                    break;
                case 6:
                    System.out.println("Enter AddressBook name : ");
                    addBookName1 = scanner.next();
                    if (addressBookHashMap.containsKey(addBookName1)) {
                        addressBook = addressBookHashMap.get(addBookName1);
                        long count = addressBook.getCount();
                        System.out.println(count);
                    } else {
                        System.out.println("AddressBook not exists.");
                    }
                    break;
                case 7:
                    System.out.println("Enter AddressBook name : ");
                    addBookName1 = scanner.next();
                    if (addressBookHashMap.containsKey(addBookName1)) {
                        addressBook = addressBookHashMap.get(addBookName1);
                        addressBook.sortContact();
                    } else {
                        System.out.println("AddressBook not found.");
                    }
                    break;
                case 8:
                    System.out.println("Enter AddressBook name : ");
                    addBookName1 = scanner.next();
                    if (addressBookHashMap.containsKey(addBookName1)) {
                        addressBook = addressBookHashMap.get(addBookName1);
                        addressBook.displayContact();

                    }
                    break;


            }
        } while (ch != 0);
        Path path = Paths.get("E:\\BridgeLabz\\RFP\\Day9AddressBookSystem\\src\\com\\bridgelabz\\AddressBookIO.txt");
        try {
            Files.deleteIfExists(path);
            Files.write(path,
                    addressBookHashMap.keySet().stream().map(key -> addressBookHashMap.get(key).toString()).collect(Collectors.toList()),
                    StandardOpenOption.CREATE);

            List<String> readAllLines = Files.readAllLines(path);
            readAllLines.stream().forEach(line -> System.out.println(line));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

