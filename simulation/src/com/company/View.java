package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame implements Observer, ActionListener  {
    Controller controller;
    Mediator mediator; //mediator is model

    JLabel timer_label;
    JTextField timer_tfield;

    JLabel speed_label;
    JTextField speed_tfield;
    JLabel collison_label;
    JTextField collission_tfield;
    JLabel distance_label;
    JTextField distance_tfield;
    JLabel spreading_label;
    JTextField spreading_tfield;
    JLabel mortality_label;
    JTextField mortality_tfield;
    JLabel ind_num_label;
    JTextField ind_num_tfield;

    int timer;

    int speed;
    int social_distance;
    int collision_time;
    double spreading;
    double mortality;

    JRadioButton masked_radio;
    JRadioButton maskless_radio;
    JRadioButton sick_radio;
    JRadioButton healthy_radio;

    JButton increment_timer;
    JButton decrese_timer;


    boolean masked_radio_setted = false;
    boolean maskless_radio_seted = false;
    boolean sick_radio_seted = false;
    boolean heathy_radio_seted = false;

    //default values for masked and healthy
    boolean masked = false;
    boolean healthy = true;

    int num_of_ind = 0;


    JButton start_default_btn;
    JButton add_btn;
    JButton start_btn;
    JButton pause_btn;
    JButton restart_btn;

    ArrayList<State> states;


    ArrayList<JButton> btns;
    JTextArea monitor;



    public View( Controller controller, Mediator mediator ) {
        this.controller = controller;
        this.mediator = mediator;


        initialize_bttns_tfields();






        initComponents();
        this.setTitle("Epidemic Simulator");
        this.setSize(1200, 650);

    }



    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == start_default_btn) {
            controller.star_as_default();
        }
        if (event.getSource() == pause_btn) {
            controller.pause();
        }
        if (event.getSource() == start_btn) {
            controller.start();
        }
        if (event.getSource() == restart_btn) {
            controller.reset();
            states = new ArrayList<State>();
            reset_radio_btns();
            move_btns();
        }
        if (event.getSource() == add_btn ) {
            //System.out.println( "IN add_btn THREAD NAME" + Thread.currentThread().getName() );
            get_from_text_fields();
            send_inputs_to_conttroller();
            controller.add_pressed();
            reset_radio_btns();
        }
        if (event.getSource() == increment_timer ) {
            controller.timer_incremented();
        }
        if (event.getSource() == decrese_timer ) {
            controller.timer_decreased();
        }
        if (event.getSource() == masked_radio) {
            masked_radio_setted = true;
            masked = true;
            if( maskless_radio_seted == true )
            {
                maskless_radio_seted = false;
                maskless_radio.setSelected( false );

            }
        }
        if (event.getSource() == maskless_radio) {
            maskless_radio_seted = true;
            masked = false;
            if( masked_radio_setted == true )
            {
                masked_radio_setted = false;
                masked_radio.setSelected( false );
            }
        }

        if (event.getSource() == healthy_radio) {
            heathy_radio_seted = true;
            healthy = true;
            if( sick_radio_seted == true )
            {
                sick_radio_seted = false;
                sick_radio.setSelected( false );

            }
        }
        if (event.getSource() == sick_radio) {
            sick_radio_seted = true;
            healthy = false;
            if( heathy_radio_seted == true )
            {
                heathy_radio_seted = false;
                healthy_radio.setSelected( false );
            }
        }




    }

    @Override
    public void update() {
        states = mediator.get_states();
        String str = controller.get_monitor_string();
        monitor.setText( str );
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                            move_btns(  );
                    }
                }

        );
    }



    void set_color( JButton button, String color )
    {
        if( color == "red" )
        {
            button.setBackground( Color.red );
        }
        else if( color == "blue" )
        {
            button.setBackground( Color.blue );
        }
        else if( color == "yellow" )
        {
            button.setBackground( Color.yellow );
        }
        else System.out.println( "ERROR in view.set_color" );
    }

    void move_btns(  ){
        //System.out.println( "THREAD NAME" + Thread.currentThread().getName() );


        if( btns != null )
        {
            for( int i = 0; i < btns.size(); i++ )
            {
                getContentPane().remove( btns.get(i) );

            }

        }

        btns = new ArrayList<JButton>();

        for( int i = 0; i < states.size(); i++  )
        {
            btns.add( new JButton( "" ) );
            btns.get(i).setBounds(states.get(i).get_x(), states.get(i).get_y(), 5, 5);
            set_color( btns.get(i), states.get(i).get_color() );
            this.add( btns.get(i) );
        }
        repaint();
    }

    void
    initialize_bttns_tfields(){
        start_default_btn = new JButton( "START AS DEFAULT" );
        start_default_btn.setBounds( 1020, 20, 160, 30 );
        start_default_btn.addActionListener(this);
        this.add(start_default_btn);

        //Masked Maskless Radio Buttons
        masked_radio = new JRadioButton( "masked" );
        masked_radio.setBounds( 1020, 70, 80, 20 );
        masked_radio.addActionListener( this );
        this.add(masked_radio);
        maskless_radio = new JRadioButton( "maskless" );
        maskless_radio.setBounds( 1100, 70, 80, 20 );
        maskless_radio.addActionListener( this );
        this.add( maskless_radio );

        //HEALTY SICK DADIO BUTTONS
        healthy_radio = new JRadioButton( "healthy" );
        healthy_radio.setBounds( 1020, 100, 80, 20 );
        healthy_radio.addActionListener( this );
        this.add(healthy_radio);
        sick_radio = new JRadioButton( "sick" );
        sick_radio.setBounds( 1100, 100, 80, 20 );
        sick_radio.addActionListener( this );
        this.add( sick_radio );



        //INDIVIDUAL NUM
        ind_num_label = new JLabel("Individual Num:");
        ind_num_label.setBounds( 1020, 140, 100, 30 );
        this.add( ind_num_label );
        ind_num_tfield = new JTextField("");
        ind_num_tfield.setBounds( 1115, 140, 70, 30 );
        this.add(ind_num_tfield);


        //SPEED
        speed_label = new JLabel("Speed:");
        speed_label.setBounds( 1020, 180, 90, 30 );
        this.add( speed_label );
        speed_tfield = new JTextField("");
        speed_tfield.setBounds( 1115, 180, 70, 30 );
        this.add(speed_tfield);

        //COLLISION TIME
        collison_label = new JLabel("Collision:");
        collison_label.setBounds( 1020, 220, 90, 30 );
        this.add( collison_label );
        collission_tfield = new JTextField("");
        collission_tfield.setBounds( 1115, 220, 70, 30 );
        this.add(collission_tfield);

        //DISTANCE
        distance_label = new JLabel("Distance:");
        distance_label.setBounds( 1020, 260, 90, 30 );
        this.add( distance_label );
        distance_tfield = new JTextField("");
        distance_tfield.setBounds( 1115, 260, 70, 30 );
        this.add(distance_tfield);

        //SPREADING
        spreading_label = new JLabel("Spreading:");
        spreading_label.setBounds( 1020, 300, 90, 30 );
        this.add( spreading_label );
        spreading_tfield = new JTextField("");
        spreading_tfield.setBounds( 1115, 300, 70, 30 );
        this.add(spreading_tfield);

        //MORTALITY
        mortality_label = new JLabel("Mortality:");
        mortality_label.setBounds( 1020, 340, 90, 30 );
        this.add( mortality_label );
        mortality_tfield = new JTextField("");
        mortality_tfield.setBounds( 1115, 340, 70, 30 );
        this.add(mortality_tfield);

        //TIMER
        timer_label = new JLabel("Timer:");
        timer_label.setBounds( 1020, 380, 40, 30 );
        this.add( timer_label );
        increment_timer = new JButton( "+" );
        increment_timer.setBounds( 1075, 380, 50, 30 );
        increment_timer.addActionListener(this);
        this.add(increment_timer);
        decrese_timer = new JButton( "-" );
        decrese_timer.setBounds( 1130, 380, 50, 30 );
        decrese_timer.addActionListener(this);
        this.add(decrese_timer);

        add_btn = new JButton( "ADD" );
        add_btn.setBounds( 1020, 420, 75, 30 );
        add_btn.addActionListener(this);
        this.add(add_btn);

        start_btn = new JButton( "START" );
        start_btn.setBounds( 1105, 420, 75, 30 );
        start_btn.addActionListener(this);
        this.add(start_btn);

        restart_btn = new JButton( "RESET" );
        restart_btn.setBounds( 1020, 460, 75, 30 );
        restart_btn.addActionListener(this);
        this.add( restart_btn );

        pause_btn = new JButton( "PAUSE" );
        pause_btn.setBounds( 1105, 460, 75, 30 );
        pause_btn.addActionListener(this);
        this.add(pause_btn);

        monitor = new JTextArea( );
        monitor.setBounds( 1020, 500, 160, 90 );
        this.add( monitor );
        monitor.setText( "" );

    }

    void get_from_text_fields()
    {
        try{
            num_of_ind = Integer.parseInt(ind_num_tfield.getText());
        } catch(NumberFormatException ex){ // handle your exception
            num_of_ind = 1;
        }

        try{
            speed = Integer.parseInt(speed_tfield.getText());
        } catch(NumberFormatException ex){ // handle your exception
            speed = 5;
        }

        try{
            social_distance = Integer.parseInt(distance_tfield.getText());
        } catch(NumberFormatException ex){ // handle your exception
            social_distance = 9;
        }

        try{
            collision_time = Integer.parseInt(collission_tfield.getText());
        } catch(NumberFormatException ex){ // handle your exception
            collision_time = 4;
        }

        try{
            spreading = Double.parseDouble(spreading_tfield.getText());
        } catch(NumberFormatException ex){ // handle your exception
            spreading = 0.0;
        }

        try{
            mortality = Double.parseDouble(mortality_tfield.getText());
        } catch(NumberFormatException ex){ // handle your exception
            mortality = 0.0;
        }

        /*System.out.println( "timer: " + timer );
        System.out.println( "masked: " + masked );
        System.out.println( "maskless: " + maskless );
        System.out.println( "speed: " + speed );
        System.out.println( "social_distance: " + social_distance );
        System.out.println( "collision_time: " + collision_time );
        System.out.println( "spreading: " + spreading );
        System.out.println( "mortality" + mortality );*/


    }

    void send_inputs_to_conttroller()
    {
        controller.setMasked( masked );
        controller.setHealthy( healthy );
        controller.setIndividual_num( num_of_ind );
        controller.setSpeed( speed );
        controller.setCollision( collision_time );
        controller.setSocial_distance( social_distance );
        controller.setSpreding( spreading );
        controller.setMortality( mortality );
        controller.setTimer( timer );
    }

    void reset_radio_btns()
    {
        masked_radio.setSelected( false );
        maskless_radio.setSelected( false );
        healthy_radio.setSelected( false );
        sick_radio.setSelected( false );
        masked = false;
        healthy = true;
        masked_radio_setted = false;
        maskless_radio_seted = false;
        sick_radio_seted = false;
        heathy_radio_seted = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>





}
