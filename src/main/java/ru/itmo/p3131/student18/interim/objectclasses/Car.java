package ru.itmo.p3131.student18.interim.objectclasses;

import java.io.Serializable;

public class Car implements Serializable {
    private Boolean cool; //Поле может быть null

    public Car(boolean cool) {
        this.cool = cool;
    }

    public String toString() {
        if (cool) {
            return "Has such a cool car!";
        } else return "Has a regular pussywagon";
    }
}