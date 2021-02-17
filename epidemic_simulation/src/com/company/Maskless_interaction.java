package com.company;

public class Maskless_interaction extends Interaction {


    public Maskless_interaction(Individual owner, int s_distance, int collide_time) {
        super(owner, s_distance, collide_time);
    }

    @Override
    public double get_mask() {
        return 1;
    }

    @Override
    public Interaction copy( Individual owner ) {
        Maskless_interaction result;
        result = new Maskless_interaction( owner, s_distance, collide_time );
        return result;
    }
}
