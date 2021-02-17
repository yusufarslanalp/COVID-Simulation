package com.company;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Timer implements Subject {
    ArrayList<Observer> individul_obsevers = new ArrayList<Observer>();
    Mediator mediator_observer;
    Hospital hospital;
    long current_time = 0;
    boolean cont = false; //continue
    int period = 400;

    Timer()
    {

    }

    void start()
    {
        while( true ) {
            if( cont == true )
            {
                current_time++;
                notify_observer();
                try {
                    Thread.sleep( period );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if( cont == false )
            {
                try {
                    Thread.sleep( 200 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void
    one_pulse()
    {
        notify_observer();
    }

    void
    reset()
    {
        remove_all_individuals();
        cont = false;
        current_time = 0;
    }

    @Override
    public void register_observer( Observer o ) {
        individul_obsevers.add( (Individual)o );
    }


    void set_mediator( Mediator mediator )
    {
        mediator_observer = mediator;
    }

    public void remove_all_individuals(  ) {
        individul_obsevers = new ArrayList<Observer>();
    }

    @Override
    public void remove_observer( Observer o ) {
        individul_obsevers.remove( o );
    }

    @Override
    public void notify_observer() {

        for( int i = 0; i < individul_obsevers.size(); i++ )
        {
            individul_obsevers.get(i).update();
        }
        hospital.update();
        mediator_observer.update();
    }

    public void set_continue(boolean cont) {
        this.cont = cont;
    }

    public void set_period(int period) {
        this.period = period;
    }

    public int get_period() {
        return period;
    }
}
