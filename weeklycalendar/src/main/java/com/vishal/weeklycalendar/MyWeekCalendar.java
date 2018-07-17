package com.vishal.weeklycalendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vishal.weeklycalendar.listener.MyCalenderListener;
import com.vishal.weeklycalendar.utilities.AppController;
import com.vishal.weeklycalendar.utilities.CalUtil;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Weeks;

import java.util.Calendar;


/**
 * Created by vishal.halani on 17-Jan-18.
 */

public class MyWeekCalendar extends Fragment {
    public static  String CURRENT_DATE_BACKGROUND_COLOR = "bg:now_color";
    public static  String DATE_SELECTOR_BACKGROUND_COLOR = "bg:selected_date_color";


    //Declaring Variables

    private LocalDateTime mStartDate = new LocalDateTime();
    private LocalDateTime selectedDate = new LocalDateTime();
    //nowView,
    private TextView monthView, sundayTv, mondayTv, tuesdayTv, wednesdayTv, thursdayTv, fridayTv, saturdayTv;
    private ViewPager pager;
    private LinearLayout mBackground;
    private RelativeLayout calLayout;

    //Calender listener
    private MyCalenderListener myCalenderListener;

    private static MyWeekCalendar mInstance;
    private CalenderAdaptor mAdaptor;

    //Bundle Keys
    public static String DATE_SELECTOR_BACKGROUND = "bg:select:date";
    public static String CURRENT_DATE_SELECTOR_BACKGROUND = "bg:current:select:date";
    public static String CURRENT_DATE_TEXT_COLOR = "bg:current:bg";
    public static String CALENDER_BACKGROUND = "bg:cal";
    public static String NOW_BACKGROUND = "bg:now";
    public static String MONTH_TEXT_COLOR = "bg:primary";
    public static String SECONDARY_BACKGROUND = "bg:secondary";
    public static String PACKAGENAME = "package";
    public static String POSITIONKEY = "pos";
    public static String CALENDER_TYPE = "cal.type";


    //NORMAL CALENDER
    public static int NORMAL_CALENDER = 0;

    //FIRST DAY FIRST CALENDER
    public static int FDF_CALENDER = 1;

    //initial values of calender property

    private String selectorDateIndicatorValue = "bg_select";
    private String currentSelectorDateIndicatorValue = "bg_select_current";
    int currentDateIndicatorValue = Color.WHITE;
    int primaryTextColor = Color.WHITE;
    int currentDateColor = Color.RED;
    int selectedDateColor = Color.GREEN;

    public static String PAKAGENAMEVALUE = "com.vishal.weeklycalendar";

    //Current weekpostion of current day
    public static int CURRENT_WEEK_POSITION = 0;

    public static int calenderType = NORMAL_CALENDER;

    //Default will the current date
    private DateTime start = new DateTime();
    //Default will be 100 more day from the current day
    private DateTime end = start.plusDays(100);
    private FragmentActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing instance

        mInstance = this;

