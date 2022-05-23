package ru.itmo.p3131.student18.interim.commands;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.collection.CollectionManager;

import java.io.Serial;
import java.io.Serializable;

public class RemoveLastCommand implements Command, Serializable {
    private CollectionManager manager;

    public RemoveLastCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean isWithArgs() {
        return false;
    }

    @Override
    public void execute(String[] params, HumanBeing human) {
        manager.remove_last();
    }

    @Override
    public String getName() {
        return "remove_last";
    }

    @Override
    public String getCommandInfo() {
        return "removes the last element from the collection.";
    }
}
