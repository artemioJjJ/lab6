package ru.itmo.p3131.student18.interim.commands;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.collection.CollectionManager;

import java.io.Serializable;

public class RemoveGreaterCommand implements Command, Serializable {
    private CollectionManager manager;

    public RemoveGreaterCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean isWithArgs() {
        return true;
    }

    @Override
    public void execute(String[] params, HumanBeing human) {
        manager.remove_greater(Integer.parseInt(params[0]));
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getCommandInfo() {
        return "removes all elements, which places further than the element with the given id, from the collection.";
    }
}
