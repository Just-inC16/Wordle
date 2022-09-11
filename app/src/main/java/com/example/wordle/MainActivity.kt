package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.text.color
import com.github.jinatonic.confetti.CommonConfetti

class MainActivity : AppCompatActivity() {
    object GlobalVariable{
        var wordToGuess=FourLetterWordList.getRandomFourLetterWord();
        var totalGuess=3;
        var currGuessNum=1;
        var isWinner=false;
        var allWords=FourLetterWordList.getAllFourLetterWords();
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        //Choose a random word
        Log.v("**********************************The word chosen is", GlobalVariable.wordToGuess)


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


                if(GlobalVariable.isWinner){
                    reset.setVisibility(View.VISIBLE)

                    gameOverMsg.setText("You Won! The word is "+GlobalVariable.wordToGuess)
                    gameOverMsg.setVisibility(View.VISIBLE)
                }
                else if(numGuessLeft.getText()=="0"){
                    reset.setVisibility(View.VISIBLE)

                    gameOverMsg.setText("You Lost! The word is "+GlobalVariable.wordToGuess)
                    gameOverMsg.setVisibility(View.VISIBLE)
//                    CommonConfetti.rainingConfetti(container, new int[] { Color.BLACK })
//                        .infinite();
                }


            }

        }
        reset.setOnClickListener(){
            GlobalVariable.wordToGuess=FourLetterWordList.getRandomFourLetterWord();
            Log.v("**********************************The word chosen is", GlobalVariable.wordToGuess)
            GlobalVariable.totalGuess=3;
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
//                val ch= guess[i]
                result += "X"
            }
        }
        Log.v("The check of guess", result)
        if(result == "OOOO"){
            GlobalVariable.isWinner=true
        }

        return result
    }
    fun convertSymbol(symbolRep:String,actualGuess:String): SpannableStringBuilder {
        val s = SpannableStringBuilder(actualGuess)
//        val s = SpannableString(actualGuess)
//

//            .append("First Part Not Bold ")
//            .bold { append("BOLD") }
//            .append("Rest not bold")
//
//        for (ch in symbolRep){
//
//        }
//        for(i in actualGuess.indices){
//            if(symbolRep[i].equals("O")){
//                s.color(ForegroundColorSpan(Color.BLUE), { append(actualGuess[i]) })
//            }
//            else if(symbolRep[i].equals("+")){
//                s.color(2, { append(actualGuess[i]) })
//            }
//            else{
//                s.color(3, { append(actualGuess[i]) })
//            }
//        }
        //Total Length
        val size=symbolRep.length
        //Define colors
        val mRed = ForegroundColorSpan(Color.RED)
        val mGreen = ForegroundColorSpan(Color.GREEN)
        val mBlack = ForegroundColorSpan(Color.BLACK)
        Log.v("Current symbols", s.toString())
        var res=mRed
        for(i in actualGuess.indices){
            Log.v("The current iteration is", symbolRep[i].toString())
            if(symbolRep[i].toString()=="O"){
                Log.v("We are executing this green", "")
                res=mGreen
            }
            else if(symbolRep[i].toString()=="+"){
                Log.v("We are executing this red", "")
//                s.append(actualGuess[i])
////                s.setSpan(mRed, i,size, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//                s.setSpan(mRed,i, i+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                res=mRed
            }
            else{
//                s.append(actualGuess[i])
//                s.setSpan(mBlack, i, size, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                Log.v("We are executing this green","")
//                s.setSpan(mBlack,i, i+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                res=mBlack
            }
//            s.append(actualGuess[i])
//                s.setSpan(mGreen,i, size, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            s.setSpan(res,i, i+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
//        s.setSpan(mGreen,0, size, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//        s.append("Chicken").color(mRed)
        return s
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
        else{
//            Spannable sb = new SpannableString( finalString );
//            sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), finalString.indexOf(normalBOLD)+ normalBOLD.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //bold
//            sb.setSpan(new AbsoluteSizeSpan(intSize), finalString.indexOf(normalBOLD)+ normalBOLD.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//resize size
            val symbolRep=checkGuess(actualGuess)
            val decipher =convertSymbol(symbolRep,actualGuess)
            textView2.text=decipher
        }


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