package com.example.atc.FlightGen;

import com.example.atc.model.Plane;

import java.util.Random;
import java.util.UUID;

public class PlaneGen extends Plane {

    public PlaneGen() {
        super();

        // String flightNumber = generateFlightNumber();
        // String planeClass = generatePlaneClass();
        id = UUID.randomUUID();
        tail_number = generateTail_number();
        state = generateState();
        last_action = generateLast_action();
        altitude = generateAltitude();
        distance = generateDistance();
        speed = generateSpeed();
        heading = generateHeading();
    }


    public UUID getId() {
        return id;
    }
    public String getTail_number() {
        return tail_number;
    }
    public String getState() {
        return state;
    }
    public long getLast_action() {return last_action; }
    //public String getFlightNumber() { return flightNumber; }
    //public String getPlaneClass() {return planeClass; }
    public int getAltitude() {
        return altitude;
    }
    public int getDistance() {
        return distance;
    }
    public int getSpeed() {
        return speed;
    }
    public int getHeading() {
        return heading;
    }

    private String generateFlightNumber() {
        // PREFIX GENERATOR
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random randomPrefixLength = new Random();
        int prefixLength = randomPrefixLength.nextInt(2) + 1;
        char[] prefix = new char[prefixLength];

        // NUMBER GENERATOR
        String numbers = "0123456789";
        Random randomNumberLength = new Random();
        int numberLength = randomPrefixLength.nextInt(4) + 1;
        char[] number = new char[numberLength];

        // COMBINES PREFIX & NUMBER
        for (int i = 0; i < prefixLength; i++) {
            prefix[i] = (letters.charAt(randomPrefixLength.nextInt(letters.length())));
        }
        for (int i = 0; i < numberLength; i++) {
            number[i] = (numbers.charAt(randomNumberLength.nextInt((numbers.length()))));
        }

        return String.valueOf(prefix) + String.valueOf(number);
    }


    private String generatePlaneClass() {
        // https://www.skybrary.aero/index.php/Airplane_Design_Group_(ADG)
        // Size of the plane in groups (small to large)
        String[] group = {"I", "II", "III", "IV", "V", "VI"};
        Random number = new Random();
        int i = number.nextInt(6);
        return group[i];
    }


    private String generateTail_number() {
        // PREFIX GENERATOR
        String[] code = {"N", "G"};
        Random randomPrefix = new Random();
        int i = randomPrefix.nextInt(code.length);
        String prefix = code[i];

        // NUMBER GENERATOR
        String numbers = "0123456789";
        Random randomNumberLength = new Random();
        int numberLength = randomNumberLength.nextInt(4) + 1;
        char[] number = new char[numberLength];

        for (int j = 0; j < numberLength; j++) {
            number[j] = (numbers.charAt(randomNumberLength.nextInt((numbers.length()))));
        }

        // POSTFIX GENERATOR
        String postfix = "";

        if (numberLength < 5) {
            String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Random randomIndex = new Random();
            int postfixIndex = randomIndex.nextInt(2) + 1;
            char[] postfixNumbers = new char[5 - numberLength];

            for (int k = 0; k < postfixNumbers.length; k++) {
                postfixNumbers[k] = (letters.charAt(randomIndex.nextInt((letters.length()))));
            }
            postfix = String.valueOf(postfixNumbers);
        }
        // COMBINES PREFIX, NUMBER, & POSTFIX
        return prefix + String.valueOf(number) + postfix;

    }


    private String generateState() {
        String[] states = {"INBOUND", "EMERGENCY" }; // Other States not auto-generated: "FINAL", "LANDED", "AT GATE", "LEAVING GATE", "DEPARTING", "DEPARTED"
        Random number = new Random();
        int random = number.nextInt(100);


        if (random >= 0) {
            return states[0];
        }
        else {
            return states[1];
        }
    }


    private long generateLast_action() {
        return System.currentTimeMillis();
    }


    private int generateAltitude() {
        Random number = new Random();
        int distance = number.nextInt(500) + 3000; // feet
        return distance;
    }

    private int generateDistance() {
        Random number = new Random();
        int random = number.nextInt(100);
        int mile = 5280;

        if (random >= 90) {
            return number.nextInt(20 * mile) + (10 * mile);
        }

        else if (random >= 80) {
            return number.nextInt(50 * mile) + (175 * mile);
        }

        else if (random >= 70) {
            return number.nextInt(50 * mile) + (500 * mile);
        }

        else if (random >= 60) {
            return number.nextInt(50 * mile) + (750 * mile);
        }

        else if (random >= 50) {
            return number.nextInt(50 * mile) + (1000 * mile);
        }

        else if (random >= 40) {
            return number.nextInt(50 * mile) + (1250 * mile);
        }

        else if (random >= 30) {
            return number.nextInt(100 * mile) + (1500 * mile);
        }

        else if (random >= 20) {
            return number.nextInt(100 * mile) + (1750 * mile);
        }

        else if (random >= 10) {
            return number.nextInt(100 * mile) + (2000 * mile);
        }

        else {
            return number.nextInt(40 * mile) + (910 * mile);
        }
    }

    private int generateSpeed() {
        Random number = new Random();
        int speed = number.nextInt(250) + 250; // mph
        return speed;
    }

    private int generateHeading() {
        Random number = new Random();
        int numHeading = number.nextInt(360) + 1; // deg
/*      String heading = String.valueOf(numHeading);

        // Leading Zeros
        if (numHeading < 10) {
           heading = "00" + heading;
        }
        else if ((numHeading > 10) && (numHeading < 100)) {
            heading = "0" + heading;
        }
*/
        return numHeading;
    }

}

