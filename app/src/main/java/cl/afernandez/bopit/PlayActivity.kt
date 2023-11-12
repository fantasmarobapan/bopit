package cl.afernandez.bopit

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager

class PlayActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textinstruccion: TextView
    private lateinit var puntuacion: TextView
    private lateinit var textviewRecord: TextView
    private lateinit var gestureDetector: GestureDetector
    private val instructions = listOf("Pulsa la pantalla", "Desliza", "Agita")
    var puntajePlayer = 0
    var record = 0
    var obtenerPuntaje = true
    var supero = false
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    //private lateinit var mediaPlayerWin: MediaPlayer
    private lateinit var mediaPlayerMusic: MediaPlayer
    //private lateinit var mediaPlayerLose: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        textinstruccion = findViewById(R.id.instruccion)
        puntuacion = findViewById(R.id.puntuacion)
        textviewRecord = findViewById(R.id.textrecord)
        gestureDetector = GestureDetector(this, MyGestureListener())


        mediaPlayerMusic = MediaPlayer.create(this, R.raw.backgroundmusic)


        mediaPlayerMusic.start()
        mediaPlayerMusic.isLooping = true

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val puntajeObtener = sharedPreferences.getString("player_point", "0")
        record = Integer.parseInt(puntajeObtener)

        puntuacion.text = puntajePlayer.toString()
        textviewRecord.text = record.toString()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        changeInstruction()
    }

    private fun changeInstruction() {
        val randomIndex = instructions.indices.random()
        textinstruccion.text = instructions[randomIndex]
    }

    private fun scheduleInstructionChange() {
        Handler(Looper.getMainLooper()).postDelayed({
            changeInstruction()
            obtenerPuntaje = true
        }, 3000)
    }

    private fun errorSound() {
        val mediaPlayerLose = MediaPlayer.create(this, R.raw.errorsound)
        mediaPlayerLose.start()
    }

    private fun correcto() {
        val mediaPlayerWin = MediaPlayer.create(this, R.raw.winsound)
        mediaPlayerWin.start()
        puntajePlayer = puntajePlayer + 1
        puntuacion.text = puntajePlayer.toString()
        obtenerPuntaje = false
        textinstruccion.text = "Correcto"
        if (puntajePlayer > record)
        {
            supero = true
            record = puntajePlayer
            textviewRecord.text = record.toString()
        }

        scheduleInstructionChange()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val handler = Handler(Looper.getMainLooper())
        private val delayMillis = 250
        override fun onDown(e: MotionEvent): Boolean {
            handler.postDelayed({
                val currentInstruction = textinstruccion.text.toString()
                if (currentInstruction == "Pulsa la pantalla" && obtenerPuntaje) {
                    correcto()
                }else if (currentInstruction != "Pulsa la pantalla" && obtenerPuntaje) {
                    errorSound()
                }
            }, delayMillis.toLong())

            return true
        }

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            handler.removeCallbacksAndMessages(null)
            val currentInstruction = textinstruccion.text.toString()
            if (currentInstruction == "Desliza" && obtenerPuntaje) {
                correcto()
            }else if (currentInstruction != "Desliza" && obtenerPuntaje) {
                errorSound()
            }
            return true
        }

    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        editor.putString("player_point", record.toString())
        editor.apply()

        if (supero)
        {

        }

        if (mediaPlayerMusic.isPlaying){
            mediaPlayerMusic.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerMusic.release()
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
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
                val currentInstruction = textinstruccion.text.toString()
                if (currentInstruction == "Agita" && obtenerPuntaje) {
                    correcto()
                }else if (currentInstruction != "Agita" && obtenerPuntaje) {
                    errorSound()
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}