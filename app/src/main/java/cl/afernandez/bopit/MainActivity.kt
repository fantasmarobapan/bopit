package cl.afernandez.bopit

import android.content.Intent
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ButtonOkAbout = findViewById<Button>(R.id.aboutbutton)
        val ButtonOkSetting = findViewById<Button>(R.id.settingbutton)
        val ButtonTestMedia = findViewById<Button>(R.id.testmediabutton)
        val ButtonTestTouchEvent = findViewById<Button>(R.id.toucheventbutton)

        ButtonOkAbout.setOnClickListener {
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        ButtonOkSetting.setOnClickListener {
            val intentSetting = Intent(this, SettingsActivity::class.java)
            startActivity(intentSetting)
        }

        ButtonTestMedia.setOnClickListener {
            val intentTestMedia = Intent(this, SoundTestActivity::class.java)
            startActivity(intentTestMedia)
        }

        ButtonTestTouchEvent.setOnClickListener {
            val intentTouchEvent = Intent(this, TouchEventActivity::class.java)
            startActivity(intentTouchEvent)
        }

    }

}