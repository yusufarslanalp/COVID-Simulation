package com.company;


import java.util.Random;

public class Individual implements Observer {

    Interaction interaction;
    Epidemic epidemic;
    Timer timer;
    Cordinate cordinate;
    int speed;
    Map map;
    Mediator mediator;
    Hospital hospital;
    boolean alive = true;

    int prew_sign_x = 0;
    int prew_sign_y = 0;

    Individual( Mediator mediator, int speed, Map map, Timer timer, Hospital hospital )
    {
        this.mediator = mediator;
        this.map = map;
        this.speed = speed;
        this.timer = timer;
        this.hospital = hospital;

        interaction = new Maskless_interaction( this, 9, 5 );
        epidemic = new Epidemic( this, false );

        timer.register_observer( this );
        mediator.log_individual( this );

        Random rand = new Random();
        int x = rand.nextInt(996);
        int y = rand.nextInt(596);

        cordinate = new Cordinate(x, y);

    }



    Individual( Individual right )
    {
        interaction = right.interaction.copy( this );
        epidemic = new Epidemic( right.epidemic );
        epidemic.owner = this;
        timer = right.timer;
        hospital = right.hospital;

        speed = right.speed;
        map = right.map;
        mediator = right.mediator;

        timer.register_observer( this );
        mediator.log_individual( this );

        //do not take cordinate from right
        Random rand = new Random();
        int x = rand.nextInt(996);
        int y = rand.nextInt(596);

        cordinate = new Cordinate(x, y);
    }

    boolean is_state( String state )
    {
        if( state == "infected" )
        {
            if( is_dead() )
            {
                return false;
            }
            return epidemic.get_state();
        }
        else if( state == "healthy" )
        {
            if( is_dead() )
            {
                return false;
            }
            return !(epidemic.get_state());
        }
        else if( state == "hospitalized" )
        {
            if( is_dead() )
            {
                return false;
            }
            return epidemic.is_in_hospital();
        }
        else if( state == "dead" )
        {
            return is_dead();
        }
        System.out.println( "Probably there is a typo in: " + state );
        return false;
    }

    boolean
    is_dead()
    {
        return !(epidemic.is_alive());
    }

    public Hospital getHospital() {
        return hospital;
    }

    void
    set_epidemic( Epidemic epidemic )
    {
        this.epidemic = epidemic;
    }

    public void set_interaction(Interaction interaction) {
        this.interaction = interaction;
    }

    Cordinate get_cordinate()
    {
        return cordinate;
    }

    boolean
    is_healthy()
    {
        return !(epidemic.state);
    }

    boolean
    is_in_interact()
    {
        return interaction.is_interacted();
    }

    void changed(  )
    {
        if( epidemic.is_in_hospital() == false ) {
            mediator.changed( this );
        }
    }

    boolean move_continue()
    {
        Cordinate temp_cord = new Cordinate(cordinate);

        if( prew_sign_y == 0 ) return false;

        temp_cord = temp_cord.right(prew_sign_x * speed );
        temp_cord = temp_cord.up( prew_sign_y * speed );

        if( map.valid_cordinate( temp_cord ) )
        {
            cordinate = new Cordinate( temp_cord );
            return true;
        }
        else return false;
    }

    public void move()
    {
        changed();

        //if there is an interaction dont move individual
        if( interaction.is_interacted() == true ) return;

        if( move_continue() == true ) return;

        Random rand = new Random();

        int dedect_sign_x;
        int dedect_sign_y;
        int sign_x;
        int sign_y;
        Cordinate temp_cord;

        while( true )
        {
            temp_cord = new Cordinate(cordinate);

            dedect_sign_x = rand.nextInt(2);
            if( dedect_sign_x == 0 ) sign_x = -1;
            else sign_x = 1;

            dedect_sign_y = rand.nextInt(2);
            if( dedect_sign_y == 0 ) sign_y = -1;
            else sign_y = 1;

            temp_cord = temp_cord.right( sign_x * speed );
            temp_cord = temp_cord.up( sign_y * speed );

            if( map.valid_cordinate( temp_cord ) )
            {
                prew_sign_x = sign_x;
                prew_sign_y = sign_y;
                cordinate = new Cordinate( temp_cord );
                break;
            }
        }
    }

    @Override
    public void update() {
        interaction.time_progress();
        epidemic.time_progress();
        move();


    }


    public boolean interact(Individual other)
    {
        boolean is_interacted;
        //returns true if there is a possible interaction between
        //two individual. And make interaction between two individual.
        Interaction other_interaction = other.interaction;
        is_interacted = interaction.interact( other_interaction );

        if( is_interacted )
        {
            Epidemic.infect( this, other );
        }

        return is_interacted;
    }

    void
    accepted_hospital()
    {
        epidemic.set_in_hospital( true );
    }

    boolean
    is_alive()
    {
        return alive;
    }

    void
    die()
    {
        alive = false;
        timer.remove_observer( this );
    }

    void
    left_from_hospital(  )
    {
        hospital.left_from_hospital( this );
        //timer.remove_observer( this );
    }
}
