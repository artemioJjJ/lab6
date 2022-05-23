package ru.itmo.p3131.student18.interim.commands.tools;

import ru.itmo.p3131.student18.server.collection.CollectionManager;
import ru.itmo.p3131.student18.interim.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
        registerCommand(new HelpCommand(this));
        registerCommand(new AddCommand(null));
        registerCommand(new InfoCommand(null));
        registerCommand(new ShowCommand(null));
        registerCommand(new UpdateCommand(null));
        registerCommand(new RemoveCommand(null));
        registerCommand(new ClearCommand(null));
        registerCommand(new ExitCommand(null));
        registerCommand(new RemoveFirstCommand(null));
        registerCommand(new RemoveLastCommand(null));
        registerCommand(new RemoveGreaterCommand(null));
        registerCommand(new CountByImpactSpeedCommand(null));
        registerCommand(new FilterStartsWithNameCommand(null));
        registerCommand(new FilterLessThanImpactSpeedCommand(null));
        registerCommand(new ExecuteScriptCommand(null));
    }
    public CommandManager(CollectionManager manager) {
        registerCommand(new HelpCommand(this));
        registerCommand(new AddCommand(manager));
        registerCommand(new InfoCommand(manager));
        registerCommand(new ShowCommand(manager));
        registerCommand(new UpdateCommand(manager));
        registerCommand(new RemoveCommand(manager));
        registerCommand(new ClearCommand(manager));
        registerCommand(new ExitCommand(manager));
        registerCommand(new RemoveFirstCommand(manager));
        registerCommand(new RemoveLastCommand(manager));
        registerCommand(new RemoveGreaterCommand(manager));
        registerCommand(new CountByImpactSpeedCommand(manager));
        registerCommand(new FilterStartsWithNameCommand(manager));
        registerCommand(new FilterLessThanImpactSpeedCommand(manager));
        registerCommand(new ExecuteScriptCommand(manager));
    }

    public Map getCommands() {
        return commands;
    }

    /**
     * Method of a class CommandManager which returns a Command by its name, if this command is registered in
     * HashMap commands of class CommandManager.
     *
     * @param name name of command according to Command's getName method.
     * @return Command with the given name.
     */
    public Command getSpecificCommand(String name) {
        return commands.get(name.toLowerCase());
    }

    private void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

}
