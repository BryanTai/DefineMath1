package com.example.DefineMath;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import junit.framework.Assert;


/**
 * Created by Bryan on 23/02/2015.
 */
public class ChoiceButtonTest extends ActivityInstrumentationTestCase2<DefineGame>{
    ChoiceButton cbZero, cbNeg1, cbFive, cbEight, cbNegFive, cbNegEight;
    Button b0,b1,b2,b3,b4,b5;

    ChoiceButton[] choiceButtons;

    public ChoiceButtonTest (){
        super("com.example.DefineMath", DefineGame.class);
/*
        DefineGame defineGame = getActivity();

        choiceButtons = defineGame.getChoiceButtons();

        choiceButtons[0].setChoiceNumber(2);
        choiceButtons[1].setChoiceNumber(5);
        choiceButtons[2].setChoiceNumber(-5);
        choiceButtons[3].setChoiceNumber(8);
*/
    }
/*
    public void testDefinitions(){
        //Zero is going to be hard to test because
        //it is neither positive nor negative
        //and it is neither prime nor composite.

        boolean[] twoD = {true,true,true};
        Assert.assertEquals(twoD, choiceButtons[0].getDefinitions());

        boolean[] fiveD = {true,false,true};
        Assert.assertEquals(fiveD, choiceButtons[1].getDefinitions());

        //Negatives are not prime.
        boolean[] negFiveD = {false,false,false};
        Assert.assertEquals(negFiveD, choiceButtons[2].getDefinitions());

        boolean[] eightD = {true,true,false};
        Assert.assertEquals(eightD,choiceButtons[3].getDefinitions());

    }

    public void testGetChoice(){
        assertEquals(-1, cbNeg1.getChoiceNumber());
    }

    public void testSetChoice(){
        assertEquals(0,cbZero.getChoiceNumber());

    }
    */
}
