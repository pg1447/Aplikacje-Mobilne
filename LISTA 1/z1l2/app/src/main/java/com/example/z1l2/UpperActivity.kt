package com.example.z1l2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.z1l2.databinding.ActivityMainBinding
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class UpperActivity : AppCompatActivity() {

    private var turn = ""
    private var buttons = arrayOf<Array<Button>>()
    private var first_turn = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upper)

        turn = intent.getStringExtra("Starts").toString()
        first_turn = turn
        changeTurn()

        for(i in 0..2){
            var temp_arr = arrayOf<Button>()
            for(j in 0..2){

                var id_str = "button" + i + j
                var button = findViewById<Button>(resources.getIdentifier(id_str, "id", packageName))

                button.setOnClickListener{
                    if(turn == "X"){

                        button.text = "x"

                        turn = "O"

                    }
                    else if(turn == "O"){

                        button.text = "o"

                        turn = "X"

                    }

                    changeTurn()

                    checkIfEnd()
                }

                temp_arr += button

            }

            buttons += temp_arr
        }

    }

    private fun changeTurn(){

        val textView2 = findViewById<TextView>(R.id.textView2).apply{
            text = "Turn: $turn"
        }

    }

    private fun checkIfEnd(){

        var state = arrayOf<Array<String>>()

        for(i in 0..2){

            var temp_arr = arrayOf<String>()

            for(j in 0..2){

                var button_id = "button" + i + j
                var button = findViewById<Button>(resources.getIdentifier(button_id, "id", packageName))

                temp_arr += button.text.toString()

            }

            state += temp_arr
        }


        for(i in 0..2){

            var str = ""
            var str1 = ""
            for(j in 0..2){

                str += state[i][j]
                str1 += state[j][i]

            }

            if(str == "xxx" || str1 == "xxx"){

                returnToMainActivity("X")


            }
            else if(str == "ooo" || str1 == "ooo"){

                returnToMainActivity("O")

            }
        }

        var str = ""
        var str1 = ""

        for(i in 0..2){
            str += state[i][i]
            str1 += state[i][2-i]

        }

        if(str == "xxx" || str1 == "xxx"){

            returnToMainActivity("X")

        }
        else if(str == "ooo" || str1 == "ooo"){

            returnToMainActivity("O")

        }


    }

    fun restart(view : View){

        for(i in 0..2){
            for(j in 0..2){
                buttons[i][j].text = ""
            }
        }

        val textV = findViewById<TextView>(R.id.textView2).apply{

            text = "Turn: $first_turn"

        }

    }

    fun quit(view : View){

        returnToMainActivity("Nobody")

    }

    private fun returnToMainActivity(retMsg : String){

        Toast.makeText(this, "$retMsg won !", Toast.LENGTH_LONG).show()

        val finishIntent = Intent()
        finishIntent.putExtra("winner", retMsg)
        setResult(Activity.RESULT_OK, finishIntent)
        finish();

    }

    
}