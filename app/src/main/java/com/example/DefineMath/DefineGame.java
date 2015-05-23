package com.example.DefineMath;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Master class of DefineGame
 */
public class DefineGame extends Activity {

    //When this reaches maxChoice, raise maxChoice.
    private int score = 0;

    //The range of possible numbers
    //Increases by 10 every 10 levels
    private int maxChoice = 10;
    private int minChoice = -maxChoice;

    //the Buttons that contain the possible number choices
    ChoiceButtons choiceButtons;

    //Keeps track of score
    ScoreBoard scoreBoard;

    //Keeps track of Definition display and the possible definitions for each turn.
    DefinitionText definitionText;

    //TODO add a Timer or CountDownTimer

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game);

        initializeApp();
    }

    /**
     * Initializes a new game
     */
    private void initializeApp() {

        //Create the Buttons and their values
        choiceButtons = new ChoiceButtons(this);
        // Reset score to zero.
        scoreBoard = new ScoreBoard((TextView) findViewById(R.id.scoreNumber));
        // Set initial definitions
        definitionText = new DefinitionText((TextView) findViewById(R.id.currentDefinition), this);

        //Determine the first Definition to use.
       definitionText.pickDefinition(choiceButtons.getDecisionButton());

        //TODO create Timer

        //TODO begin the initial starting timer

    }

    /**
     * pre: 0 <= index < CHOICES
     * Handler for when a ChoiceButton has been chosen by the user.
     * The choice is correct if
     *  - the index of the chosen button matches the decisionButton OR
     *  - the chosen button matches the current definition
     * The choice is wrong otherwise.
     */
    protected void choiceMadeHandler(int index){
        //TODO just changes number for now... NEED TO CHECK IF CHOICE IS CORRECT
        if(index == choiceButtons.getIndexToBaseNextDecisionOn() ||
                definitionText.doesChosenButtonMatchDefinition(choiceButtons.getChoiceButton(index))) {
            correctChoiceHandler(index);
        }else{
            wrongChoiceHandler(index);
        }
    }

    /**
     * Called when a correct button was chosen.
     * Increase the score, choose a new number for the ChoiceButton at index, and pick a new definition
     */
    private void correctChoiceHandler(int index) {
        increaseScore();
        choiceButtons.updateCorrectChoiceButton(index);
        definitionText.pickDefinition(choiceButtons.getDecisionButton());
    }

    /**
     * Called when a wrong button was chosen.
     * TODO make some visual cue that a bad thing happened. Maybe print out reason why
     */
    private void wrongChoiceHandler(int index){
        choiceButtons.updateWrongChoiceButton(index);
    }


    /** increases the score on the scoreBoard
     * If it hits the maxChoice value, increase max and min values by 10
     * TODO add colour
     */
    private void increaseScore(){
        score++;
        scoreBoard.increaseScore();
        if(score >= maxChoice){
            resetMaxMinChoice(maxChoice + 10);
        }
    }

    /** pre: newChoice > 0 and newChoice % 10 == 0
     * Resets maxChoice and minChoice to newChoice
     * Called every time score % 10 == 0
     */
    private void resetMaxMinChoice(int newChoice){
        maxChoice = newChoice;
        minChoice = -newChoice;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public DefinitionText getDefinitionText() {
        return definitionText;
    }


    public int getMaxChoice(){
        return maxChoice;
    }

    public int getMinChoice(){
        return minChoice;
    }


}


