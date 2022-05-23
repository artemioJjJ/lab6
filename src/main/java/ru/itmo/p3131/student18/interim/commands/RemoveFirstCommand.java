package ru.itmo.p3131.student18.interim.commands;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.collection.CollectionManager;

import java.io.Serializable;

public class RemoveFirstCommand implements Command, Serializable {
    private CollectionManager manager;

    public RemoveFirstCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean isWithArgs() {
        return false;
    }

    @Override
    public void execute(String[] params, HumanBeing human) {
        manager.remove_first();
    }

    @Override
    public String getName() {
        return "remove_first";
    }

    @Override
    public String getCommandInfo() {
        return "removes the first element from the collection (if collection is not empty).";
    }
}
