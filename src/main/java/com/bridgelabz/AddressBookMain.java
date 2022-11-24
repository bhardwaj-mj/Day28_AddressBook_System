package com.bridgelabz;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressBookMain {

    public static<JSONParser> void main(String[] args) {
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
        FileWriter fileWriter = null;

        String csvPath = "E:\\BridgeLabz\\RFP\\Day28AddressBookSystem\\src\\main\\java\\com\\bridgelabz\\AddressBook.csv";

        try {
            fileWriter = new FileWriter(csvPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CSVWriter writer = new CSVWriter(fileWriter);
        List<String[]> csvLines = new ArrayList<>();

        addressBookHashMap.keySet().stream().forEach(bookName -> addressBookHashMap.get(bookName).getContacts()
                .stream().forEach(contact -> csvLines.add(contact.getContactStrings())));


        writer.writeAll(csvLines);

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("E:\\BridgeLabz\\RFP\\Day28AddressBookSystem\\src\\main\\java\\com\\bridgelabz\\AddressBook.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String csvPath1 = "E:\\BridgeLabz\\RFP\\Day28AddressBookSystem\\src\\main\\java\\com\\bridgelabz\\AddressBook.csv";
        try {
            fileReader = new FileReader(csvPath);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        CSVReader reader = new CSVReaderBuilder(fileReader).build();

        List<String[]> linesOfData = null;

        try {
            linesOfData = reader.readAll();
        } catch (IOException | CsvException e) {

            e.printStackTrace();
        }

        System.out.println("\nReading data from csv file:");
        linesOfData.stream().forEach(csvs -> {
            for (String value : csvs)
                System.out.print(value + "\t");
            System.out.println();
        });
        JSONArray jsonPersons = new JSONArray();

        addressBookHashMap.keySet().stream().forEach(bookname -> addressBookHashMap.get(bookname).getContacts()
                .stream().forEach(contact -> jsonPersons.put(contact.getContactJSON())));

        Path jsonPath = Paths.get("E:\\BridgeLabz\\RFP\\Day28AddressBookSystem\\src\\main\\java\\com\\bridgelabz\\AB.json");
        try {
            Files.deleteIfExists(jsonPath);
            Files.writeString(jsonPath, jsonPersons.toJSONObject(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONParser jsonParser = new JSONParser();

        System.out.println("\nReading data from JSON file:");
        try {
            Object object = jsonParser.parse(Files.newBufferedReader(jsonPath));
            JSONArray personsList = (JSONArray) object;
            System.out.println(personsList);
        } catch (IOException | ParseException e) {

            e.printStackTrace();
        }

    }
}

