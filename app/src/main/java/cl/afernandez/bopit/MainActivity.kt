package cl.afernandez.bopit

import android.content.Intent
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity() {

    private lateinit var puntuacion: TextView
    var testeo = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ButtonOkAbout = findViewById<Button>(R.id.aboutbutton)
        val ButtonOkSetting = findViewById<Button>(R.id.settingbutton)
        val ButtonTestZone = findViewById<Button>(R.id.testzonebutton)
        val ButtonPlay = findViewById<Button>(R.id.playbutton)

        puntuacion = findViewById(R.id.testpoint)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val puntaje = sharedPreferences.getString("player_point", "10")
        testeo = Integer.parseInt(puntaje) + 2
        puntuacion.setText(testeo.toString())

        puntuacion.setText(puntaje.toString())

        ButtonOkAbout.setOnClickListener {
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        ButtonOkSetting.setOnClickListener {
            val intentSetting = Intent(this, SettingsActivity::class.java)
            val editor = sharedPreferences.edit()

            editor.putString("player_point", testeo.toString())
            editor.apply()
            startActivity(intentSetting)
        }

        ButtonTestZone.setOnClickListener {
            val intentTestZone = Intent(this, TestZoneActivity::class.java)
            startActivity(intentTestZone)
        }

        ButtonPlay.setOnClickListener {
            val intentPlay = Intent(this, PlayActivity::class.java)
            startActivity(intentPlay)
        }

    }

    override fun onResume() {
        super.onResume()
        puntuacion = findViewById(R.id.testpoint)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val puntaje = sharedPreferences.getString("player_point", "10")
        testeo = Integer.parseInt(puntaje) + 2
        puntuacion.setText(testeo.toString())
    }

    override fun onPause() {
        super.onPause()
    }

}