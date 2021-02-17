package com.company;

public class Masked_Interaction extends Interaction {


    public Masked_Interaction(Individual owner, int s_distance, int collide_time) {
        super(owner, s_distance, collide_time );
    }

    public double get_mask() {
        return 0.2;
    }

    @Override
    public Interaction copy( Individual owner ) {
       Masked_Interaction result;
       result = new Masked_Interaction( owner, s_distance, collide_time );
       return result;
    }


}
