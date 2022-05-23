package ru.itmo.p3131.student18.server.tools;

import ru.itmo.p3131.student18.interim.messages.ClientMessage;
import ru.itmo.p3131.student18.interim.messages.ServerMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Objects;

public class Receiver {
    private final DatagramSocket serverSocket;
    private InetSocketAddress recentAddress;
    private int oldPort = 0;
    private ClientMessage clientMessage;

    public int getOldPort() {
        return oldPort;
    }

    public void closeSocket() {
        serverSocket.close();
    }

    private void isMessageFromNewClient(int newPort) {
        if (newPort != getOldPort()) {
            System.out.println("New client. Port: " + newPort);
        }
    }

    /**Constructs a receiver object with own Datagram Socket, connected to random accessed local port.
     *
     * @param port - a port on a local host machine to be bound by socket.
     * @throws SocketException - if socket could not be opened, or the socket could not bind to the specified local port.
     */
    public Receiver(int port) throws SocketException {
        this.serverSocket = new DatagramSocket(port);
        System.out.println("Server started. Port: " + port);
    }

    /**
     *
     * @return client message
     */
    public ClientMessage getClientMessage() {
        return clientMessage;
    }

    /**Method is waiting until it receives a DatagramPacket from Client. When a datagram is received it deserializes to ClientMessage object.
     *
     * @throws IOException if something is wrong with receiving the data from socket.
     */
    public void receive() throws IOException {
        byte[] message = new byte[8192];
        DatagramPacket pack = new DatagramPacket(message, message.length);
        serverSocket.receive(pack);
        isMessageFromNewClient(pack.getPort());
        recentAddress = new InetSocketAddress(InetAddress.getLocalHost(), pack.getPort());
        oldPort = pack.getPort();
        byte[] data = pack.getData();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            ClientMessage obj = (ClientMessage) inputStream.readObject();
            System.out.println("Received: " + obj.getCommandName() + " " + (Objects.equals(obj.getCommandArgs(),
                    null) ? "" : " " + obj.getCommandArgs()[0]));
            this.clientMessage = obj;
            inputStream.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Class exception");
        }
    }
    public void send(ServerMessage message) {
        byte[] bytes;
        bytes = message.getBytes();
        try {
            DatagramPacket notification = new DatagramPacket(bytes, bytes.length, recentAddress);
            serverSocket.send(notification);
        } catch (IOException e) {
            System.out.println("Failed to send server message.");
        }
    }
}
