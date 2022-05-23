package ru.itmo.p3131.student18.client;

import ru.itmo.p3131.student18.client.tools.CommandSaver;
import ru.itmo.p3131.student18.client.tools.ScriptExecutive;
import ru.itmo.p3131.student18.client.tools.Sender;
import ru.itmo.p3131.student18.client.tools.readers.ClientCommandReader;
import ru.itmo.p3131.student18.interim.commands.tools.parsers.HumanBeingParser;
import ru.itmo.p3131.student18.interim.messages.ClientMessage;
import ru.itmo.p3131.student18.server.exeptions.CommandScannerException;
import ru.itmo.p3131.student18.server.exeptions.ScriptException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private final ClientCommandReader clientCommandReader = new ClientCommandReader();
    private final Sender sender = new Sender(9000);
    private final List<ScriptExecutive> scriptExecutive = new ArrayList<>();
    private int currentScriptExecutive = -1;

    private final CommandSaver commandSaver = new CommandSaver();

    public void extendedCommandFetch() throws CommandScannerException, ScriptException {
        if (scriptExecutive.size() > 0 && scriptExecutive.get(currentScriptExecutive).isScriptIsRunning()) {
            if (!scriptExecutive.get(currentScriptExecutive).run(commandSaver)) {
                scriptExecutive.remove(currentScriptExecutive);
                currentScriptExecutive--;
            }
        } else {
            clientCommandReader.startScanning();
            commandSaver.setFoundCommand(clientCommandReader.getFoundCommand());
            commandSaver.setParams(clientCommandReader.getArgs());
        }
        commandSaver.setObject(null);
        if ("add".equals(commandSaver.getFoundCommand())) {
            commandSaver.setObject(new HumanBeingParser().create());
        } else if ("update".equals(commandSaver.getFoundCommand())) {
            commandSaver.setObject(new HumanBeingParser().create());
        } else if ("execute_script".equals(commandSaver.getFoundCommand())) {
            currentScriptExecutive++;
            scriptExecutive.add(new ScriptExecutive());
            scriptExecutive.get(currentScriptExecutive).initialize(commandSaver.getParams()[0]);
            }
        }

    public void dataTransferring() throws InterruptedException {
        while (true) {
            ClientMessage message = new ClientMessage(commandSaver.getFoundCommand(), commandSaver.getParams(), commandSaver.getObject());
            sender.send(message);
            Thread connectionThread = new Thread(() -> {
                try {
                    sender.receive();
                } catch (IOException e) {
                    System.out.println("Failed to receive message.");
                }
            });
            connectionThread.start();
            Thread.sleep(3000);
            if (connectionThread.isAlive()) {
                connectionThread.interrupt();
                System.out.println("No connection to server.");
            } else {
                break;
            }
        }
    }

    public void printResults() {
        String commandResult = sender.getCommandResultMessage();
        String commandError = sender.getErrMessage();
        System.out.println((Objects.equals(commandResult, null) ? "" : commandResult) +
                (Objects.equals(commandError, null) ? "" : commandError));
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Client stops working. Good bye!");
        }));

        Client client = new Client();
        try {
            while (true) {
                try {
                    client.extendedCommandFetch();
                    client.dataTransferring();
                    client.printResults();
                    } catch(CommandScannerException | InterruptedException | ScriptException e){
                        System.out.println(e.getMessage());
                    }
                    /* catch (ConnectionException e) {
                    System.out.println("No connection to server.");
                }*/
                }
            } finally{
                client.sender.closeSocket();
            }
        }
    }
