package ru.itmo.p3131.student18.interim.commands.tools.parsers;

import ru.itmo.p3131.student18.server.exeptions.NumberValueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileParser {
    private boolean parsingIsComplete = false;
    private File pathToFile;
    private final Scanner fileDataScanner;

    /**
     * This constructor is used for getting File object with path to file containing:
     * 1) starting data for collection initialization and
     * save command.
     * 2) scripts for command Execute script.
     *
     * @param name name of file without filename extension.
     * @param mode defines a mode for File parser.
     *            0 - parsing file contains starting data.
     *            1 - parsing file contains script with commands.
     * @throws FileNotFoundException - if file was not found.
     */
    public FileParser(String name, int mode) throws FileNotFoundException, NumberValueException {
        switch (mode) {
            case 0 -> this.fileDataScanner = new Scanner(initFileBuilder(name));
            case 1 -> this.fileDataScanner = new Scanner(scriptFileBuilder(name));
            default -> throw new NumberValueException("Provided mode doesn't exists.");
        }
    }

    public File getFilePath() {
        return pathToFile;
    }

    public String parseToString() throws NoSuchElementException {
        StringBuilder data = new StringBuilder();
        while (!parsingIsComplete) {
                while (fileDataScanner.hasNextLine()) {
                    String line = fileDataScanner.nextLine();
                    data.append(line).append("\n");
                }
                parsingIsComplete = true;
                fileDataScanner.close();
        }
        return data.toString();
    }

    /**
     * This method is used for getting a File object, containing a path to file with starting data.
     * @return File with path to starting data.
     * @param fileName name of file containing json objects of HumanBeing type.
     * @throws FileNotFoundException if file with the given name doesn't exists in resourses/json directory.
     * @throws SecurityException if user have no rights to read or write file.
     */
    private File initFileBuilder(String fileName) throws FileNotFoundException, SecurityException {
        try {
            pathToFile = getFileFromResource("json/" + fileName + ".json");
            if (!(pathToFile.canRead() && pathToFile.canWrite())) {
                throw new SecurityException("User have no right to read or write file.");
            }
        } catch (URISyntaxException e) {
            throw new FileNotFoundException("Failed to get file from resources file.");
        }
        return pathToFile;
    }

    /**
     * This method is used for getting a File object, containing a path to file with script to be executed.
     * @throws FileNotFoundException if file with the given name doesn't exists in resourses/scripts directory.
     * @throws SecurityException if user have no rights to read or write file.
     * @return File with path to starting data.
     * @param fileName name of file containing commands for executing automatically.
     */
    private File scriptFileBuilder(String fileName) throws FileNotFoundException, SecurityException {
        try {
            pathToFile = getFileFromResource("scripts/" + fileName + ".txt");
            if (!(pathToFile.canRead() && pathToFile.canWrite())) {
                throw new SecurityException("User have no right to read or write file.");
            }
        } catch (URISyntaxException e) {
            System.out.println("Failed to get file from resources file.");
        }
        return pathToFile;
    }

    /**
     * This method is used to get any kind of file from directory resources root.
     * @param completeFileName should be a relative path to the file.
     * @return File by its name.
     * @throws URISyntaxException is thrown when some strange shit happens.
     * @throws FileNotFoundException if there is no file with such name in resources.
     */
    private File getFileFromResource(String completeFileName) throws URISyntaxException, FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(completeFileName);
        if (resource == null) {
            throw new FileNotFoundException("File not found!");
        } else {
            return new File(resource.toURI());
        }
    }
}

