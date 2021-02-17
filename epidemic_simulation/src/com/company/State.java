package com.company;

public class State {
    boolean helthy;
    boolean in_interaction;
    Cordinate cordinate;

    State( Individual individual )
    {
        helthy = individual.is_healthy();
        in_interaction = individual.is_in_interact();
        cordinate = individual.get_cordinate();
    }

    String
    get_color()
    {
        if( helthy == false ) return "red";
        if( in_interaction )
        {
            return "yellow";
        }
        else return "blue";

    }

    int
    get_x()
    {
        return cordinate.x;
    }

    int
    get_y()
    {
        return cordinate.y;
    }
}
