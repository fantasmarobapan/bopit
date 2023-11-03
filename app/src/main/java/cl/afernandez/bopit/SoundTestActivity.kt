package cl.afernandez.bopit

import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SoundTestActivity : AppCompatActivity() {
    private lateinit var mediaPlayerWin: MediaPlayer
    private lateinit var mediaPlayerMusic: MediaPlayer
    private lateinit var mediaPlayerLose: MediaPlayer
    private var playbackParams: PlaybackParams? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_test)

        mediaPlayerWin = MediaPlayer.create(this, R.raw.winsound)
        mediaPlayerMusic = MediaPlayer.create(this, R.raw.backgroundmusic)
        mediaPlayerLose = MediaPlayer.create(this, R.raw.errorsound)

        playbackParams = mediaPlayerMusic.playbackParams

        val winButton = findViewById<Button>(R.id.winbutton)
        val musicButton = findViewById<Button>(R.id.musicbutton)
        val loseButton = findViewById<Button>(R.id.losebutton)
        val speedButton = findViewById<Button>(R.id.speedbutton)
        val slowButton = findViewById<Button>(R.id.slowbutton)

        winButton.setOnClickListener{
            mediaPlayerWin.start()
        }

        musicButton.setOnClickListener{
            mediaPlayerMusic.isLooping = true
            mediaPlayerMusic.start()
        }

        loseButton.setOnClickListener{
            mediaPlayerLose.start()
        }

        speedButton.setOnClickListener{
            playbackParams?.setSpeed(playbackParams!!.speed + 0.5f)
            mediaPlayerMusic.playbackParams = playbackParams!!
        }

        slowButton.setOnClickListener{
            playbackParams?.setSpeed(playbackParams!!.speed - 0.5f)
            mediaPlayerMusic.playbackParams = playbackParams!!
        }
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayerMusic.isPlaying){
            mediaPlayerMusic.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerMusic.release()
    }
}