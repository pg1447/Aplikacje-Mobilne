package com.example.z2l2b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.z2l2b.databinding.ActivityMainBinding
import java.lang.NumberFormatException
import kotlin.random.Random
import kotlin.text.String

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    private var myNumber = 0;
    var playerNumber = 0;
    var hintsCounter = 0;
    var scores = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        generate();
    }

    private fun generate(){
        myNumber = Random.nextInt(1, 100);
        val msg = "Number between 1 and 100 generated";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    fun answerButton(view: View){
        val ans = "My number is: $myNumber";
        Toast.makeText(this, ans, Toast.LENGTH_SHORT).show();
        generate();
    }

    fun submitButton(view: View){
        val inputStr = binding.input1.text.toString();
        try{
            playerNumber = inputStr.toInt();
        }
        catch(exception: NumberFormatException){
            Toast.makeText(this, "I expect number !", Toast.LENGTH_LONG).show();
            return;
        }

        if(playerNumber < myNumber){
            Toast.makeText(this, "My number is bigger", Toast.LENGTH_SHORT).show();
            hintsCounter += 1;
            binding.hintsField.text = "Hints: $hintsCounter";
        }
        else if(playerNumber > myNumber){
            Toast.makeText(this, "My number is smaller", Toast.LENGTH_SHORT).show();
            hintsCounter += 1;
            binding.hintsField.text = "Hints: $hintsCounter";
        }
        else{
            Toast.makeText(this, "Bravo!", Toast.LENGTH_SHORT).show();
            scores += 1;
            binding.scoresField.text = "Scores: $scores";
            generate();
        }

    }


}