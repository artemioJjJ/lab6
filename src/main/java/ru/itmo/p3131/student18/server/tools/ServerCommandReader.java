package ru.itmo.p3131.student18.server.tools;

import ru.itmo.p3131.student18.interim.commands.tools.Invoker;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.exeptions.CommandScannerException;
import ru.itmo.p3131.student18.server.exeptions.ExcessiveArgsException;
import ru.itmo.p3131.student18.server.exeptions.NoArgsException;
import ru.itmo.p3131.student18.interim.commands.tools.CommandManager;

import javax.naming.InvalidNameException;

public class ServerCommandReader {
    private final CommandManager commandManager;

    public ServerCommandReader(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void startScanning(String command, String[] args, HumanBeing human) throws CommandScannerException {
        Invoker invoker = new Invoker(commandManager);
        try {
            invoker.findCommand(command, args, human);
        } catch (InvalidNameException | NoArgsException | ExcessiveArgsException e) {
            throw new CommandScannerException(e.getMessage());
        }
    }
}
