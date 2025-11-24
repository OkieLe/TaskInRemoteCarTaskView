package io.github.ole.taskintaskview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import io.github.ole.taskview.wm.TaskHostController

class MinusTwo : AppCompatActivity() {
    companion object {
        private const val TAG = "MinusTwo"
    }
    private val hostController by lazy { TaskHostController.get(this) }
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Log.d(TAG, "handleOnBackPressed")
            if (hostController.onBackPressed().not()) {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minus_2)
        hostController.start()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        findViewById<Button>(R.id.back).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.start_external).setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.setClassName("com.android.settings", "com.android.settings.Settings")
            startActivity(intent)
        }
        findViewById<GestureLayout>(R.id.horizontal_gesture_layout).setOnHorizontalDragListener(
            object : GestureLayout.OnHorizontalDragListener {
                override fun onHorizontalDrag(distancePx: Int) {
                    Log.i(TAG, "onHorizontalDrag $distancePx")
                    hostController.onScrolled(distancePx, true)
                }

                override fun onHorizontalDragEnd(endDistancePx: Int) {
                    Log.d(TAG, "onHorizontalDragEnd: $endDistancePx")
                    hostController.onScrolled(endDistancePx, false)
                }
            }
        )
    }

    override fun onDestroy() {
        hostController.stop()
        super.onDestroy()
    }
}
