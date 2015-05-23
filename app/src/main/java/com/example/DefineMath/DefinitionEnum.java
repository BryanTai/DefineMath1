package com.example.DefineMath;

/**
 * Created by Bryan on 07/01/2015.
 */
public enum DefinitionEnum {
    //These are the POSSIBLE TOTALDEFINITIONS.
    // Can be possible definition depending on numbers.

    //Stores the index in the boolean[] that they'd be stored in.
    PRIME(0),   //True if Prime, False if Composite
    SIGN(1),    //True if Positive, False if Negative
    PARITY(2), //True if Even, False if Odd

    //Prime is first and Sign is second so we can skip them if Button is 0 or 1.

    // These are the CONSTANT TOTALDEFINITIONS.
    // These will be possible definitions, no matter what numbers are chosen.

    EQUAL(3),
    INEQUAL(4),
    MORE(5),
    LESS(6);

    private final int INDEX;
    DefinitionEnum(int index){
        this.INDEX = index;
    }

    //Total number of POSSIBLE definitions
    public static final int POSDEF = 3;
    //Total number of CONSTANT definitions
    public static final int CONDEF = -1; //TODO
    //Total number of ALL definitions
    public static final int ALLDEF = 3; //TODO


    public int getIndex(){
        return INDEX;
    }
}
