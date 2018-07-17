package com.vishal.weeklycalendar.utilities;



import com.vishal.weeklycalendar.MyWeekCalendar;

import org.joda.time.LocalDateTime;


/**
 * Created by vishal.halani on 17-Jan-18.
 */
public class CalUtil {

    LocalDateTime mStartDate, selectedDate;


    /**
     * Get the day difference in the selected day and the first day in the week
     *
     * @param dayName
     */
    public static int mDateGap(String dayName) {

        if (dayName.equals("mon")) {
            return 1;
        } else if (dayName.equals("tue")) {
            return 2;
        } else if (dayName.equals("wed")) {
            return 3;
        } else if (dayName.equals("thu")) {
            return 4;
        } else if (dayName.equals("fri")) {
            return 5;
        } else if (dayName.equals("sat")) {
            return 6;
        } else if (dayName.equals("sun")) {
            return 0;
        } else {
            return 0;
        }


    }


    /**
     * Initial calculation of the week
     *
     * @param mStartDate
     */
    public void calculate(LocalDateTime mStartDate, int type) {

        //Initializing Start with current month
        final LocalDateTime currentDateTime = mStartDate;

        setStartDate(currentDateTime.getYear(), currentDateTime.getMonthOfYear(), currentDateTime.getDayOfMonth());

        /*Check for difference of weeks for alignment of days*/
        int weekGap = CalUtil.mDateGap(currentDateTime.dayOfWeek().getAsText().substring(0, 3).toLowerCase());


        if (weekGap != 0) {

            //If the there is week gap we need to maintain in the calender else alignment will be a mess

            if (type == MyWeekCalendar.FDF_CALENDER) {
                //If the  week gap is in FDF calender first get the current days number of the week
                int currentWeekNumber = new LocalDateTime().dayOfWeek().get();
                //Subtract it with the rest of the days(Week gap) to get the rest of the days
                weekGap = weekGap - currentWeekNumber;
            }

            //This will add the additional days
            LocalDateTime ldt = mStartDate.minusDays(weekGap);

            // Set the the new startDate after new calculated days
            setStartDate(ldt.getYear(), ldt.getMonthOfYear(), ldt.getDayOfMonth());


        } else {

            //Some times the week gap will be zero in that case If the selected calender is FDFCalender
            if (type == MyWeekCalendar.FDF_CALENDER) {

                //Subtract total days of week (7) with the week day number of current date

                int currentWeekNumber = 7 - new LocalDateTime().dayOfWeek().get();

                if (currentWeekNumber != 0) {

                    // Set the the new startDate after new calculated days

                    LocalDateTime ldt = mStartDate.minusDays(currentWeekNumber);
                    setStartDate(ldt.getYear(), ldt.getMonthOfYear(), ldt.getDayOfMonth());
                }

            }
        }
    }

/*    */

    /**
     * Initial calculation of the week
     *
     * @param
     *//*
    public void calculate(Context mContext,LocalDateTime currentDateTime)
    {
//        //Initializing Start with current month
//        final LocalDateTime currentDateTime = new LocalDateTime();
        setStartDate(currentDateTime.getYear(), currentDateTime.getMonthOfYear(), currentDateTime.getDayOfMonth());
        int weekGap = CalUtil.mDateGap(currentDateTime.dayOfWeek().getAsText().substring(0, 3).toLowerCase());
        if (weekGap != 0) {
            //if the current date is not the first day of the week the rest of days is added
          *//*  //Calendar set to the current date
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -weekGap);
            //now the date is weekGap days back
            Log.i("weekGap", "" + calendar.getTime().toString());
            LocalDateTime ldt = LocalDateTime.fromCalendarFields(calendar);
*//*
            LocalDateTime ldt=currentDateTime.minusDays(weekGap);
            setStartDate(ldt.getYear(), ldt.getMonthOfYear(), ldt.getDayOfMonth());
        }
    }*/


/*Set The Start day (week)from calender*/
    private void setStartDate(int year, int month, int day) {

        mStartDate = new LocalDateTime(year, month, day, 0, 0, 0);
        selectedDate = mStartDate;

        AppController.getInstance().setDate(selectedDate);

    }



 /*   public LocalDateTime getSelectedDate()
    {
        return selectedDate;
    }*/


    public LocalDateTime getStartDate() {
        return mStartDate;
    }


}