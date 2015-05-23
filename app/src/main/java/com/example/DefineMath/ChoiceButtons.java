package com.example.DefineMath;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Random;
/**
 * Handles the ChoiceButtons
 */
public class ChoiceButtons {

    //The # of possible choices
    //NOTE: IF THIS EVER GETS UPDATED, ALL THE UI BUTTONS NEED TO BE UPDATED AS WELL
    private final int CHOICES = 4;

    //The possible number choices and the Buttons that contain them
    ChoiceButton[] choiceButtons;

    /** the decisionButton:
     * Used by DefinitionText to determine the next definition to display.
     * The next definition must work for at least the decisionButton.
     * The decisionButton is determined randomly in order to keep game lively.
    */
    //Keeps track of the index of the decisionButton in choiceButtons where the last definition was determined from.
    private int indexToBaseNextDecisionOn = -1;

    DefineGame defineGame;
    Random rand;

    public ChoiceButtons(DefineGame defineGame) {
        this.defineGame = defineGame;
        rand = new Random();
        initializeButtons();
        indexToBaseNextDecisionOn = pickANewDecisionIndex();
    }

    /**
     * Gets references to the Button Widgets
     * and creates a random number for each.
     */
    private void initializeButtons() {
        choiceButtons = new ChoiceButton[CHOICES];
        int[] buttonIdArray = {R.id.button0,R.id.button1,R.id.button2,R.id.button3};

        //Initialize all the ChoiceButtons
        for(int x = 0; x < CHOICES; x++){
            int newChoice = randomlyGenerateNewChoice();
            Button buttonWidget = (Button)defineGame.findViewById(buttonIdArray[x]);
            choiceButtons[x] = new ChoiceButton(newChoice,buttonWidget);

            //Set listeners to each Button
            final int thisButtonIndex = x;
            choiceButtons[x].getButtonWidget().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                //let defineGame handle the correctness checking
                defineGame.choiceMadeHandler(thisButtonIndex);
                }
            });
        }
    }

    /**
     * Randomly selects a new ChoiceButton index to base the next definition on.
     * The original indexToBaseNextDecision has a lower chance of appearing twice in a row.
     */
    private int pickANewDecisionIndex(){
        int newIndex;
        newIndex = rand.nextInt(CHOICES);
        if(newIndex == indexToBaseNextDecisionOn)
            newIndex = rand.nextInt(CHOICES); //Got the current decisionButton? Roll again.
        return newIndex;
    }

    /**
     * returns the decisionButton to base the next definition on
     */
    public ChoiceButton getDecisionButton(){
        return getChoiceButton(indexToBaseNextDecisionOn);
    }

    /**
     * Randomly generates a new number in between maxChoice and minChoice
     * that does not already exist inside a choiceButton
     */
    private int randomlyGenerateNewChoice() {
        //TODO Impliment cache system to remember last 5 numbers.... perhaps an array of 9 to incorperate current buttons?
        //TODO Lean towards numbers that are closer to maxChoice and minChoice
        int newChoice;
        int max = defineGame.getMaxChoice();
        int min = defineGame.getMinChoice();
        do {
            int range = (max - min) + 1;
            newChoice = (int) (range * Math.random()) + min;
        } while (doesChoiceButtonsCurrentlyContain(newChoice));
        return newChoice;
    }

    /**
     * Update the ChoiceButton on a successful choice
     * - regenerate the number of the choiceButton
     * - choose a new decisionButton for the DefinitionText
     */

    public void updateCorrectChoiceButton(int index){
        regenerateChoiceButton(index);
        pickANewDecisionIndex();
    }

    /**
     * Update the ChoiceButton on a wrong choice
     * - Number stays the same but background colour changes
     * -
     */
    public void updateWrongChoiceButton(int index){
        //TODO
        Log.i("COMEONANDSLAM", "Wrong choice!");

    }

    /** pre: 0 <= index < CHOICES
     * Changes the number of the Button at given index
     * Updates the definitions stored by defineText
     */
    private void regenerateChoiceButton(int index){
        //TODO Set colour of Button text
        choiceButtons[index].setChoiceNumber(randomlyGenerateNewChoice());
    }

    /**
     * True if and only if a choiceButton contains num (and thus is currently displayed in choiceButtons)
     * If cb is null, return false
     */
    private boolean doesChoiceButtonsCurrentlyContain(int num){
        for(ChoiceButton cb : choiceButtons){
            if(cb == null) return false;
            if(cb.getChoiceNumber() == num) return true;
        }
        return false;
    }

    public ChoiceButton getChoiceButton(int index){
        return choiceButtons[index];
    }
    public int getIndexToBaseNextDecisionOn() { return indexToBaseNextDecisionOn;}


}
