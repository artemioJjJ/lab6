package ru.itmo.p3131.student18.interim.commands.tools.parsers;

import ru.itmo.p3131.student18.client.tools.readers.ObjectFieldsReader;
import ru.itmo.p3131.student18.interim.commands.tools.objectcheck.HumanBeingChecker;
import ru.itmo.p3131.student18.interim.objectclasses.*;
import ru.itmo.p3131.student18.server.exeptions.ObjectFieldsValueException;

public class HumanBeingParser {

    private String name;
    private Coordinates coordinates;
    private boolean realHero;
    private boolean hasToothpick;
    private float impactSpeed; //Значение поля должно быть больше -163
    private WeaponType weaponType;
    private Mood mood;
    private Car car;


    private final HumanBeingChecker checker = new HumanBeingChecker();
    private ObjectFieldsReader reader;

    public HumanBeingParser() {
        reader = new ObjectFieldsReader();
    }
    public HumanBeingParser(HumanBeing human) {}

    public HumanBeing create() {
        check();
        return new HumanBeing(name, coordinates, realHero, hasToothpick, impactSpeed, weaponType, mood, car);
    }
    public HumanBeing create(HumanBeing human) {
        check(human);
        return new HumanBeing(name, coordinates, realHero, hasToothpick, impactSpeed, weaponType, mood, car);
    }

    public HumanBeing update(int newId) {
        check();
        HumanBeing updatedHumanBeing = new HumanBeing(name, coordinates, realHero, hasToothpick, impactSpeed, weaponType, mood, car);
        updatedHumanBeing.setId(newId);
        return updatedHumanBeing;
    }
    public HumanBeing update(int newId, HumanBeing human) {
        check(human);
        HumanBeing updatedHumanBeing = new HumanBeing(name, coordinates, realHero, hasToothpick, impactSpeed, weaponType, mood, car);
        updatedHumanBeing.setId(newId);
        return updatedHumanBeing;
    }

    public void initCheck(HumanBeing human) throws ObjectFieldsValueException {
        //If name value is null, gson will throw an exception and end the program.

        checker.coordinatesCheck(human.getCoordinates());
        if (!checker.isCoordinatesCorrect()) {
            throw new ObjectFieldsValueException("\"Coordinates\" value is not correct.");
        }
        checker.impactSpeedCheck(human.getImpactSpeed());
        if (!checker.isImpactSpeedCorrect()) {
            throw new ObjectFieldsValueException("\"ImpactSpeed\" value is not correct.");
        }
    }

    private void check() {
        while (!checker.isNameCorrect()) {
            name = checker.nameCheck(reader.nameScanner());
        }
        while (!checker.isCoordinatesCorrect()) {
            coordinates = checker.coordinatesCheck(reader.coordinatesScanner());
        }
        // Boolean values can not be null so they are validated in ObjectFieldsReader.
        realHero = reader.realHeroScanner();
        hasToothpick = reader.hasToothPickScanner();

        while (!checker.isImpactSpeedCorrect()) {
            impactSpeed = checker.impactSpeedCheck(reader.impactSpeedScanner());
        }
        // Can't go wrong with Enums as well. Exceptions will be caught at gson parsing or at reading validation.
        weaponType = reader.weaponScanner();
        mood = reader.moodScanner();

        // Car can be null.
        car = reader.carScanner();
    }

    private void check(HumanBeing human) {
        while (!checker.isNameCorrect()) {
            name = checker.nameCheck(human.getName());
        }
        while (!checker.isCoordinatesCorrect()) {
            coordinates = checker.coordinatesCheck(human.getCoordinates());
        }
        // Boolean values can not be null so they are validated in ObjectFieldsReader.
        realHero = human.isRealHero();
        hasToothpick = human.isHasToothpick();

        while (!checker.isImpactSpeedCorrect()) {
            impactSpeed = checker.impactSpeedCheck(human.getImpactSpeed());
        }
        // Can't go wrong with Enums as well. Exceptions will be caught at gson parsing or at reading validation.
        weaponType = human.getWeaponType();
        mood = human.getMood();

        // Car can be null.
        car = human.getCar();
    }
}
