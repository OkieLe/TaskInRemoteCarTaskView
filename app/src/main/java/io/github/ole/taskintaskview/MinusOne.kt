package io.github.ole.taskintaskview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.ole.taskview.wm.TaskHostController

class MinusOne : AppCompatActivity() {
    companion object {
        private const val TAG = "MinusOne"
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
        setContentView(R.layout.activity_minus)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.horizontal_gesture_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        hostController.start()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        findViewById<Button>(R.id.start_another).setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.setClass(this, MinusTwo::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            startActivity(intent)
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
