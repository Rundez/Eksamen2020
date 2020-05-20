package com.company.events;

import com.company.Person;
import com.company.State;

public class ImmuneEvent extends Event {
    Person person;
    public ImmuneEvent(int day, Person person) {
        super(day);
        this.person = person;
    }

    @Override
    public Event happen() {
        // The person will gain the state "IMMUNITY", which is the final state of the program.
        // The reason this method returns null is to prevent the eventQueue in
        // PandemiSim to loop in infinity.
        person.setState(State.IMMUNE);
        return null;
    }
}
