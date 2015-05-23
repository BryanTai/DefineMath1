package com.example.DefineMath;

import android.test.ActivityInstrumentationTestCase2;
import junit.framework.Assert;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.example.DefineMath.DefineMathTest \
 * com.example.DefineMath.tests/android.test.InstrumentationTestRunner
 */
public class DefineGameTest extends ActivityInstrumentationTestCase2<DefineGame> {
    DefineGame myGame;
    ChoiceButton[] myChoices; //??Why doesnt this work? TODO
    ScoreBoard myScoreBoard;
    DefinitionText myDefinitionText;

    public DefineGameTest() {
        super("com.example.DefineMath", DefineGame.class);
        myGame = getActivity();
        //myChoices = myGame.getChoiceButtons();
        myScoreBoard = myGame.getScoreBoard();
        myDefinitionText = myGame.getDefinitionText();
    }
/*
    public void testInitialization() throws Exception {
        Assert.assertEquals(myGame.CHOICES, myChoices.length);

        for(ChoiceButton cb : myChoices){
            Assert.assertTrue(cb.getChoiceNumber() >= myGame.minChoice);
            Assert.assertTrue(cb.getChoiceNumber() <= myGame.maxChoice);
            Assert.assertEquals(DefinitionEnum.POSDEF, cb.getDefinitions().length);
        }

        Assert.assertEquals(0,myScoreBoard.getScore());
    }

    public void testChoiceButton() throws Exception {

    }
    */
}
