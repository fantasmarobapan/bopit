package cl.afernandez.bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondoSplash = findViewById<ConstraintLayout>(R.id.Splash_activity)
        if(value.equals("1"))
        {
            fondoSplash.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondoSplash.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}