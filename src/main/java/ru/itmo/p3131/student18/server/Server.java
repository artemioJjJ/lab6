package ru.itmo.p3131.student18.server;

import com.google.gson.JsonSyntaxException;
import ru.itmo.p3131.student18.interim.commands.tools.CommandManager;
import ru.itmo.p3131.student18.interim.messages.ServerMessage;
import ru.itmo.p3131.student18.server.collection.CollectionManager;
import ru.itmo.p3131.student18.server.exeptions.CommandScannerException;
import ru.itmo.p3131.student18.server.exeptions.NumberValueException;
import ru.itmo.p3131.student18.server.exeptions.ObjectFieldsValueException;
import ru.itmo.p3131.student18.server.tools.Receiver;
import ru.itmo.p3131.student18.server.tools.ServerCommandReader;

import java.io.*;
import java.net.SocketException;

public class Server {
    private Receiver receiver;
    private CollectionManager collectionManager;
    private CommandManager commandManager;
    private ServerCommandReader commandReader;

    static String errMessage = "";
    static String defMessage = "";

    public static void printErr(String message) {
        errMessage = errMessage + "\n" + message;
    }

    public static void printDef(String message) {
        defMessage = defMessage + "\n" + message;
    }

    String writeErr() {
        String mes = errMessage;
        errMessage = "";
        return mes;
    }

    String writeDef() {
        String mes = defMessage;
        defMessage = "";
        return mes;
    }

    private Server(int port, String nameOfFile) {
        try {
            this.receiver = new Receiver(port);
            collectionManager = new CollectionManager(nameOfFile);
            System.out.println("Collection successfully initialized");
            commandManager = new CommandManager(collectionManager);
            commandReader = new ServerCommandReader(commandManager);
        } catch (SocketException e) {
            System.out.println("Socket could not be opened, or the socket could not bind to the specified local port.");
            System.exit(1);
        } catch (FileNotFoundException | NumberValueException | ObjectFieldsValueException | JsonSyntaxException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }


    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Server stops working. This is the end.");
        }));
        Server server = new Server(9000, args[0]);
        while (true==true) {
            try {
                server.receiver.receive();
                String commandName = server.receiver.getClientMessage().getCommandName();
                server.commandReader.startScanning(commandName, server.receiver.getClientMessage().getCommandArgs(), server.receiver.getClientMessage().getObject());
                server.receiver.send(new ServerMessage(server.writeDef(), server.writeErr()));
            } catch (CommandScannerException e) {
                System.out.println(e.getMessage());
                server.receiver.send(new ServerMessage(null, e.getMessage()));
            /*} catch (NullPointerException e) {
                System.out.println("oh no cringe");

            */} catch (IOException e) {
                server.receiver.send(new ServerMessage(null, "Operation failed."));
            }
        }
    }
}

