package cl.afernandez.bopit

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

class PlayActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var textinstruccion: TextView
    private lateinit var puntuacion: TextView
    private lateinit var textviewRecord: TextView
    private lateinit var gestureDetector: GestureDetector
    private val instructions = listOf("Pulsa la pantalla", "Desliza", "Agita")
    private var puntajePlayer = 0
    private var record = 0
    private var obtenerPuntaje = true
    private var supero = false
    private var tiempoLimiteJuego = 0L
    private var aumentoVelocidadMusica = 1.00f
    private var reduccionTiempoLimite = 200
    private var tiempoLimiteReduccion = 5
    private var tiempoExtra = 1000L

    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    private lateinit var musicaFondo: MediaPlayer
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("Theme_color", "1")
        val fondoSetting = findViewById<ConstraintLayout>(R.id.Play_Background)
        if(value.equals("1"))
        {
            fondoSetting.background = ContextCompat.getDrawable(this, R.drawable.gradient1)
        }else if (value.equals("2"))
        {
            fondoSetting.background = ContextCompat.getDrawable(this, R.drawable.gradient2)
        }

        textinstruccion = findViewById(R.id.instruccion)
        puntuacion = findViewById(R.id.puntuacion)
        textviewRecord = findViewById(R.id.textrecord)
        gestureDetector = GestureDetector(this, MyGestureListener())
        handler = Handler(Looper.getMainLooper())


        musicaFondo = MediaPlayer.create(this, R.raw.backgroundmusic)
        musicaFondo.start()
        musicaFondo.isLooping = true

        val puntajeObtener = sharedPreferences.getString("player_point", "0")
        var difficultyselect = sharedPreferences.getString("Difficulty_select", "7000")
        tiempoLimiteJuego = difficultyselect?.toLong() ?: 7000L
        record = Integer.parseInt(puntajeObtener)

        puntuacion.text = puntajePlayer.toString()
        textviewRecord.text = record.toString()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        changeInstruction()

        handler.postDelayed({
            errorSound()
        }, tiempoLimiteJuego)
    }

    private fun changeInstruction() {
        val randomIndex = instructions.indices.random()
        textinstruccion.text = instructions[randomIndex]
    }

    private fun cancelarHandler() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun programarSiguienteCambio() {
        handler.postDelayed({
            changeInstruction()
            obtenerPuntaje = true
        }, tiempoExtra)

        // Iniciar el temporizador
        handler.postDelayed({
            errorSound()
        }, tiempoLimiteJuego + tiempoExtra)
    }

    private fun errorSound() {
        cancelarHandler()
        val mediaPlayerLose = MediaPlayer.create(this, R.raw.errorsound)
        mediaPlayerLose.start()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun correcto() {
        val mediaPlayerWin = MediaPlayer.create(this, R.raw.winsound)
        mediaPlayerWin.start()
        puntajePlayer = puntajePlayer + 1
        puntuacion.text = puntajePlayer.toString()
        obtenerPuntaje = false
        textinstruccion.text = "Correcto"
        if (puntajePlayer > record) {
            supero = true
            record = puntajePlayer
            textviewRecord.text = record.toString()
        }

        // Cancelar el Handler actual antes de programar uno nuevo
        cancelarHandler()

        // Programar el próximo cambio de instrucción y temporizador
        programarSiguienteCambio()

        if (puntajePlayer % tiempoLimiteReduccion == 0 && puntajePlayer > 0) {
            tiempoLimiteJuego -= reduccionTiempoLimite
            aumentoVelocidadMusica += 0.05f

            val params = PlaybackParams()
            params.speed = aumentoVelocidadMusica
            musicaFondo.playbackParams = params
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val handler = Handler(Looper.getMainLooper())
        private val delayMillis = 300

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

    override fun onPause() {
        super.onPause()
        cancelarHandler()
        sensorManager?.unregisterListener(this)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        editor.putString("player_point", record.toString())
        editor.apply()

        if (musicaFondo.isPlaying){
            musicaFondo.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelarHandler()
        if (musicaFondo.isPlaying) {
            musicaFondo.stop()
        }
        musicaFondo.release()
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }
}