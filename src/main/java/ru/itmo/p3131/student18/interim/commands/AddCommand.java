package ru.itmo.p3131.student18.interim.commands;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.collection.CollectionManager;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.HumanBeingParser;

import java.io.Serializable;

public class AddCommand implements Command, Serializable {
    private CollectionManager manager;

    public AddCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String[] params) {
        HumanBeingParser parser = new HumanBeingParser();
        manager.add(parser.create());
    }
    @Override
    public void execute(String[] args, HumanBeing human) {
        manager.add(human);
    }

    @Override
    public boolean isWithArgs() {
        return false;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getCommandInfo() {
        return "add a new element to the collection.";
    }
}

