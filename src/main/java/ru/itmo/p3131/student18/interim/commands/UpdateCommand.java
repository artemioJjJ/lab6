package ru.itmo.p3131.student18.interim.commands;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.Server;
import ru.itmo.p3131.student18.server.collection.CollectionManager;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.HumanBeingParser;

import java.io.Serializable;

public class UpdateCommand implements Command, Serializable {
    private CollectionManager manager;

    public UpdateCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] params, HumanBeing human) {
        try {
            if (manager.isIdValid(Integer.parseInt(params[0]))) {
                HumanBeingParser parser = new HumanBeingParser(human);
                manager.update(parser.update(Integer.parseInt(params[0]), human));
                System.out.println(Integer.parseInt(params[0]));
            } else {
                Server.printErr("There is no element with that id.");
            }
        } catch (NumberFormatException e) {
            Server.printErr("Incorrect value. Parameter value has to be int.");
        }
    }

    @Override
    public boolean isWithArgs() {
        return true;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getCommandInfo() {
        return "updates the element with the given id.";
    }
}
