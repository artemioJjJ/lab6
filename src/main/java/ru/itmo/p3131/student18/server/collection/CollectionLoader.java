package ru.itmo.p3131.student18.server.collection;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.FileParser;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.HumanBeingParser;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.exeptions.NumberValueException;
import ru.itmo.p3131.student18.server.exeptions.ObjectFieldsValueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Stack;

public class CollectionLoader {
    private final Gson gson;
    private final FileParser fileParser;
    private File file;


    public CollectionLoader(Gson gson, String filename, int mode) throws FileNotFoundException, NumberValueException {
        this.gson = gson;
        this.fileParser = new FileParser(filename, mode);
    }

    public Stack<HumanBeing> execute() throws JsonSyntaxException, ObjectFieldsValueException {
        Type humanType = new TypeToken<Stack<HumanBeing>>() {}.getType();
        Stack<HumanBeing> tmpStack = gson.fromJson(fileParser.parseToString(), humanType);
        file = fileParser.getFilePath();
        HumanBeingParser initParser = new HumanBeingParser();
        for (HumanBeing human : tmpStack) {
            initParser.initCheck(human);
        }
        return tmpStack;
    }

    public File getFile() {
        return file;
    }
}


