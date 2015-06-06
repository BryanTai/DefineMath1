package com.example.DefineMath;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Keeps track of the definition to display
 */

public class DefinitionText {

    /* Use context.getString( DEFINITIONARRAY( index )) to get strings. */
    final int TOTALDEFINITIONS = 6;
    final int TOTALDEFINITIONPAIRS = TOTALDEFINITIONS /2;

    /** oneNumberDefinitions:
    * The R values of the definitions that only require one number.
    * There are 6 as of now. In order: Prime, Comp, Pos, Neg, Even, Odd
    */
     int[] oneNumberDefinitions =                 // definition pairs
        {R.string.prime,R.string.composite,       // 0) Prime (hard)
            R.string.positive,R.string.negative,  // 1) Sign
            R.string.even,R.string.odd,           // 2) Parity
        };
    /** twoNumberDefinitions:
    * The R values of the definitions that require a second generated number.
    * There are 6 as of now. In order: A factor of, A multiple of, Equal to, (the number itself), More than, Less than
    */
     int[] twoNumberDefinitions =                  // definition pairs
        { R.string.multiple,R.string.factor,       // 0) Divisibility (hard)
            R.string.equalTo, R.string.unequalTo,      // 1) Equality
            R.string.moreThan, R.string.lessThan   // 2) Inequality};
        };
    //Keeping the two arrays separate for readability...
    //TODO Perhaps integrate the arrays? Or even a different data structure?

    //Used for negations of definitions (I.E. "Not Even", "Not A Multiple Of 5")
    String not;

    //The current definition on display based on oneNumberDefinition or twoNumberDefinition
    int currentDefinitionIndex;
    //Are we using a oneNumberDefinition?
    boolean isOneNumberDefinition;
    //Used for twoNumberDefinitions; The number that is randomly generated and displayed along with definition
    int secondNumberToDisplay;

    //Is this current game in HARD MODE?
    boolean isGameInHardMode = false; //TODO have DefineGame determine this somehow

    //App fields
    TextView definitionTextWidget;
    TextView errorTextWidget; //TODO add this to app

    Context context;
    Random rand;

    public DefinitionText(TextView definitionTextWidget, Context context){
        this.context = context;
        this.definitionTextWidget = definitionTextWidget;
        rand = new Random();
        not = context.getString(R.string.not);
    }

    /**
     * Checks whether the chosenButton's definitions matches the currentDefinition.
     */
    public boolean doesChosenButtonMatchDefinition(ChoiceButton chosenButton){
        int currentChoiceNumber = chosenButton.getChoiceNumber();
        if(isOneNumberDefinition){
            switch (currentDefinitionIndex) { //TODO NEED TO CHECK 0 AND 1
                case 0: return  isPrime(currentChoiceNumber);   //Prime
                case 1: return !isPrime(currentChoiceNumber);   //Composite
                case 2: return  isPositive(currentChoiceNumber);//Positive
                case 3: return !isPositive(currentChoiceNumber);//Negative
                case 4: return  isEven(currentChoiceNumber);    //Even
                case 5: return !isEven(currentChoiceNumber);    //Odd
                default : Log.i("COMEONANDSLAM","oneNumberMatching is broken!"); return false;
            }
        }else{
            //twoNumberDefinition
            switch(currentDefinitionIndex) {
                case 0: return isMultipleOf(currentChoiceNumber, secondNumberToDisplay);//Multiple Of
                case 1: return isMultipleOf(secondNumberToDisplay,currentChoiceNumber);//Factor Of
                case 2: return currentChoiceNumber == secondNumberToDisplay;  //Equal
                case 3: return currentChoiceNumber != secondNumberToDisplay;  //Unequal
                case 4: return currentChoiceNumber > secondNumberToDisplay;  //More than
                case 5: return currentChoiceNumber < secondNumberToDisplay;  //Less than
                default : Log.i("COMEONANDSLAM","twoNumberMatching is broken!"); return false;
            }
        }
    }


    /**
     * Picks next definition as currentDefinitionIndex that
     *  - is based on the definitions of the decisionButton AND
     *  - has a lower chance of showing if it was the last definition
     *
     * First decide whether to use a oneNumberDefinition or a twoNumberDefinition (coin flip)
     *
     * If we're using a oneNumberDefinition:
     * - pick a oneNumberDefinition pair at random (with startIndex offset)
     * - choose the entry that matches the decisionButton.
     *
     * Else we're using a twoNumberDefinition:
     * - generate a random number in between max and min
     * - pick a twoNumberDefinition pair at random (with startIndex offset)
     * - assign the secondNumberToDisplay based on the random number and the definition.
     *
     *  startingPairIndex can truncate the definitions of Sign and Prime in the cases that the Button is 0 or 1
     */

