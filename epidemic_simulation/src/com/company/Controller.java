package com.company;

public class Controller {
    View simulation;
    Mediator mediator; //Mediator is model
    Hospital hospital;

    int individual_num;
    boolean masked;
    boolean healthy;
    int speed;
    int collision;
    int social_distance;
    double spreding;
    double mortality;
    int timer;

    Controller( Mediator mediator, Hospital hospital )
    {
        this.mediator = mediator;
        this.hospital = hospital;
        simulation = new View( this, mediator );
        mediator.register_observer( simulation );
        simulation.setVisible( true );
    }

    String
    get_monitor_string()
    {
        String result = "";
        result += "Infected: " + mediator.get_count_individual( "infected" ) + "\n";
        result += "Healthy: " + mediator.get_count_individual( "healthy" ) + "\n";
        result += "Hospitalized: " + mediator.get_count_individual( "hospitalized" ) + "\n";
        result += "Dead: " + mediator.get_count_individual( "dead" );
        return result;
    }

    void star_as_default()
    {
        for( int i = 0; i < 500-1; i++ )
        {
            mediator.create_individual();
        }
        mediator.create_sick_individual();
        hospital.set_population( 300 );
        start();

    }

    void timer_incremented()
    {
        Timer timer = mediator.timer;
        int period = timer.get_period();
        timer.set_period( period + 100 );
    }

    void timer_decreased() {
        Timer timer = mediator.timer;
        int period = timer.get_period();
        if (period >= 200) {
            timer.set_period(period - 100);
            return;
        } else if (period >= 60) {
            timer.set_period(period - 50);
            return;
        }
        else{
            timer.set_period( 10 );
            return;
        }
    }


    void
    reset()
    {
        mediator.reset_simulation();
    }

    void
    add_pressed()
    {
        //if a valid value assigned for spreading and mortality
        //set epidemic property using singleton class;
        if( (spreding > 0.1) & (mortality > 0.1) )
        {
            //this is a singleton class.
            Epidemic_property.getInstance( spreding, mortality );
        }
        if( individual_num >0 )
        {
            add_individual( );
        }
    }

    void add_individual( )
    {
        Individual individual = new Individual( mediator, speed, mediator.map, mediator.timer, hospital );
        Interaction interaction;
        if( masked == true )
        {
            interaction = new Masked_Interaction( individual, social_distance, collision );
        }
        else{
            interaction = new Maskless_interaction( individual, social_distance, collision );
        }
        Epidemic epidemic = new Epidemic( individual, !(healthy) );

        individual.set_interaction( interaction );
        individual.set_epidemic( epidemic );

        for( int i = 0; i < individual_num -1; i++ )
        {
            new Individual( individual );
        }
        mediator.timer.one_pulse();

    }

    void start()
    {
        mediator.timer.set_continue( true );
    }

    void
    pause()
    {
        mediator.timer.set_continue( false );
    }

    public void setIndividual_num(int individual_num) {
        this.individual_num = individual_num;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setCollision(int collision) {
        this.collision = collision;
    }

    public void setSocial_distance(int social_distance) {
        this.social_distance = social_distance;
    }

    public void setSpreding(double spreding) {
        this.spreding = spreding;
    }

    public void setMortality(double mortality) {
        this.mortality = mortality;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
