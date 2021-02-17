package com.company;

public class Epidemic_property {
    private double spreading_factor;
    private double mortality;

    private static Epidemic_property singleton;

    private Epidemic_property( double spreading_factor, double mortality )
    {
        this.spreading_factor = spreading_factor;
        this.mortality = mortality;

    }

    static Epidemic_property
    getInstance(  )
    {
        if( singleton != null )
        {
            return singleton;
        }
        System.out.println( "Sıngleton Object not Inıtialized" );
        return null;
    }

    static Epidemic_property
    getInstance( double spreading_factor, double mortality )
    {
        singleton = new Epidemic_property( spreading_factor, mortality );
        return singleton;
    }

    double
    get_spreading()
    {
        return spreading_factor;
    }

    double
    get_mortality()
    {
        return mortality;
    }
}
