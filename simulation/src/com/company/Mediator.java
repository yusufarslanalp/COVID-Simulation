package com.company;

import java.util.ArrayList;

//Mediator is the Model
public class Mediator implements Subject, Observer {
    Timer timer;
    Map map = new Map( 1000, 600 );
    View view; //Observer object
    Hospital hospital;
    ArrayList<Individual> individual_list = new ArrayList<Individual>();
    ArrayList<Individual> individual_log = new ArrayList<Individual>();

    public Mediator(Timer timer) {
        this.timer = timer;
    }

    void changed( Individual ind )
    {
        individual_list.add( ind );
    }

    void create_individual(  )
    {
        individual_list.add( new Individual( this, 5 , map, timer, hospital ) );
    }

    void create_sick_individual(  )
    {
        Individual sick_individual = new Individual( this, 5 , map, timer, hospital );
        Epidemic epidemic = new Epidemic( sick_individual, true );
        sick_individual.set_epidemic( epidemic );
        individual_list.add( sick_individual );
    }

    void reset_simulation()
    {
        individual_list = new ArrayList<Individual>();
        individual_log = new ArrayList<Individual>();
        timer.reset();


    }

    void remove_individual( Individual individual ){
        boolean check = individual_list.remove( individual );
        if( check == false )
        {
            System.out.println( "ERROR in Mediator.remove_individual()" );
        }
    }

    void
    log_individual( Individual individual )
    {
        individual_log.add( individual );
    }

    int
    get_count_individual( String state )
    {
        int result = 0;
        for( int i = 0; i < individual_log.size(); i++ )
        {
            if( individual_log.get(i).is_state( state ) )
            {
                result++;
            }
        }
        return result;
    }

    int
    get_total_created_individual()
    {
        return individual_log.size();
    }


    ArrayList<State> get_states()
    {
        ArrayList<State> result = new ArrayList<State>();

        for( int i = 0; i < individual_list.size(); i++ )
        {
            result.add( new State( individual_list.get(i) ) );
        }

        return result;
    }

    void make_interaction()
    {
        boolean interacted;
        for( int i = 0; i < individual_list.size()-1; i++ )
        {
            for( int j = i+1; j < individual_list.size(); j++ )
            {
                interacted = individual_list.get(i).interact( individual_list.get(j) );
                if( interacted == true )
                {
                    break;
                }
            }
        }
    }

    @Override
    public void register_observer(Observer o) {
        view = (View)o;
    }

    @Override
    public void remove_observer(Observer o) {

    }

    @Override
    public void notify_observer() {
        view.update();
    }

    @Override
    public void update() {
        view.update();
        make_interaction();
        //reset individual list
        individual_list = new ArrayList<Individual>();
    }
}
