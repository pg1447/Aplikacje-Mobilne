package com.example.z2l2c

import com.example.z2l2c.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.w3c.dom.Text
import java.lang.StringBuilder
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {

    private var wordsArr = arrayOf<String>()

    private var imageIds : IntArray = intArrayOf(R.drawable.wisielec0, R.drawable.wisielec1,
            R.drawable.wisielec2, R.drawable.wisielec3, R.drawable.wisielec4, R.drawable.wisielec5,
            R.drawable.wisielec6, R.drawable.wisielec7, R.drawable.wisielec8, R.drawable.wisielec9,
            R.drawable.wisielec10)

    private var points = 0
    private var failCounter = 0
    private var currString = ""
    private var chosenString = ""
    var outputString = ""
    private var latinAlphabet = "ABCDEFGHIJKLMNOQPRSTUVWXYZ"
    private lateinit var buttons : Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordsArr = resources.getStringArray(R.array.myDict)

        for(i in 0..25){

            var button = findViewById<Button>(resources.getIdentifier(latinAlphabet[i].toString(),
                    "id",
                    packageName))

            button.setOnClickListener{

                var input = findViewById<EditText>(R.id.input1)

                var myLetter = button.text.toString()

                var indexOfLetter = currString.indexOf(myLetter, 0, true)

                if(indexOfLetter == -1){

                    var image = findViewById<ImageView>(R.id.image1)

                    Toast.makeText(this, "Bad !", Toast.LENGTH_SHORT).show()

                    image.setImageResource(imageIds[failCounter])

                    failCounter += 1

                    if(failCounter == 11){
                        Toast.makeText(this, "You lost", Toast.LENGTH_SHORT).show()
                        restartGame()
                    }

                }

                else{

                    Toast.makeText(this, "Good !", Toast.LENGTH_SHORT).show()

                    var chars = currString.toCharArray()
                    chars[indexOfLetter] = '#'
                    currString = String(chars)

                    chars = outputString.toCharArray()
                    var letterToChar = myLetter.toCharArray()
                    chars[indexOfLetter] = letterToChar[0]
                    outputString = String(chars)

                    input.setText(outputString)

                }

                if(outputString.toLowerCase() == chosenString){

                    points += 1

                    var pointsCounter = findViewById<TextView>(R.id.pointsBar)

                    pointsCounter.text = "Points: $points"


                    Toast.makeText(this, "You won!", Toast.LENGTH_SHORT).show()
                    restartGame()

                }


            }

        }

        var restartButton = findViewById<Button>(resources.getIdentifier("restartButton",
                "id",
                packageName))

        restartButton.setOnClickListener{

            restartGame()

        }

        startGame()


    }

    private fun startGame(){

        var image = findViewById<ImageView>(R.id.image1)

        image.setImageResource(R.drawable.init)


        Toast.makeText(this, "Choosing random word !", Toast.LENGTH_SHORT).show()
        chooseWord()
        Toast.makeText(this, "Guess my word !", Toast.LENGTH_SHORT).show()

        var input = findViewById<EditText>(R.id.input1)
        var n = currString.length

        for(i in 0 until n){
            outputString += "#"
        }

        input.setText(outputString)

    }

    private fun chooseWord(){

        outputString = ""

        var n = wordsArr.size
        var idx = nextInt(n)

        currString = wordsArr[idx]
        chosenString = wordsArr[idx]

    }

    private fun restartGame(){

        currString = ""
        outputString = ""

        failCounter = 0

        startGame()
    }



}