    //TODO Break this up into smaller functions
    public void pickDefinition(ChoiceButton decisionButton){
        //TODO perhaps lean towards the least popular definition?
        //boolean[] possibleDefBooleans = decisionButton.getDefinitions();

        //the minimum index from the pair of definitions that we'll pick from
        int startingPairIndex = 0;
        int choiceNumber = decisionButton.getChoiceNumber();

        //If we're on easy mode, skip factor and prime definitions.
        if(isGameInHardMode) //TODO placeholder until isGameInHardMode is implemented...
            startingPairIndex = 1;

        //choose one or twoNumberDefinitions with a coin flip
        isOneNumberDefinition = coinFlip();
        //TODO Set this to test either one or twoNumberDefinitions
       // isOneNumberDefinition = false;

        if(isOneNumberDefinition){
            if(choiceNumber == 1)   //1 is neither Prime nor Composite (so skip these definitions)
                startingPairIndex = 1;
            if(choiceNumber == 0)   //0 is neither Prime nor Composite
                startingPairIndex = 2;                   //and is neither Positive nor Negative (so skip these definitions)
            currentDefinitionIndex = pickDefinitionIndex(startingPairIndex, choiceNumber);
        }
        else{ //twoNumberDefinition
            currentDefinitionIndex = pickDefinitionIndex(startingPairIndex, choiceNumber);
            secondNumberToDisplay = determineSecondNumber(choiceNumber, currentDefinitionIndex);
        }

        updateTextView();
    }

    private boolean coinFlip(){
       return rand.nextInt() % 2 == 0;
    }

    /**
     * determines the the second number depending on the chosen definition
     * the currentDefinitionIndex needs to be decided first
     */
    private int determineSecondNumber(int startingNumber, int currentDefinitionIndex){
        int secondNumber;
        // 1 <= helperNumber < startingNumber
        int helperNumber = generateHelperNumber(startingNumber);

        switch (currentDefinitionIndex) {
            case 0 : //Multiple Of
                secondNumber = generateRandomFactor(startingNumber);
                break;
            case 1 : //Factor Of
                secondNumber = helperNumber * startingNumber;
                break;
            case 2 : //Equal
                secondNumber = startingNumber;
                break;
            case 3 : //Unequal
                secondNumber = (coinFlip()) ? startingNumber * helperNumber : startingNumber + helperNumber;
                break;
            case 4 : //More than
                secondNumber = startingNumber - helperNumber;
                break;
            case 5 : //Less than
                secondNumber = startingNumber + helperNumber;
                break;
            default:
                secondNumber = -1; Log.i("COMEONANDSLAM","secondNumberToDisplay BROKE");break;
        }
        return secondNumber;
    }

    /**
     * generates a helperNumber based on startingNumber
     * return 1 <= helperNumber < abs(startingNumber)
     */
    private int generateHelperNumber(int startingNumber){
        return rand.nextInt(Math.abs(startingNumber) + 1); //TODO use defineGame's min max? or just leave simple?
    }

    /**
     * Generates a random factor of the given choiceNumber
     *
     */
    private int generateRandomFactor(int choiceNumber){
        HashSet<Integer> factors = findFactors(choiceNumber);
        int stopHere = rand.nextInt(factors.size());
        int index = 0;
        for (Integer factor : factors){
            if(index == stopHere)
                return factor;
            index++;
        }
        throw new Error("generateRandomFactor dun goofed");
    }

    /**
     * Finds all the factors of a given integer
     */
    private HashSet<Integer> findFactors(int x){
        HashSet<Integer> factors = new HashSet<Integer>();
        factors.add(x);
        factors.add(1);

        //check if x is negative
        if (x < 0) {
            factors.add(-1);
            x = Math.abs(x);
        }
        int incrementer = 1;
        //if x is odd, we can skip checking all the even factors
        if (!isEven(x))
            incrementer = 2;
        for(int i=1; i*i<=x; i+=incrementer) {
            if(x%i==0)
                factors.add(i);
        }
        return factors;
    }



    /**
     * Randomly generates an index of a definition
     * The currentDefinitionIndex has a lower chance of appearing.
     * secondNumberToDisplay is only used if isOnenumberDefinition is false.
     *
     * required: isOneNumberDefinition has been determined already
     * post: startingPairIndex < returned int < length of definitions/2
     */

