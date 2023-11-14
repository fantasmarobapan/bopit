package cl.afernandez.bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

class FinishGameActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SUPERO = "EXTRA_SUPERO"
        const val EXTRA_PUNTUACION = "EXTRA_PUNTUACION"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_game)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondoSetting = findViewById<ConstraintLayout>(R.id.finish_game_id)
        if(value.equals("1"))
        {
            fondoSetting.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondoSetting.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }

        val botonhome = findViewById<Button>(R.id.mainActivityButton)

        botonhome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val botonJuegoNuevo = findViewById<Button>(R.id.newGamebutton)

        botonJuegoNuevo.setOnClickListener {
            val intentPlay = Intent(this, PlayActivity::class.java)
            startActivity(intentPlay)
        }

        val supero = intent.getBooleanExtra(EXTRA_SUPERO, false)
        val puntuacion = intent.getIntExtra(EXTRA_PUNTUACION, 0)

        val puntaje = findViewById<TextView>(R.id.puntajeNum)
        val record = findViewById<TextView>(R.id.recordNum)

        puntaje.text = puntuacion.toString()
        record.text = sharedPreferences.getString("player_point", "0").toString()

        if (supero)
        {
            val textoFinal = findViewById<TextView>(R.id.finalText)
            textoFinal.text = "Nuevo Record"
        }

    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondoSetting = findViewById<ConstraintLayout>(R.id.finish_game_id)
        if(value.equals("1"))
        {
            fondoSetting.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondoSetting.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }
    }
}