package com.company.events;

import com.company.Person;
import com.company.RandomWrapper;
import com.company.State;
import com.company.Conf;
import com.company.PandemiSim;

public class CleanEvent extends Event {
        Person person;

    public CleanEvent(int day, Person person) {
        super(day);
        this.person = person;
    }

    @Override
    public Event happen() {
        if(getsSick()) {

            int newDay = day + 1;
            return new SickEvent(newDay, person);
        } else {
            int newDay = day + 1;
            return new CleanEvent(newDay, person);
        }
    }

    // Simulates contact if the person have had contacts less than 3 times that day.
    private boolean getsSick(){
        // Resets the daily contacts for each day. (When this method launches, a day has passed)
        person.dailyContacts = 0;

        // Retrieves a random number to simulate the chances of having contact to a person.
        int contactsToSimulate = RandomWrapper.getInstance().nextInt(Conf.MAX_CONTACTS_PER_DAY);

        // A loop that will execute the chance of getting sick.
        while(person.dailyContacts < contactsToSimulate){
            person.dailyContacts++;
            if(PandemiSim.getInstance().getRandomPerson().getState() == State.SICK){
                if(RandomWrapper.getInstance().isNextDoubleWithinProbability(Conf.SICK_PROBABILITY)){
                    return true;
                }
            }
        }
        return false;
    }
}
