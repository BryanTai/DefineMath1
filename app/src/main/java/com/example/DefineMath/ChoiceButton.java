package com.example.DefineMath;

import android.graphics.Color;
import android.widget.Button;

/**
 * Created by Bryan on 06/02/2015.
 *
 * Represents the Buttons that the user presses to make their choiceNumber.
 */
public class ChoiceButton {
    private int choiceNumber;
    private Button buttonWidget;
    //private boolean[] definitions;
    private Color textColour; //TODO implement some Colours based on score

    public ChoiceButton(int choiceNumber, Button b){
      //  definitions = new boolean[DefinitionEnum.POSDEF];
        buttonWidget = b;
        setChoiceNumber(choiceNumber);
    }

//    /*Given a number, returns a boolean array that represents
//     *all the Definitions the number contains
//     *
//     *
//     *
//    */
//    private void assignDefinitions(int x){
//        definitions[DefinitionEnum.PRIME.getIndex()]  = isPrime(x);
//        definitions[DefinitionEnum.SIGN.getIndex()]   = isPositive(x);
//        definitions[DefinitionEnum.PARITY.getIndex()] = isEven(x);
//    }

    //TODO Should these be moved to DefinitionEnum?



    //General getters and setters

    public int getChoiceNumber() {
        return choiceNumber;
    }

    //Update the Button text and definitions[] when we update the int
    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;
        buttonWidget.setText(Integer.toString(choiceNumber));
       // assignDefinitions(choiceNumber);
    }

    public Button getButtonWidget() {
        return buttonWidget;
    }

    public void setButtonWidget(Button buttonWidget) {
        this.buttonWidget = buttonWidget;
    }
//
//    public boolean[] getDefinitions() {
//        return definitions;
//    }
//
//    public void setDefinitions(boolean[] definitions) {
//        this.definitions = definitions;
//    }
}
