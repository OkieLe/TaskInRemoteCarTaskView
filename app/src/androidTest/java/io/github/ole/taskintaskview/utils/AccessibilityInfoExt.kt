package io.github.ole.taskintaskview.utils

import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.simpleViewResourceName
import androidx.test.uiautomator.textAsString

fun AccessibilityNodeInfo.withId(@IdRes id: Int): Boolean {
    val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
    val resourceName = targetContext.resources.getResourceName(id)
    return viewIdResourceName == resourceName
}

fun AccessibilityNodeInfo.withText(text: String, ignoreCase: Boolean = false): Boolean {
    return text.equals(textAsString(), ignoreCase)
}

fun AccessibilityNodeInfo.withTextResource(@StringRes id: Int): Boolean {
    val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
    val text = targetContext.resources.getString(id)
    return textAsString() == text
}

fun AccessibilityNodeInfo.withContentDescription(desc: String): Boolean {
    return contentDescription == desc
}

fun AccessibilityNodeInfo.withResourceName(name: String): Boolean {
    return simpleViewResourceName() == name
}

fun AccessibilityNodeInfo.withHintText(text: String): Boolean {
    return hintText == text
}

fun AccessibilityNodeInfo.withScrollable(scrollable: Boolean): Boolean {
    return isScrollable == scrollable
}

fun AccessibilityWindowInfo.withDisplay(id: Int): Boolean {
    return displayId == id
}

fun AccessibilityWindowInfo.withPackage(pkg: String): Boolean {
    return root.packageName == pkg
}

fun AccessibilityWindowInfo.withTitle(title: String): Boolean {
    return this.title?.contains(title) == true
}

fun AccessibilityWindowInfo.withType(type: Int): Boolean {
    return type == this.type
}
