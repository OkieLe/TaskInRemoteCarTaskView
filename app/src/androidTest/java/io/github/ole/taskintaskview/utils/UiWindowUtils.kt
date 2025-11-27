package io.github.ole.taskintaskview.utils

import android.view.Display
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.ByWindowSelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiWindow

object UiWindowUtils {
    fun findWindowWithTitle(title: String, displayId: Int = Display.DEFAULT_DISPLAY): UiWindow? {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findWindow(
            By.Window.displayId(displayId).titleContains(title))
    }

    fun findWindowWithType(pkg: String, @ByWindowSelector.WindowType type: Int,
                   displayId: Int = Display.DEFAULT_DISPLAY): UiWindow? {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findWindow(
            By.Window.displayId(displayId).type(type).pkg(pkg))
    }

    fun findWindowFocused(pkg: String, focused: Boolean = true,
                   displayId: Int = Display.DEFAULT_DISPLAY): UiWindow? {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findWindow(
            By.Window.displayId(displayId).pkg(pkg).focused(focused))
    }
}
