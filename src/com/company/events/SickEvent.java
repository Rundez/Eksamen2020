package com.company.events;

import com.company.Conf;
import com.company.Person;
import com.company.RandomWrapper;
import com.company.State;

public class SickEvent extends Event {
    private Person person;

    public SickEvent(int day, Person person) {
        super(day);
        this.person = person;
    }

    @Override
    public Event happen() {
        person.setState(State.SICK);
        // This value will indicate for how long the person will stay sick before death or immunity.
        int howLongSickBeforeRecoveryOrDeath = RandomWrapper.getInstance().nextInt(Conf.MAX_SICK_DAYS - Conf.MIN_SICK_DAYS);

        // If the person dies or gains immunity, the person should stay sick for a random amount of time between
        // the min and max amount of days set in the Conf file.
        if(willPersonDie()){
            int newDay = day + howLongSickBeforeRecoveryOrDeath;
            return new DeadEvent(newDay, person);
        } else {
            int newDay = day + howLongSickBeforeRecoveryOrDeath;
            return new ImmuneEvent(newDay, person);
        }
    }

    // A method that returns true if the person will die. Returns true if the person dies.
    private boolean willPersonDie(){
        if(RandomWrapper.getInstance().isNextDoubleWithinProbability(Conf.DEATH_PROBILITY)){
            return true;
        }
        return false;
    }
}
