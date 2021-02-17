package com.company;

import java.util.LinkedList;
import java.util.Queue;

public class Hospital implements Observer {
    Mediator mediator;
    int num_of_ventilator;
    int num_of_patient = 0;

    Queue<Individual> waiting_patients = new LinkedList<Individual>();

    void
    apply_hospital( Individual individual )
    {
        waiting_patients.add( individual );
    }

    Hospital( Mediator mediator )
    {
        this.mediator = mediator;
    }

    void set_population( int population ){
        num_of_ventilator = population/100;
    }



    void
    left_from_hospital( Individual individual )
    {
        num_of_patient--;
    }

    void
    accept_patints()
    {
        Individual individual = waiting_patients.peek();
        if(  individual.is_alive() )
        {
            individual.accepted_hospital();
            num_of_patient++;
        }
        waiting_patients.remove();

    }

    @Override
    public void update() {
        int num_of_individual = mediator.get_total_created_individual();
        num_of_ventilator = (num_of_individual / 100) +1;
        while( (waiting_patients.size() > 0) & (num_of_ventilator > num_of_patient) )
        {
            accept_patints();
        }
    }
}
