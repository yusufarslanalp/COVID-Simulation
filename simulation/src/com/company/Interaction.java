package com.company;

import java.util.ArrayList;

public abstract class Interaction {
    protected int s_distance;
    protected int collide_time;
    Individual owner;
    ArrayList<Individual> prev_interactions = new ArrayList<Individual>();
    int remaining_time = 0;
    boolean enable_interaction = true;

    public abstract double get_mask();
    public abstract Interaction copy( Individual owner );

    public Interaction(Individual owner, int s_distance, int collide_time) {
        this.s_distance = s_distance;
        this.collide_time = collide_time;
        this.owner = owner;
    }




    public boolean is_interacted()
    {
        if( remaining_time == 0 )
        {
            return false;
        }
        return true;

    }

    void time_progress()
    {
        if( remaining_time >= 1 ) remaining_time--;
    }


    public boolean interact( Interaction other ) {
        if( enable_interaction == false ) return false;
        //no interaction for same objects
        if( this == other ) return false;
        if( prev_interactions.contains( other.owner ) )
        {
            return false;
        }

        int min_distance = get_min_s_distance( other );
        Cordinate cord = owner.cordinate;
        Cordinate other_cord = other.owner.cordinate;

        boolean in_range = cord.is_distance_eq_or_less( other_cord, min_distance);

        if( in_range == false ) return false;

        prev_interactions.add( other.owner );
        remaining_time = get_max_collide_time( other );
        other.remaining_time = get_max_collide_time( other );
        return true;
    }

    public int get_min_s_distance(Interaction other) {
        if( s_distance <= other.s_distance ) return s_distance;
        else return other.s_distance;
    }


    public int get_max_collide_time(Interaction other) {
        if( collide_time >= other.collide_time ) return collide_time;
        else return other.collide_time;
    }

    void
    set_enable_interaction( boolean value )
    {
        enable_interaction = value;
    }
}
