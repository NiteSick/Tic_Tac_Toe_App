package com.example.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tic_tac_toe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn{
        ZERO,
        CROSS
    }

    private var crossScore = 0
    private var zeroScore = 0
    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS
    private var boardList = mutableListOf<Button>()


    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

    }
    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)

    }

    fun boardTapped(view: View){
        if (view !is Button)
            return
        addToBoard(view)

        if (checkForVictory(ZERO))
        {
            zeroScore++
            result("ZERO WIN!")
        }

        if (checkForVictory(CROSS))
        {
            crossScore++
            result("CROSS WIN!")
        }
        
        if(fullBoard())
        {
            result("Draw")
        }
    }

    private fun checkForVictory(s: String): Boolean {
        //horizontal wins
        if (match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if (match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if (match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true
        //vertical wins
        if (match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if (match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if (match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true
        //diagonal win
        if (match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if (match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true

        return false
    }

    private fun match(button : Button,symbol : String) = button.text == symbol

    private fun result(title: String) {
        val message = "\n O Points : $zeroScore\n\nX Points : $crossScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset"){
                _,_ -> resetBoard()
            }.setCancelable(false).show()

    }

    private fun resetBoard() {
        for(button in boardList){
            button.text =""
        }
        if(firstTurn == Turn.ZERO)
            firstTurn == Turn.CROSS
        else if (firstTurn == Turn.CROSS)
            firstTurn == Turn.ZERO

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for(button in boardList)
        {
            if (button.text == "")
                return false
        }
        return true

    }


    private fun addToBoard(button: Button) {
        if(button.text != "")
            return

        if (currentTurn == Turn.ZERO)
        {
            button.text = ZERO
            currentTurn = Turn.CROSS
        }
       else if (currentTurn == Turn.CROSS)
        {
            button.text = CROSS
            currentTurn = Turn.ZERO
        }
        setTurnLabel()

    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currentTurn == Turn.CROSS)
            turnText = "Turn $CROSS"
        else if(currentTurn == Turn.ZERO)
            turnText = "Turn $ZERO"

        binding.turnTv.text = turnText
    }

    companion object{
        const val ZERO ="O"
        const val CROSS = "X"
    }

    fun scoreReset(view:View) {
        zeroScore = 0
        crossScore = 0
    }


}
