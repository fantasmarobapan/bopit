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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ButtonOkAbout = findViewById<Button>(R.id.aboutbutton)
        val ButtonOkSetting = findViewById<Button>(R.id.settingbutton)
        val ButtonTestZone = findViewById<Button>(R.id.testzonebutton)
        val ButtonPlay = findViewById<Button>(R.id.playbutton)


        ButtonOkAbout.setOnClickListener {
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        ButtonOkSetting.setOnClickListener {
            val intentSetting = Intent(this, SettingsActivity::class.java)
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
    }

    override fun onPause() {
        super.onPause()
    }

}