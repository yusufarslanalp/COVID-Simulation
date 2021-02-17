package com.company;

import java.util.Random;

public class Epidemic {
    //true for sick //false for healthy
    Epidemic_property property;
    boolean state = false;
    boolean in_hospital = false;
    boolean waiting_for_hospital = false;
    int total_life;
    int lossed_life = 0;
    int cure_time = 0;
    boolean alive = true;
    Individual owner;

    public Epidemic( Individual owner, boolean state ) {
        this.owner = owner;
        this.state = state;


        if( state == true )
        {
            set_property( Epidemic_property.getInstance() );
        }
    }

    Epidemic( Epidemic right )
    {
        state = right.state;
        if( state == true )
        {
            set_property( Epidemic_property.getInstance() );
        }
    }

    void set_property( Epidemic_property property )
    {
        this.property = property;
        double mortality = property.get_mortality();
        total_life = (int)(100 * (1-mortality));
    }

    boolean get_state()
    {
        return state;
    }



    boolean
    is_in_hospital( )
    {
        return in_hospital;
    }

    void
    set_in_hospital( boolean value )
    {
        in_hospital = true;
    }

    boolean is_alive()
    {
        return alive;
    }

    void time_progress()
    {
        if( in_hospital == true )
        {
            cure_time++;
            if( cure_time >= 10 )
            {
                owner.left_from_hospital();
                cure_time = 0;
                lossed_life = 0;
                in_hospital = false;
                state = false;
            }
        }
        if( (in_hospital == false) & (state == true) )
        {
            lossed_life++;
            if( lossed_life >= total_life )
            {
                owner.die();
                alive = false;
            }
            if( (lossed_life >= 25) & (waiting_for_hospital == false) )
            {
                Hospital hospital = owner.getHospital();
                hospital.apply_hospital( owner );
                waiting_for_hospital = true;
            }
        }
    }



    static void infect( Individual ind_one, Individual ind_two )
    {
        Epidemic negative_epidemic;
        Epidemic_property epidemic_property;
        epidemic_property = Epidemic_property.getInstance();

        //if both individual sick return.
        if( (ind_one.epidemic.state == false) & (ind_two.epidemic.state == false) )
        {
            return;
        }
        //if both individual healthy return.
        if( (ind_one.epidemic.state == true) & (ind_two.epidemic.state == true))
        {
            return;
        }

        if( ind_one.epidemic.state == false )
        {
            negative_epidemic = ind_one.epidemic;
        }
        else {
            negative_epidemic = ind_two.epidemic;
        }

        Interaction intr_one = ind_one.interaction;
        Interaction intr_two = ind_two.interaction;

        double mask_1 = intr_one.get_mask();
        double mask_2 = intr_two.get_mask();
        int collid_time = intr_one.get_max_collide_time( intr_two );
        int distace = intr_one.get_min_s_distance( intr_two );


        double probablity;
        double spread = epidemic_property.get_spreading();
        probablity = spread * (1 + collid_time/10) * mask_1 * mask_2 * (1 - distace/10);
        int percent = (int) (probablity * 100);

        Random r = new Random();
        int random = r.nextInt( 101 );
        if( random <= percent )
        {
            negative_epidemic.state = true;
            negative_epidemic.set_property( epidemic_property );
        }
    }


}
