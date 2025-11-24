package io.github.ole.taskview.wm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import io.github.ole.taskview.ITaskHost;
import io.github.ole.taskview.ITaskHostCallback;

public class TaskHostController {
    private static final String TAG = "TaskHostController";
    private final Context mContext;
    private final AtomicInteger mConnectionCount = new AtomicInteger(0);

    private boolean mInputInterceptable = false;
    private boolean mBound;
    private ITaskHost mTaskHost;

    private final ITaskHostCallback mHostCallback = new ITaskHostCallback.Stub() {
        @Override
        public void setInputInterceptable(boolean enabled) {
            mInputInterceptable = enabled;
            Log.d(TAG, "Input interceptable: " + mInputInterceptable);
        }
    };

    private final IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mTaskHost = null;
        }
    };
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mTaskHost = ITaskHost.Stub.asInterface(service);
            if (mTaskHost == null) {
                return;
            }
            try {
                mTaskHost.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to link to death", e);
            }
            Log.d(TAG, "ITaskHost connected");
            try {
                mTaskHost.registerHostCallback(mHostCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to register host", e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mTaskHost == null) {
                return;
            }
            try {
                mTaskHost.unregisterHostCallback(mHostCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to unregister overlay", e);
            }
            Log.d(TAG, "ITaskHost disconnected");
            mTaskHost.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mTaskHost = null;
        }
    };

    private static volatile TaskHostController sInstance;
    public static TaskHostController get(Context context) {
        if (sInstance == null) {
            synchronized (TaskHostController.class) {
                if (sInstance == null) {
                    sInstance = new TaskHostController(context);
                }
            }
        }
        return sInstance;
    }

    private TaskHostController(Context context) {
        mContext = context.getApplicationContext();
    }

    public void start() {
        mConnectionCount.incrementAndGet();
        if (mTaskHost != null) {
            return;
        }
        if (mBound) {
            unbindTaskHost();
        }
        bindTaskHost();
    }

    private void bindTaskHost() {
        Intent intent = new Intent("io.github.ole.taskview.action.GET_HOST");
        intent.setPackage("com.android.launcher3");
        mBound = mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindTaskHost() {
        if (!mBound) {
            return;
        }
        mContext.unbindService(mConnection);
        mBound = false;
    }

    public boolean onBackPressed() {
        if (mTaskHost != null) {
            try {
                return mTaskHost.onOverlayBackPressed();
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to send back press", e);
                return false;
            }
        } else {
            return false;
        }
    }

    public void onScrolled(int scrollX, boolean scrolling) {
        if (mTaskHost != null) {
            try {
                mTaskHost.onOverlayScrolled(scrollX, scrolling);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to send scroll", e);
            }
        }
    }

    public void stop() {
        int count = mConnectionCount.decrementAndGet();
        if (mTaskHost == null || count > 0) {
            return;
        }
        unbindTaskHost();
    }
}
