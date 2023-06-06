package com.example.z1l2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.z1l2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var message:String
    private var canStart : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }


    fun lowerButton(view : View){

        if(canStart){

            val intent = Intent(this, LowerActivity::class.java).apply{
                putExtra("Starts", message)
            }
            startActivityForResult(intent, 1)

        }
        else{
            Toast.makeText(this, "You haven't choosen any turn !",
                    Toast.LENGTH_LONG).show()
            return
        }


    }

    fun upperButton(view : View){

        if(canStart){

            val intent = Intent(this, UpperActivity::class.java).apply{
                putExtra("Starts", message)
            }
            startActivityForResult(intent, 2)

        }
        else{
            Toast.makeText(this, "You haven't choosen any turn !",
                    Toast.LENGTH_LONG).show()
            return
        }


    }



    fun startsX(view : View){

        message = "X"
        canStart = true

        Toast.makeText(this, "X starts", Toast.LENGTH_LONG).show()

    }

    fun startsO(view : View){

        message = "O"
        canStart = true

        Toast.makeText(this, "O starts", Toast.LENGTH_LONG).show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 || requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                var winner = data?.getStringExtra("winner")
                var winnerView = findViewById<TextView>(R.id.textView5)

                winnerView.text = "Recently won: $winner"

            }
        }
    }

}