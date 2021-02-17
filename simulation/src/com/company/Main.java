package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {



        Epidemic_property.getInstance( 1.0, 0.4 );
        Timer t = new Timer();
        Mediator mediator = new Mediator( t );
        Hospital hospital = new Hospital( mediator );
        mediator.hospital = hospital;
        Controller ctr = new Controller( mediator, hospital );
        t.mediator_observer = mediator;
        t.hospital = hospital;



        t.start();
        System.out.println( "END OF MAIN" );




    }
}
