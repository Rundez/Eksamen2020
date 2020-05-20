package com.company;

import com.company.events.CleanEvent;
import com.company.events.Event;
import com.company.events.SickEvent;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * PandemiSim is the main class of the application. It contains the main loop,
 * which advances the time, and a secondary loop that goes through the events of
 * the current day
 */
public class PandemiSim implements Conf {

    // PandemiSim uses the singleton pattern
    // to make the single PandemiSim object available everywhere.
    private static final PandemiSim theInstance = new PandemiSim();

    private PriorityQueue<Event> eventQueue;
    public Person[] pop;

    // the day that are currently being simulated
    private int today;


    public static PandemiSim getInstance() {
        return theInstance;
    }

    // main just starts the sim
    public static void main(String[] args) {
        PandemiSim sim = PandemiSim.getInstance();
        sim.run();


    }

    public PandemiSim() {
        // Oppgave 1b)
        eventQueue = new PriorityQueue<>();
        pop = new Person[POPULATION];

        generatePersons();
        generateSick();
    }

    // Generates the population where every person has the name (or ID) of an integer. The initial state is set to CLEAN.
    // A new CleanEvent is created with each person as input.
    public void generatePersons(){
        for(int i = 0; i < (POPULATION); i++) {
            pop[i] = new Person(Integer.toString(i), State.CLEAN);
            addEvent(new CleanEvent(0, pop[i]));
        }
    }

    // Generate initial sick persons. Also adds a new SickEvent to the Queue.
    // If the same person gets chosen, it will decrement and select a new person.
    public void generateSick(){
        for(int i = 0; i <= INITALLY_SICK; i++){
            int rand = RandomWrapper.getInstance().nextInt(POPULATION);
            if((pop[rand].state == State.CLEAN)) {
                pop[rand].state = State.SICK;
                addEvent(new SickEvent(0, pop[rand]));
            } else{
                i--;
            }
        }
    }

    // Main loop for the "runDay()" method, which then will handle the eventQueue for each day (given as input).
    public void run() {
        for (int day = 0; day < MAX_DAY; day++) {
            runDay(day);

            printStats(day);
        }
    }

    // Traverses the array after each day and prints the stats of the persons.
    private void printStats(int day){
        int sickPeople = 0;
        int healthy = 0;
        int immune = 0;
        int dead = 0;
        for(Person p : pop){
            if(p.getState() == State.SICK){
                sickPeople++;
            } else if(p.getState() == State.CLEAN){
                healthy++;
            }else if(p.getState() == State.IMMUNE){
                immune++;
            }else if(p.getState() == State.DEAD){
                dead++;
            }
        }

        System.out.println("Info for day: " + day);
        System.out.println("Healthy: " + healthy);
        System.out.println("Immune: " + immune);
        System.out.println("Dead: " + dead);
        System.out.println("Sick: " + sickPeople + "\n");
    }


    // The main loop that polls all of the elements in the eventQueue.
    // Since all of the elements are sorted by day, the
    private void runDay(int day) {
        today = day;
        while (!eventQueue.isEmpty() && eventQueue.peek().getDay() == today) {
            Event e = eventQueue.poll();
            addEvent(e.happen());
        }
    }

    // Returns a random person from the population.
    public Person getRandomPerson() {
        RandomWrapper random = RandomWrapper.getInstance();
        int i = random.nextInt(POPULATION);
        return pop[i];
    }

    // Adds an event to the event queue.
    public void addEvent(Event e) {
        if(e != null) {
            eventQueue.add(e);
        }
    }
}
