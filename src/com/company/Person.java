package com.company;

/**
 *
 * @author evenal
 */
public class Person implements Conf {

    String name;
    State state;
    public int dailyContacts;

    public Person(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state){
        this.state = state;
    }



}
