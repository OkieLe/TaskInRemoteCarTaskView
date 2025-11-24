package io.github.ole.taskview;

interface ITaskOverlayCallback {

    /**
     * User presses the back button in overlay
     * @return true if the host handled it, false otherwise
     */
    boolean onOverlayBackPressed();

    /**
     * User scrolls the task overlay out
     * @param scrollX the x position of the scroll
     * @param scrolling true if the user is still scrolling, false otherwise
     */
    oneway void onOverlayScrolled(int scrollX, boolean scrolling);
}
