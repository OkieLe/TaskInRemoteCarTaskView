package io.github.ole.taskintaskview.utils

import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

fun UiObject2.clickToOpenWindow(timeout: Long = 3000) {
    clickAndWait(Until.newWindow(), timeout)
}

fun UiObject2.scrollFarthestTo(direction: Direction) {
    scrollUntil(direction, Until.scrollFinished(direction))
}

fun UiObject2.waitUntilEnabled(enabled: Boolean, timeout: Long = 3000) {
    wait(Until.enabled(enabled), timeout)
}

fun UiObject2.waitUntilClickable(clickable: Boolean, timeout: Long = 3000) {
    wait(Until.clickable(clickable), timeout)
}

fun UiObject2.waitUntilFocused(focused: Boolean, timeout: Long = 3000) {
    wait(Until.focused(focused), timeout)
}

fun UiObject2.waitUntilSelected(selected: Boolean, timeout: Long = 3000) {
    wait(Until.selected(selected), timeout)
}

fun UiObject2.waitUntilGone(selector: BySelector, timeout: Long = 3000) {
    wait(Until.gone(selector), timeout)
}

fun UiObject2.waitUntilVisible(selector: BySelector, timeout: Long = 3000) {
    wait(Until.hasObject(selector), timeout)
}

fun UiObject2.waitUntilFound(selector: BySelector, timeout: Long = 3000): UiObject2 {
    return this.wait(Until.findObject(selector), timeout)
}
