package cl.afernandez.bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondoAbout = findViewById<ConstraintLayout>(R.id.About_activity)
        if(value.equals("1"))
        {
            fondoAbout.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondoAbout.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }

        val botonhome = findViewById<Button>(R.id.button2)

        botonhome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondoAbout = findViewById<ConstraintLayout>(R.id.About_activity)
        if(value.equals("1"))
        {
            fondoAbout.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondoAbout.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }
    }
}