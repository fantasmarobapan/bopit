package cl.afernandez.bopit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast

class TouchEventActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)
        gestureDetector = GestureDetector(this, GestureListener())

    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent) {

        }

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            showToast("onFling")
            return true
        }

        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            return true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}