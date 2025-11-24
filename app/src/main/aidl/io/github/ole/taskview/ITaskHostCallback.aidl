package io.github.ole.taskview;

interface ITaskHostCallback {

    /**
     * Set input interceptable in task
     * @param enabled send back event in task to host or not
     */
    oneway void setInputInterceptable(boolean enabled);
}
