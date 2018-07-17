package com.vishal.weeklycalendar.utilities;

import android.app.Application;

import com.vishal.weeklycalendar.BuildConfig;

import org.joda.time.LocalDateTime;


import timber.log.Timber;


/**
 * Created by vishal.halani on 17-Jan-18.

 */

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();


    private static AppController mInstance;

    public LocalDateTime setDate, selectedDate;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        JodaTimeAndroid.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }


    }


    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.DebugTree {
        @Override
        public void i(String message, Object... args) {
            // TODO e.g., Crashlytics.log(String.format(message, args));
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);

            // TODO e.g., Crashlytics.logException(t);
        }
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * Set the current week date
     *
     * @param setDate
     */
    public void setDate(LocalDateTime setDate) {
        this.setDate = setDate;
    }

    /*getting the current week*/
    public LocalDateTime getDate() {
        return setDate;
    }

    /*getting the selected week*/

    public LocalDateTime getSelected() {
        return selectedDate;
    }

    /**
     * Setting selected week
     *
     * @param selectedDate
     */
    public void setSelected(LocalDateTime selectedDate) {
        this.selectedDate = selectedDate;
    }


}