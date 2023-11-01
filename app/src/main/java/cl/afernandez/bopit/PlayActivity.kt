package cl.afernandez.bopit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast

class PlayActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var gestureDetector: GestureDetector
    private val instructions = listOf("Pulsa la pantalla", "Desliza", "Pulsa dos veces")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        textView = findViewById(R.id.instruccion) // Reemplaza R.id.textView con el ID de tu TextView
        gestureDetector = GestureDetector(this, MyGestureListener())

        // Iniciar la ejecución del Runnable para cambiar las instrucciones
        changeInstruction()
    }

    private fun changeInstruction() {
        val randomIndex = instructions.indices.random()
        textView.text = instructions[randomIndex]
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            val currentInstruction = textView.text.toString()
            if (currentInstruction == "Pulsa la pantalla") {
                showToast("Correcto")
                //changeInstruction()
            }
            else{
                showToast("ERROR")
            }
            return true
        }

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            val currentInstruction = textView.text.toString()
            if (currentInstruction == "Desliza") {
                showToast("Correcto")
                //changeInstruction()
            }
            else{
                showToast("ERROR")
            }
            return true
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}