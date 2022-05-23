package ru.itmo.p3131.student18.interim.commands;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.Server;
import ru.itmo.p3131.student18.server.collection.CollectionManager;

import java.io.Serializable;

public class RemoveCommand implements Command, Serializable {
    private CollectionManager manager;

    public RemoveCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean isWithArgs() {
        return true;
    }

    @Override
    public void execute(String[] params, HumanBeing human) {
        try {
            if (manager.isIdValid(Integer.parseInt(params[0]))) {
                manager.remove_by_id(Integer.parseInt(params[0]));
            } else {
                Server.printErr("There is no element with that id.");
            }
        } catch (NumberFormatException e) {
        Server.printErr("Incorrect value. Parameter value has to be int.");
        }
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getCommandInfo() {
        return "removes an element from collection by it's id (if there is an element with the given id).";
    }
}
