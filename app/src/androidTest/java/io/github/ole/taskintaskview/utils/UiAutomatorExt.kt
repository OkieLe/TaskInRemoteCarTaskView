package io.github.ole.taskintaskview.utils

import android.hardware.display.DisplayManager
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.ElementNotFoundException
import androidx.test.uiautomator.UiAutomatorTestScope
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.onElement
import androidx.test.uiautomator.onElements

fun UiAutomatorTestScope.onChildElement(
    expectedElement: AccessibilityNodeInfo.() -> Boolean,
    expectedChild: AccessibilityNodeInfo.() -> Boolean
): UiObject2 {
    return onElement { expectedElement() }
        .onElement { expectedChild() }
}

fun UiAutomatorTestScope.onChildElements(
    expectedElement: AccessibilityNodeInfo.() -> Boolean,
    expectedChild: AccessibilityNodeInfo.() -> Boolean
): List<UiObject2> {
    return onElement { expectedElement() }
        .onElements { expectedChild() }
}

private fun UiAutomatorTestScope.onWindowOnNull(
    expectedWindow: AccessibilityWindowInfo.() -> Boolean
): AccessibilityWindowInfo? {
    return windows().find { it.expectedWindow() }
}

fun UiAutomatorTestScope.onWindow(
    expectedWindow: AccessibilityWindowInfo.() -> Boolean
): AccessibilityWindowInfo {
    return onWindowOnNull { expectedWindow() }
        ?: throw ElementNotFoundException("Window not found")
}

fun UiAutomatorTestScope.onWindowElement(
    expectedWindow: AccessibilityWindowInfo.() -> Boolean,
    expectedElement: AccessibilityNodeInfo.() -> Boolean
): UiObject2 {
    return onWindow { expectedWindow() }
        .onElement(block = expectedElement)
}

fun UiAutomatorTestScope.onWindowElements(
    expectedWindow: AccessibilityWindowInfo.() -> Boolean,
    expectedElement: AccessibilityNodeInfo.() -> Boolean
): List<UiObject2> {
    return onWindow { expectedWindow() }
        .onElements(block = expectedElement)
}

fun UiAutomatorTestScope.onOverlayWindowElement(
    expectedWindow: AccessibilityWindowInfo.() -> Boolean,
    expectedElement: AccessibilityNodeInfo.() -> Boolean
): UiObject2 {
    return onWindow { expectedWindow() && isOverlayDisplay(displayId) }
        .onElement(block = expectedElement)
}

fun UiAutomatorTestScope.onOverlayWindowElements(
    expectedWindow: AccessibilityWindowInfo.() -> Boolean,
    expectedElement: AccessibilityNodeInfo.() -> Boolean
): List<UiObject2> {
    return onWindow { expectedWindow() && isOverlayDisplay(displayId) }
        .onElements(block = expectedElement)
}

private fun isOverlayDisplay(id: Int): Boolean {
    val context = InstrumentationRegistry.getInstrumentation().context
    val displayManager = context.getSystemService(DisplayManager::class.java)
    return displayManager.getDisplay(id).let {
        it.isValid && it.name.startsWith("BrainOverlay#")
    }
}
