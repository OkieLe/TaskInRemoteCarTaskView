package io.github.ole.taskintaskview

import android.view.Display
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.uiAutomator
import io.github.ole.taskintaskview.utils.clickToOpenWindow
import io.github.ole.taskintaskview.utils.onWindowElement
import io.github.ole.taskintaskview.utils.scrollFarthestTo
import io.github.ole.taskintaskview.utils.withDisplay
import io.github.ole.taskintaskview.utils.withPackage
import io.github.ole.taskintaskview.utils.withResourceName
import io.github.ole.taskintaskview.utils.withScrollable
import io.github.ole.taskintaskview.utils.withText
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MinusOneAutomatorTest {
    @Test
    fun startAnotherWorks() {
        uiAutomator {
            startActivity(MinusOne::class.java)
            waitForAppToBeVisible()
            onElement { withScrollable(true) }
                .scrollFarthestTo(Direction.DOWN)
            onElement { withScrollable(true) }
                .scrollFarthestTo(Direction.UP)
            onElement { withResourceName("start_another") }.clickToOpenWindow()
            waitForStableInActiveWindow()
            onElement { withResourceName("back") }.click()
            waitForStableInActiveWindow()
            pressBack()
        }
    }

    @Test
    fun startExternalWorks() {
        uiAutomator {
            startActivity(MinusOne::class.java)
            waitForAppToBeVisible()
            onElement { withResourceName("start_external") }.clickToOpenWindow()
            waitForAppToBeVisible("com.android.settings")
            onWindowElement(
                {
                    withDisplay(Display.DEFAULT_DISPLAY) && withPackage("com.android.settings")
                },
                {
                    withText("google", ignoreCase = true)
                }
            )
            pressBack()
        }
    }
}
