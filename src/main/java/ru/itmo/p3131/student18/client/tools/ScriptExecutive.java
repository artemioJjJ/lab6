package ru.itmo.p3131.student18.client.tools;

import ru.itmo.p3131.student18.client.Client;
import ru.itmo.p3131.student18.client.tools.readers.ClientCommandReader;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.FileParser;
import ru.itmo.p3131.student18.server.exeptions.CommandScannerException;
import ru.itmo.p3131.student18.server.exeptions.NumberValueException;
import ru.itmo.p3131.student18.server.exeptions.RecursionException;
import ru.itmo.p3131.student18.server.exeptions.ScriptException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class ScriptExecutive {
    private static final Set<File> executedScripts = new HashSet<>();
    private boolean scriptIsRunning = false;
    private String nameOfFile = "";
    private FileParser parser = null;
    private ClientCommandReader clientCommandReader = null;

    public boolean isScriptIsRunning() {
        return scriptIsRunning;
    }

    public void initialize(String nameOfFile) throws ScriptException {
        this.scriptIsRunning = true;
        this.nameOfFile = nameOfFile;
        try {
            parser = new FileParser(nameOfFile, 1);
            if (!executedScripts.add(parser.getFilePath())) {
                scriptIsRunning = false;
                throw new RecursionException("There is a recursion. Script won't continue working.");
            } else {
                clientCommandReader = new ClientCommandReader(parser.parseToString());
            }
        } catch(FileNotFoundException | RecursionException | NumberValueException | NoSuchElementException e) {
            scriptIsRunning = false;
            throw new ScriptException(e.getMessage());
        }
    }

    public boolean run(CommandSaver commandSaver) throws CommandScannerException {
        boolean hasScannerNextLine;
        hasScannerNextLine = clientCommandReader.scriptScanning();
        commandSaver.setFoundCommand(clientCommandReader.getFoundCommand());
        commandSaver.setParams(clientCommandReader.getArgs());
        if (!hasScannerNextLine) {
            scriptIsRunning = false;
            executedScripts.remove(parser.getFilePath());
        }
        return hasScannerNextLine;
    }
}
