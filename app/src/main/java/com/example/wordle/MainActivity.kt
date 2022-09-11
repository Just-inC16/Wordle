package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    object GlobalVariable{
        var wordToGuess=FourLetterWordList.getRandomFourLetterWord();
        var totalGuess=4;
        var currGuessNum=1;
        var isWinner=false;
        var allWords=FourLetterWordList.getAllFourLetterWords();
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        //Choose a random word
        Log.v("*****The word chosen is", GlobalVariable.wordToGuess)


        //Guess button functionality
        val guessButton=findViewById<Button>(R.id.guessButton)
        val inputField=findViewById<EditText>(R.id.inputField)
        val reset=findViewById<Button>(R.id.reset)
        val numGuessLeft=findViewById<TextView>(R.id.numGuessLeft)
        val gameOverMsg=findViewById<TextView>(R.id.gameOverMsg)
        val linearLayout=findViewById<LinearLayout>(R.id.linearLayout)

        guessButton.setOnClickListener(){
            //Check that the word entered is valid
            var valid=true
            if(GlobalVariable.isWinner){
                Toast.makeText(this, "You won the game already!", Toast.LENGTH_SHORT).show()
                valid=false
            }
            if(numGuessLeft.getText()=="0"){
                Toast.makeText(this, "No more guesses left!", Toast.LENGTH_SHORT).show()
                valid=false
            }

            val text=inputField.getText()
            for (c in text){
                if(c !in 'A'..'Z' && c !in 'a'..'z') {
                    Toast.makeText(this, "Only alphabet characters!", Toast.LENGTH_SHORT).show()
                    valid=false
                }
            }
            Log.v("*****The word length",text.toString()+inputField.length())
            if(inputField.length().toInt()!=4){
                Toast.makeText(this, "Must be 4 characters!", Toast.LENGTH_SHORT).show()
                valid=false
            }

            if(!GlobalVariable.allWords.contains(text.toString())){
                Toast.makeText(this, "Must be part of the dictionary!", Toast.LENGTH_SHORT).show()
                valid=false
            }
            if(valid){
                Toast.makeText(this,"Successful Guess!", Toast.LENGTH_SHORT).show()
                constructNewGuess(GlobalVariable.currGuessNum,true,text.toString())
                constructNewGuess(GlobalVariable.currGuessNum,false,text.toString())
                numGuessLeft.setText((GlobalVariable.totalGuess-GlobalVariable.currGuessNum).toString())
                GlobalVariable.currGuessNum+=1

                if(numGuessLeft.getText()=="0"){
                    reset.setVisibility(View.VISIBLE)

                    gameOverMsg.setText("You Lost! The word is "+GlobalVariable.wordToGuess)
                    gameOverMsg.setVisibility(View.VISIBLE)
                }
                else if(GlobalVariable.isWinner){
                    reset.setVisibility(View.VISIBLE)

                    gameOverMsg.setText("You Won! The word is "+GlobalVariable.wordToGuess)
                    gameOverMsg.setVisibility(View.VISIBLE)
                }


            }

        }
        reset.setOnClickListener(){
            GlobalVariable.wordToGuess=FourLetterWordList.getRandomFourLetterWord();
            GlobalVariable.totalGuess=4;
            GlobalVariable.currGuessNum=1;
            GlobalVariable.isWinner=false;

            gameOverMsg.setVisibility(View.INVISIBLE)
            reset.setVisibility(View.INVISIBLE)

            numGuessLeft.setText(GlobalVariable.totalGuess.toString())
            inputField.setText("")

            linearLayout.removeAllViews();

        }
    }
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    fun checkGuess(guess: String) : String {
        var guess=guess.uppercase()
        var result = ""
        for (i in 0..3) {
            if (guess[i] == GlobalVariable.wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in GlobalVariable.wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        Log.v("The check of guess", result)
        if(result == "OOOO"){
            GlobalVariable.isWinner=true
        }
        return result
    }
    fun constructNewGuess(numGuess:Int,isGuess:Boolean, actualGuess:String){
        //Create a Horizontal layout
        val horizontalLayout=LinearLayout(this)

        val textView = TextView(this)
        if(isGuess)
            textView.text="Guess #"+numGuess
        else
            textView.text="Guess #"+numGuess +"Check"
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30.toFloat())
        textView.setTextAlignment( View.TEXT_ALIGNMENT_VIEW_START)

        val textView1 = TextView(this)
        if(isGuess)
            textView1.text="                        "
        else
            textView1.text="                 "
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,30.toFloat())

        val textView2 = TextView(this)
        if(isGuess)
            textView2.text=actualGuess
        else
            textView2.text=checkGuess(actualGuess)
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,30.toFloat())
        textView2.setTextAlignment( View.TEXT_ALIGNMENT_VIEW_END)

        horizontalLayout.addView(textView)
        horizontalLayout.addView(textView1)
        horizontalLayout.addView(textView2)
        horizontalLayout.setGravity(Gravity.CENTER);

        //Add the result to vertical layout
        val linearLayout=findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.addView(horizontalLayout)
    }
}

//Possible words
//Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,
//Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot
//,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,
//John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,
//Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,
//Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,
//Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,
//West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,
//Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,
//Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,
//Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,
//Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,
//Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,
//Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,
//Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,
//Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,
//High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,
//Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,
//Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,
//Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,
//Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,
//Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,
//Soon,Sure,That,Then,This,Thus,Very,When,Wide