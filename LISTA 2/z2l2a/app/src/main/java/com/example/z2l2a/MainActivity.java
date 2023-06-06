package com.example.z2l2a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int myNumber;
    public int playerNumber;
    public int hintsCounter = 0;
    public int scores = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generate();
    }

    public void generate(){
        Random ran_gen = new Random();
        myNumber = ran_gen.nextInt(100) + 1;
        String msg = "Number between 1 and 100 generated";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void submitButton(View view){
        EditText input = (EditText) findViewById(R.id.input1);
        TextView points = (TextView) findViewById(R.id.scoresField);
        TextView hints = (TextView) findViewById(R.id.hintsField);

        try{
            playerNumber = Integer.parseInt(input.getText().toString());

        }
        catch(NumberFormatException e){
            Toast.makeText(this, "I expect number!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(playerNumber < myNumber){
            Toast.makeText(this, "My number is bigger", Toast.LENGTH_SHORT).show();
            hintsCounter += 1;
            hints.setText("Hints: " + Integer.toString(hintsCounter));
        }
        else if(playerNumber > myNumber){
            Toast.makeText(this, "My number is smaller", Toast.LENGTH_SHORT).show();
            hintsCounter += 1;
            hints.setText("Hints: " + Integer.toString(hintsCounter));
        }
        else{
            Toast.makeText(this, "Bravo!", Toast.LENGTH_SHORT).show();
            scores += 1;
            points.setText("Scores: " + Integer.toString(scores));
            generate();
        }
    }

    public void answerButton(View view){
        String ans = "My number: " + Integer.toString(myNumber);
        Toast.makeText(this, ans, Toast.LENGTH_SHORT).show();
        generate();
    }

}