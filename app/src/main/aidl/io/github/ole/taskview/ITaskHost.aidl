package io.github.ole.taskview;

import io.github.ole.taskview.ITaskHostCallback;

interface ITaskHost {

    /**
     * Register a host to the channel
     * @param host host to register
     */
    oneway void registerHostCallback(ITaskHostCallback host);

    /**
     * Unregister a host from the channel
     * @param host host to unregister
     */
    oneway void unregisterHostCallback(ITaskHostCallback host);

    /**
     * User presses the back button in overlay
     * @return true if the host handled it, false otherwise
     */
    boolean onOverlayBackPressed();

    /**
     * User scrolls the task overlay out
     * @param scrollX the x position of the scroll
     * @param scrolling true if the user is scrolling, false otherwise
     */
    oneway void onOverlayScrolled(int scrollX, boolean scrolling);
}