        mActivity = getActivity();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        if (context instanceof AppCompatActivity) {
        mActivity = (FragmentActivity) context;

//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calenderview, container, false);

        pager = (ViewPager) view.findViewById(R.id.vp_pages);
        monthView = (TextView) view.findViewById(R.id.monthTv);
        calLayout = (RelativeLayout) view.findViewById(R.id.calendar_view_cal);
        // nowView = (TextView) view.findViewById(R.id.nowTv);
        sundayTv = (TextView) view.findViewById(R.id.week_sunday);
        mondayTv = (TextView) view.findViewById(R.id.week_monday);
        tuesdayTv = (TextView) view.findViewById(R.id.week_tuesday);
        wednesdayTv = (TextView) view.findViewById(R.id.week_wednesday);
        thursdayTv = (TextView) view.findViewById(R.id.week_thursday);
        fridayTv = (TextView) view.findViewById(R.id.week_friday);
        saturdayTv = (TextView) view.findViewById(R.id.week_saturday);
        mBackground = (LinearLayout) view.findViewById(R.id.background);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //   nowView.setVisibility(View.GONE);

        /*
         * Checking for any customization values
         */
        if (getArguments() != null) {
            if (getArguments().containsKey(CALENDER_BACKGROUND)) {

                mBackground.setBackgroundColor(getArguments().getInt(CALENDER_BACKGROUND));

            }


            // Get date Selector Drawable
            if (getArguments().containsKey(DATE_SELECTOR_BACKGROUND)) {

                selectorDateIndicatorValue = getArguments().getString(DATE_SELECTOR_BACKGROUND);
//                selectorDateIndicatorValue = "bg_select";

            }

            // Get current date Selector Drawable
            if (getArguments().containsKey(CURRENT_DATE_SELECTOR_BACKGROUND)) {

                currentSelectorDateIndicatorValue = getArguments().getString(CURRENT_DATE_SELECTOR_BACKGROUND);



            }

            if (getArguments().containsKey(CURRENT_DATE_TEXT_COLOR)) {

                currentDateIndicatorValue = getArguments().getInt(CURRENT_DATE_TEXT_COLOR);

            }


            if (getArguments().containsKey(CURRENT_DATE_BACKGROUND_COLOR)) {


                currentDateColor = getArguments().getInt(CURRENT_DATE_BACKGROUND_COLOR);

            }


            if (getArguments().containsKey(DATE_SELECTOR_BACKGROUND_COLOR)) {

                // monthView.setTextColor(getArguments().getInt(MONTH_TEXT_COLOR));
                selectedDateColor = getArguments().getInt(DATE_SELECTOR_BACKGROUND_COLOR);

            }



            // get calendar  type
            if (getArguments().containsKey(CALENDER_TYPE)) {

                calenderType = getArguments().getInt(CALENDER_TYPE);
            }

            // get default text color
            if (getArguments().containsKey(MONTH_TEXT_COLOR)) {

                // monthView.setTextColor(getArguments().getInt(MONTH_TEXT_COLOR));
                primaryTextColor = getArguments().getInt(MONTH_TEXT_COLOR);

            }


            if (getArguments().containsKey(SECONDARY_BACKGROUND)) {

                //nowView.setTextColor(getArguments().getInt(SECONDARY_BACKGROUND));
                sundayTv.setTextColor(getArguments().getInt(SECONDARY_BACKGROUND));
                mondayTv.setTextColor(getArguments().getInt(SECONDARY_BACKGROUND));
                tuesdayTv.setTextColor(getArguments().getInt(SECONDARY_BACKGROUND));
                wednesdayTv.setTextColor(getArguments().getInt(SECONDARY_BACKGROUND));
                thursdayTv.setTextColor(getArguments().getInt(SECONDARY_BACKGROUND));
                fridayTv.setTextColor(getArguments().getInt(SECONDARY_BACKGROUND));
                saturdayTv.setTextColor(getArguments().getInt(SECONDARY_BACKGROUND));

            }


            if (getArguments().containsKey(PACKAGENAME)) {

                PAKAGENAMEVALUE = getArguments().getString(PACKAGENAME); //its for showing the resource value from the parent package

            }

        }


//        if (getArguments().containsKey(NOW_BACKGROUND)) {
//
////            Resources resources = getResources();
////            nowView.setBackgroundResource(resources.getIdentifier(getArguments().getString(MyWeekCalendar.NOW_BACKGROUND), "drawable",
////                    PAKAGENAMEVALUE));
//
//        }

        //----------------------------------------------------------------------------------------------//


        /*If the selected calender is FDF Calender the resent the day names according to the starting days*/
        if (calenderType != NORMAL_CALENDER) {
            int startingDate = new LocalDateTime().dayOfWeek().get();
            if (startingDate == 1) {

                sundayTv.setText("Mon");
                mondayTv.setText("Tue");
                tuesdayTv.setText("Wed");
                wednesdayTv.setText("Thu");
                thursdayTv.setText("Fri");
                fridayTv.setText("Sat");
                saturdayTv.setText("Sun");

            } else if (startingDate == 2) {

                sundayTv.setText("Tue");
                mondayTv.setText("Wed");
                tuesdayTv.setText("Thu");
                wednesdayTv.setText("Fri");
                thursdayTv.setText("Sat");
                fridayTv.setText("Sun");
                saturdayTv.setText("Mon");

            } else if (startingDate == 3) {

                sundayTv.setText("Wed");
                mondayTv.setText("Thu");
                tuesdayTv.setText("Fri");
                wednesdayTv.setText("Sat");
                thursdayTv.setText("Sun");
                fridayTv.setText("Mon");
                saturdayTv.setText("Tue");

            } else if (startingDate == 4) {

                sundayTv.setText("Thu");
                mondayTv.setText("Fri");
                tuesdayTv.setText("Sat");
                wednesdayTv.setText("Sun");
                thursdayTv.setText("Mon");
                fridayTv.setText("Tue");
                saturdayTv.setText("Wed");

            } else if (startingDate == 5) {

                sundayTv.setText("Fri");
                mondayTv.setText("Sat");
                tuesdayTv.setText("Sun");
                wednesdayTv.setText("Mon");
                thursdayTv.setText("Tue");
                fridayTv.setText("Wed");
                saturdayTv.setText("Thu");

            } else if (startingDate == 6) {

                sundayTv.setText("Sat");
                mondayTv.setText("Sun");
                tuesdayTv.setText("Mon");
                wednesdayTv.setText("Tue");
                thursdayTv.setText("Wed");
                fridayTv.setText("Thu");
                saturdayTv.setText("Fri");

            }
        }


        /*Setting Calender Adaptor*/

        if (mActivity != null) {

            mAdaptor = new CalenderAdaptor(mActivity.getSupportFragmentManager());
            pager.setAdapter(mAdaptor);

        }




        /*CalUtil is called*/

        CalUtil mCal = new CalUtil();
        //date calculation called according to the typr
//        if (calenderType != NORMAL_CALENDER) {
//            mCal.calculate(mStartDate, FDF_CALENDER);
//        } else {
        mCal.calculate(mStartDate, NORMAL_CALENDER);
//        }

        mStartDate = mCal.getStartDate();//sets start date from CalUtil

        //Setting the month name and selected date listener
        monthView.setText(selectedDate.monthOfYear().getAsShortText().concat("-").concat(selectedDate.year().getAsShortText().toUpperCase()));
        if (selectedDate != null) {
            myCalenderListener.onSelectDate(selectedDate);
            CURRENT_WEEK_POSITION = Weeks.weeksBetween(mStartDate, selectedDate).getWeeks();
        }


        pager.setCurrentItem(CURRENT_WEEK_POSITION);
        /*Week change Listener*/

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int weekNumber) {

                int addDays = weekNumber * 7;

                selectedDate = mStartDate.plusDays(addDays); //add 7 days to the selected date

//                LocalDateTime nextweekFirstdate = mStartDate.plusDays(addDays);

                monthView.setText(selectedDate.monthOfYear().getAsShortText().concat("-").concat(selectedDate.year().getAsShortText().toUpperCase()));

//                if (weekNumber == CURRENT_WEEK_POSITION) {
//
//                    //the first week comes to view
////                    nowView.setVisibility(View.GONE);
//
//
//                } else {
//
//                    //the first week goes from view nowView set visible for Quick return to first week
//
////                    nowView.setVisibility(View.VISIBLE);
//                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /**
         * Change view to  the date of the current week
         */

//        nowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                myCalenderListener.onSelectDate(new LocalDateTime());
//
//                pager.setCurrentItem(CURRENT_WEEK_POSITION);
//
//
//            }
//        });

        /**
         * For quick selection of a date.Any picker or custom date picker can de used
         */
        calLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myCalenderListener.onSelectPicker();


            }
        });


    }

    /**
     * Set set date of the selected week
     *
     * @param calendar
     */
    public void setDateWeek(Calendar calendar) {

        LocalDateTime ldt = LocalDateTime.fromCalendarFields(calendar);

        AppController.getInstance().setSelected(ldt);


        int nextPage = Weeks.weeksBetween(mStartDate, ldt).getWeeks();


        if (nextPage >= 0 && nextPage < getWeekBetweenDates(start, end)) {

            try {
                pager.setCurrentItem(nextPage);
                myCalenderListener.onSelectDate(ldt);

                if (pager.getAdapter() != null) {
                    WeekFragment fragment = (WeekFragment) pager.getAdapter().instantiateItem(pager, nextPage);
                    fragment.ChangeSelector(ldt);
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


    }


    /**
     * Notify the selected date main page
     *
     * @param mSelectedDate
     */
    public void getSelectedDate(LocalDateTime mSelectedDate, boolean fromUservisblity) {
        if (pager.getCurrentItem() == CURRENT_WEEK_POSITION) {
            if (fromUservisblity) {
                myCalenderListener.onSelectDate(new LocalDateTime());
            } else {
                myCalenderListener.onSelectDate(mSelectedDate);
            }

        } else {
            myCalenderListener.onSelectDate(mSelectedDate);
        }

    }

    /*Setting the starting date fom user in put*/
    public void startDate(int year, int month, int date) {
        mStartDate = new LocalDateTime(year, month, date, 0, 0, 0, 0);
        start = new DateTime(year, month, date, 0, 0, 0, 0);
    }

    /*Setting the ending date fom user in put*/
    public void endDate(int year, int month, int date) {
        end = new DateTime(year, month, date, 0, 0, 0, 0);
    }

    /**
     * Adaptor which shows weeks in the view
     */

    private class CalenderAdaptor extends FragmentStatePagerAdapter {

        public CalenderAdaptor(FragmentManager fm) {
            super(fm);
        }

        @Override
        public WeekFragment getItem(int pos) {


            return WeekFragment.newInstance(pos, selectorDateIndicatorValue, currentSelectorDateIndicatorValue, currentDateIndicatorValue, primaryTextColor,selectedDateColor,currentDateColor);

        }

        @Override
        public int getCount() {
            return getWeekBetweenDates(start, end);
        }
    }


    /**
     * Set setMyCalenderListener when user click on a date
     *
     * @param myCalenderListener
     */
    public void setMyCalenderListener(MyCalenderListener myCalenderListener) {
        this.myCalenderListener = myCalenderListener;
    }

    /**
     * creating instance of the calender class
     */
    public static synchronized MyWeekCalendar getInstance() {
        return mInstance;
    }

    public int getWeekBetweenDates(DateTime start, DateTime end) {

        int diff = Weeks.weeksBetween(start, end).getWeeks();
        diff = diff + 1;
        return diff;
    }
/*
    public void SetPrimaryTypeFace(Typeface mFont) {
//        monthView.setTypeface(null, Typeface.NORMAL);
    }
    public void SetSecondaryTypeFace(Typeface mFont) {
//        nowView.setTypeface(mFont);
//        sundayTv.setTypeface(mFont);
//        mondayTv.setTypeface(mFont);
//        tuesdayTv.setTypeface(mFont);
//        wednesdayTv.setTypeface(mFont);
//        thursdayTv.setTypeface(mFont);
//        fridayTv.setTypeface(mFont);
//        saturdayTv.setTypeface(mFont);
    }*/


}