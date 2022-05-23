package ru.itmo.p3131.student18.interim.commands;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.collection.CollectionManager;

import java.io.Serializable;

public class ShowCommand implements Command, Serializable {
    private CollectionManager manager;

    public ShowCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] params, HumanBeing human) {
        manager.show();
    }

    @Override
    public boolean isWithArgs() {
        return false;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getCommandInfo() {
        return "returns all elements string representation.";
    }
}