    private int pickDefinitionIndex(int startingPairIndex, int choiceNumber){

        //First, randomly pick a pair
        int newPairIndex;
        int max = TOTALDEFINITIONPAIRS - 1;
        newPairIndex = rand.nextInt((max - startingPairIndex) + 1) + startingPairIndex;
        if (newPairIndex == currentDefinitionIndex) //Got the currentDefinitionIndex? Roll again.
            newPairIndex = rand.nextInt((max - startingPairIndex) + 1) + startingPairIndex;

        //Now, determine which index of the pair to use as currentDefinition

        return fromDefinitionPairToSingleIndex(newPairIndex, choiceNumber);
    }

    /**
     * Takes in a choiceNumber and the pair index
     * and returns the proper single index from the oneNumberDefinitions
     */
    private int fromDefinitionPairToSingleIndex(int pairIndex, int choiceNumber){
        if(isOneNumberDefinition) {
            switch (pairIndex) {
                case 0:
                    return isPrime(choiceNumber) ? 0 : 1;
                case 1:
                    return isPositive(choiceNumber) ? 2 : 3;
                case 2:
                    return isEven(choiceNumber) ? 4 : 5;
                default:
                    Log.i("COMEONANDSLAM", "fromDefinitionPairToSingleIndex is broken...");
                    return -1;
            }
        }
        else{ //twoNumberDefinition
            int newIndex = pairIndex * 2; //All twoNumberDefinitions have a 50% chance for now
            return (coinFlip()) ? newIndex : newIndex + 1;
//            switch (pairIndex){
//                case 0 : return coinFlip()  ? 0 : 1; // isMultipleOf works differently
//                case 1 : return coinFlip() ? 2 : 3;
//                case 2 : return coinFlip() ? 4 : 5;
//                default : Log.i("COMEONANDSLAM","fromDefinitionPairToSingleIndex 2.0 is broken...");return -1;
//            }
        }
    }
    /**
     * Takes in a choiceNumber, the pair index, and the secondNumberToDisplay
     * and returns the proper single index from the twoNumberDefinitions
     */
//    private int fromDefinitionPairToSingleIndex(int pairIndex, int choiceNumber, int secondNumber){
//        switch (pairIndex){
//            case 0 : return (choiceNumber >= secondNumber)  ? 0 : 1; // isMultipleOf works differently
//            case 1 : return (choiceNumber == secondNumber) ? 2 : 3;
//            case 2 : return (choiceNumber > secondNumber) ? 4 : 5;
//            default : Log.i("COMEONANDSLAM","fromDefinitionPairToSingleIndex 2.0 is broken...");return -1;
//        }
//    }

    /**
     * Changes the TextView to be the definition at oneNumberDefinitions at currentDefinitionIndex
     * Pre: isOneNumberDefinition, the definition arrays, currentDefinitionIndex, and secondNumberToDisplay have values
     */
    private void updateTextView(){
        //TODO allow for negatives (ie NOT EVEN)
        String toPrint = "";
        if(isOneNumberDefinition) {
            toPrint += context.getString(oneNumberDefinitions[currentDefinitionIndex]);
        }
        else {
            toPrint += context.getString(twoNumberDefinitions[currentDefinitionIndex]);
            toPrint += " " + secondNumberToDisplay;
        }
        definitionTextWidget.setText(toPrint);
    }

    //Zero is neither positive nor negative.
    private boolean isPositive(int x){
        return (x > 0);
    }

    private boolean isEven(int x){
        return (x % 2) == 0;
    }

    /**Based on function written by Oscar_Sanchez
    * Note that negative numbers are composites.
    */
     private boolean isPrime(int x){
        if (x < 0) return false; //Negative numbers are not prime
        //check if x is a multiple of 2
        //but not actually 2 because 2 is prime
        if (x > 2 && isEven(x)) return false;
        //if not, then just check the odds
        for(int i=3; i*i<=x; i+=2) {
            if(x%i==0)
                return false;
        }
        return true;
    }

    private boolean isEqualTo(int x,int y){
        return x == y;
    }

    private boolean isMoreThan(int x, int y){
        return x > y;
    }

    /** True if x is a multiple of y (i.e. 42 is a multiple of 7)
     * reverse for Factors (7 is a factor of 42)
     *
     * Note, zero is a multiple of ANY number
     * but zero is a factor of NOTHING
     * required: y != 0
     */
    private boolean isMultipleOf(int x, int y){
        return x % y == 0;
    }


}
