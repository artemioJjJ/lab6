package ru.itmo.p3131.student18.client.tools.readers;

import java.util.NoSuchElementException;

/**
 * This class provides console input for
 * 1) initialization(user inserts name of file, which contains data structure with objects)
 * 2) exiting program, for which user have to accept that he is going to close program.
 */
public class DataReader {
    private boolean Successful_Scanning = false;

    public String nameOfFileScanning() {
        String nameOfFile = "";
        while (true) {
            System.out.println("Insert json file name:");
            try {
                nameOfFile = InputStream.nextLine().trim();
                if (!nameOfFile.equals("")) {
                    break;
                }
                else {
                    System.out.println("This field can not be empty.");
                }
            } catch (NoSuchElementException e) {
                System.exit(0);
            }
        }
        return nameOfFile;
    }
}