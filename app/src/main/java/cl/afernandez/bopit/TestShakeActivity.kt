package cl.afernandez.bopit

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.preference.PreferenceManager

class TestShakeActivity : AppCompatActivity() , SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private lateinit var textView: TextView
    private lateinit var fondo: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_shake)

        textView = findViewById(R.id.deteccion)
        fondo =findViewById(R.id.background)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val acceleration = Math.sqrt(x*x + y*y + z*z.toDouble()).toFloat()

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val umbales = sharedPreferences.getString("Umbral_duracion", "10")
            val umbral = Integer.parseInt(umbales)

            if (acceleration > umbral) {
                textView.text = "ESTA VIBRANDO"
                //fondo.setBackgroundColor(Color.BLACK)
            } else {
                textView.text = "NO ESTA VIBRANDO"
                //fondo.setBackgroundColor(Color.WHITE)
            }
        }
    }
}