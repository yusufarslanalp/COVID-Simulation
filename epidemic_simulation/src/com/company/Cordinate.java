package com.company;

public class Cordinate
{
    int x;
    int y;

    Cordinate(int new_x, int new_y )
    {
        x = new_x;
        y = new_y;
    }

    Cordinate()
    {
	    x = 0;
        y = 0;
    }
    
    Cordinate(Cordinate right )
    {
        x = right.x;
        y = right.y;
    }
    
    boolean equal( Cordinate crd )
    {
        if( x == crd.x && y == crd.y ) return true;
        else return false;
    }

    boolean is_distance_eq_or_less( Cordinate other, int check_distance )
    {
        //square of distance
        int distance_2 = (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y);

        //square of check distance
        int check_2 = check_distance * check_distance;

        if( distance_2 <= check_2 )
        {
            return true;
        }
        return false;
    }

    Cordinate up( int amount )
    {
        Cordinate new_crd = new Cordinate( x , y + amount );
        return new_crd;
    }
    
    Cordinate right( int amount )
    {
        Cordinate new_crd = new Cordinate( x + amount , y );
        return new_crd;
    }
    



    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }






}

