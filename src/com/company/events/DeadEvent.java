package com.company.events;

import com.company.Person;
import com.company.State;

public class DeadEvent extends Event {
    Person person;

    public DeadEvent(int day, Person person) {
        super(day);
        this.person = person;
    }

    @Override
    public Event happen() {
        // The person will gain the state "DEAD", which is the final state of the program.
        // The reason this method returns null is to prevent the eventQueue in
        // PandemiSim to loop in infinity.
        person.setState(State.DEAD);
        return null;
    }
}
