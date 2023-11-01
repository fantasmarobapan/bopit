package cl.afernandez.bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TestZoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_zone)

        val buttontestmedia = findViewById<Button>(R.id.testmediabutton)
        val buttontesttouch = findViewById<Button>(R.id.toucheventbutton)
        val buttonshake = findViewById<Button>(R.id.testshakebutton)

        buttontestmedia.setOnClickListener {
            val intenttestmedia = Intent(this, SoundTestActivity::class.java)
            startActivity(intenttestmedia)
        }

        buttontesttouch.setOnClickListener {
            val intenttouchevent = Intent(this, TouchEventActivity::class.java)
            startActivity(intenttouchevent)
        }

        buttonshake.setOnClickListener {
            val intentshake = Intent(this, TestShakeActivity::class.java)
            startActivity(intentshake)
        }
    }
}
