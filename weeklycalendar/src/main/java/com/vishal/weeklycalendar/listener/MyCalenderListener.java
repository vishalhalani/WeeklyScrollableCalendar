package com.vishal.weeklycalendar.listener;

import org.joda.time.LocalDateTime;

/**
 * Created by vishal.halani on 10-Jul-18.
 * Project Name WeeklyScrollableCalander
 */
public abstract class MyCalenderListener {

    /**
     * listener notify if select date picker
     */
    public abstract void onSelectPicker();

    /**
     * Notify when date selected
     *
     * @param mSelectedDate
     */
    public abstract void onSelectDate(LocalDateTime mSelectedDate);
}
