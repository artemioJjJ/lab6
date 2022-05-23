package ru.itmo.p3131.student18.interim.commands;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;

public interface Command {
    boolean isWithArgs();

    default void execute(String[] params) {}
    default void execute(String[] params, HumanBeing human) {}
    String getName();
    String getCommandInfo();
}
