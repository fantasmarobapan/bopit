package cl.afernandez.bopit

import android.content.Intent
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondoSplash = findViewById<ConstraintLayout>(R.id.Main_activity)
        if(value.equals("1"))
        {
            fondoSplash.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondoSplash.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }


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