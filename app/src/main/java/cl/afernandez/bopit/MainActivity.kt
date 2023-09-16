package cl.afernandez.bopit

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity() {
    val tagLog ="error"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ButtonOkAbout = findViewById<Button>(R.id.aboutbutton)
        val ButtonOkSetting = findViewById<Button>(R.id.settingbutton)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondo = findViewById<ConstraintLayout>(R.id.Main_activity)

        if(value.equals("1"))
        {
            fondo.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondo.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }

        ButtonOkAbout.setOnClickListener{
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        ButtonOkSetting.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondo = findViewById<ConstraintLayout>(R.id.Main_activity)

        if(value.equals("1"))
        {
            fondo.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondo.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }
    }


}