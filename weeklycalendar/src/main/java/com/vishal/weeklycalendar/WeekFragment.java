package com.vishal.weeklycalendar;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.vishal.weeklycalendar.utilities.AppController;
import com.vishal.weeklycalendar.utilities.CalUtil;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Calendar;

import timber.log.Timber;

/**
 * Created by vishal.halani on 17-Jan-18.
 * Developed by 4CInfoengg
 * Project Name PDCA
 */

public class WeekFragment extends Fragment {

    //Declaring Variables

    LocalDateTime mSelectedDate, startDate, mCurrentDate;

    TextView sundayTV, mondayTv, tuesdayTv, wednesdayTv, thursdayTv, fridayTv, saturdayTv;
    TextView[] textViewArray = new TextView[7];

    int datePosition = 0, selectorDateIndicatorValue = 0, currentDateIndicatorValue = 0;
    int currentDateColor = Color.RED;
    int selectedDateColor = Color.GREEN;
    int currentDateselectorDateIndicatorValue = 0;
    ArrayList<LocalDateTime> dateInWeekArray = new ArrayList<>();

    GradientDrawable selectedDateBackground,currentDateBackground;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weekcell, container, false);

        sundayTV = (TextView) view.findViewById(R.id.sun_txt);
        mondayTv = (TextView) view.findViewById(R.id.mon_txt);
        tuesdayTv = (TextView) view.findViewById(R.id.tue_txt);
        wednesdayTv = (TextView) view.findViewById(R.id.wen_txt);
        thursdayTv = (TextView) view.findViewById(R.id.thu_txt);
        fridayTv = (TextView) view.findViewById(R.id.fri_txt);
        saturdayTv = (TextView) view.findViewById(R.id.sat_txt);

        /*Adding WeekViews to array for background changing purpose*/
        textViewArray[0] = sundayTV;
        textViewArray[1] = mondayTv;
        textViewArray[2] = tuesdayTv;
        textViewArray[3] = wednesdayTv;
        textViewArray[4] = thursdayTv;
        textViewArray[5] = fridayTv;
        textViewArray[6] = saturdayTv;


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*Setting the date info in the Application class*/

        startDate = AppController.getInstance().getDate();
        mCurrentDate = AppController.getInstance().getDate();


        /*Setting the Resources values and Customization values to the views*/

        Resources resources = getActivity().getResources();

        selectorDateIndicatorValue = resources.getIdentifier(getArguments().getString(MyWeekCalendar.DATE_SELECTOR_BACKGROUND), "drawable",
                MyWeekCalendar.PAKAGENAMEVALUE);
        currentDateselectorDateIndicatorValue = resources.getIdentifier(getArguments().getString(MyWeekCalendar.CURRENT_DATE_SELECTOR_BACKGROUND), "drawable",
                MyWeekCalendar.PAKAGENAMEVALUE);
        currentDateIndicatorValue = getArguments().getInt(MyWeekCalendar.CURRENT_DATE_TEXT_COLOR);


        currentDateColor = getArguments().getInt(MyWeekCalendar.CURRENT_DATE_BACKGROUND_COLOR);
        selectedDateColor = getArguments().getInt(MyWeekCalendar.DATE_SELECTOR_BACKGROUND_COLOR);



        datePosition = getArguments().getInt(MyWeekCalendar.POSITIONKEY);
        int addDays = datePosition * 7;

        startDate = startDate.plusDays(addDays);//Adding the 7days to the previous week

        mSelectedDate = AppController.getInstance().getSelected();

        selectedDateBackground =(GradientDrawable)getResources().getDrawable(selectorDateIndicatorValue);
        selectedDateBackground.setColor(selectedDateColor);

        currentDateBackground =(GradientDrawable)getResources().getDrawable(currentDateselectorDateIndicatorValue);
        currentDateBackground.setColor(currentDateColor);

        textViewArray[0].setBackground(selectedDateBackground);//Setting the first days of the week as selected

      /*Fetching the data's for the week to display*/

        for (int i = 0; i < 7; i++) {

            if (mSelectedDate != null) {

                if (mSelectedDate.getDayOfMonth() == startDate.getDayOfMonth()) {

                   /*Indicate  if the day is selected*/

                    textViewArray[i].setBackground(selectedDateBackground);
//                    textViewArray[i].setTextColor(getResources().getColor(R.color.white));

                    mDateSelectedBackground(i);//Changing View selector background

                    AppController.getInstance().setSelected(null);//null the selected date

                }
            }

            dateInWeekArray.add(startDate);//Adding the days in the selected week to list


            startDate = startDate.plusDays(1); //Next day


        }

        /*Setting color in the week views*/

        if (datePosition != MyWeekCalendar.CURRENT_WEEK_POSITION) {
            sundayTV.setTextColor(getResources().getColor(R.color.white));
            mondayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            tuesdayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            wednesdayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            thursdayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            fridayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            saturdayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
        } else {
            sundayTV.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            mondayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            tuesdayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            wednesdayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            thursdayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            fridayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));
            saturdayTv.setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));

        }

        /*Displaying the days in the week views*/

        sundayTV.setText(Integer.toString(dateInWeekArray.get(0).getDayOfMonth()));
        mondayTv.setText(Integer.toString(dateInWeekArray.get(1).getDayOfMonth()));
        tuesdayTv.setText(Integer.toString(dateInWeekArray.get(2).getDayOfMonth()));
        wednesdayTv.setText(Integer.toString(dateInWeekArray.get(3).getDayOfMonth()));
        thursdayTv.setText(Integer.toString(dateInWeekArray.get(4).getDayOfMonth()));
        fridayTv.setText(Integer.toString(dateInWeekArray.get(5).getDayOfMonth()));
        saturdayTv.setText(Integer.toString(dateInWeekArray.get(6).getDayOfMonth()));


        /*if the selected week is the current week indicates the current day*/
        if (datePosition == MyWeekCalendar.CURRENT_WEEK_POSITION) {


            for (int i = 0; i < 7; i++) {


                if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == dateInWeekArray.get(i).getDayOfMonth()) {
                    textViewArray[i].setTextColor(currentDateIndicatorValue);
                    textViewArray[i].setBackground(currentDateBackground);
                    mDateSelectedBackground(i);
                }
            }

        }

        /**
         * Click listener of all week days with the indicator change and passing listener info.
         */

        sundayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mSelectedDateInfo(0);
                sundayTV.setBackground(selectedDateBackground);
//                sundayTV.setTextColor(getResources().getColor(R.color.white));
                mDateSelectedBackground(0);


            }
        });

        mondayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mSelectedDateInfo(1);
//                mondayTv.setTextColor(getResources().getColor(R.color.white));
                mondayTv.setBackground(selectedDateBackground);
                mDateSelectedBackground(1);


            }
        });

        tuesdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedDateInfo(2);
//                tuesdayTv.setTextColor(getResources().getColor(R.color.white));
                tuesdayTv.setBackground(selectedDateBackground);
                mDateSelectedBackground(2);


            }
        });


        wednesdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedDateInfo(3);
//                wednesdayTv.setTextColor(getResources().getColor(R.color.white));
                wednesdayTv.setBackground(selectedDateBackground);
                mDateSelectedBackground(3);


            }
        });


        thursdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedDateInfo(4);
//                thursdayTv.setTextColor(getResources().getColor(R.color.white));
                thursdayTv.setBackground(selectedDateBackground);
                mDateSelectedBackground(4);


            }
        });


        fridayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedDateInfo(5);
//                fridayTv.setTextColor(getResources().getColor(R.color.white));
                fridayTv.setBackground(selectedDateBackground);
                mDateSelectedBackground(5);


            }
        });


        saturdayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedDateInfo(6);
//                saturdayTv.setTextColor(getResources().getColor(R.color.white));
                saturdayTv.setBackground(selectedDateBackground);
                mDateSelectedBackground(6);


            }
        });

//---------------------------------------------------------------------------------------//


    }


    /**
     * Set Values including customizable info
     *  @param position
     * @param selectorDateIndicatorValue
     * @param currentDateIndicatorValue
     * @param primaryTextColor
     * @param selectedDateColor
     * @param currentDateColor
     */
    public static WeekFragment newInstance(int position, String selectorDateIndicatorValue, String currentSelectorDateIndicatorValue, int currentDateIndicatorValue, int primaryTextColor, int selectedDateColor, int currentDateColor) {

        WeekFragment f = new WeekFragment();
        Bundle b = new Bundle();

        b.putInt(MyWeekCalendar.POSITIONKEY, position);
        b.putString(MyWeekCalendar.DATE_SELECTOR_BACKGROUND, selectorDateIndicatorValue);
        b.putString(MyWeekCalendar.CURRENT_DATE_SELECTOR_BACKGROUND, currentSelectorDateIndicatorValue);
        b.putInt(MyWeekCalendar.CURRENT_DATE_TEXT_COLOR, currentDateIndicatorValue);
        b.putInt(MyWeekCalendar.MONTH_TEXT_COLOR, primaryTextColor);
        b.putInt(MyWeekCalendar.DATE_SELECTOR_BACKGROUND_COLOR, selectedDateColor);
        b.putInt(MyWeekCalendar.CURRENT_DATE_BACKGROUND_COLOR, currentDateColor);

        f.setArguments(b);

        return f;
    }


    /**
     * Passing the selected date info
     *
     * @param position
     */
    public void mSelectedDateInfo(int position) {
        MyWeekCalendar.getInstance().getSelectedDate(dateInWeekArray.get(position), false);

        mSelectedDate = dateInWeekArray.get(position);

        AppController.getInstance().setSelected(mSelectedDate);


    }

    /**
     * Changing backgrounds of unselected views
     *
     * @param position
     */
    public void mDateSelectedBackground(int position) {

        for (int i = 0; i < textViewArray.length; i++) {
            if (position != i) {


                textViewArray[i].setBackgroundColor(Color.TRANSPARENT);
                textViewArray[i].setTextColor(getArguments().getInt(MyWeekCalendar.MONTH_TEXT_COLOR));


            } else {
                textViewArray[i].setTextColor(getResources().getColor(R.color.white));
            }

        }

        if (datePosition == MyWeekCalendar.CURRENT_WEEK_POSITION) {

            int mposition = CalUtil.mDateGap(new LocalDateTime().dayOfWeek().getAsText().substring(0, 3).toLowerCase());
            textViewArray[mposition].setBackground(currentDateBackground);
            textViewArray[mposition].setTextColor(currentDateIndicatorValue);
        }


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /**
         * Reset date to first day of week when week goes from the view
         */

        if (isVisibleToUser) {

            if (dateInWeekArray.size() > 0)

                MyWeekCalendar.getInstance().getSelectedDate(dateInWeekArray.get(0), true);


        } else {
            if (mSelectedDate != null) {
                if (MyWeekCalendar.calenderType == MyWeekCalendar.NORMAL_CALENDER) {
                    //Resetting the day when current week scrolled or hidden
                    if (datePosition != MyWeekCalendar.CURRENT_WEEK_POSITION) {
                        textViewArray[0].setBackground(selectedDateBackground);
                        textViewArray[0].setTextColor(getResources().getColor(R.color.white));
//                        textViewArray[1].setBackgroundColor(Color.TRANSPARENT);
//                        textViewArray[2].setBackgroundColor(Color.TRANSPARENT);
//                        textViewArray[3].setBackgroundColor(Color.TRANSPARENT);
//                        textViewArray[4].setBackgroundColor(Color.TRANSPARENT);
//                        textViewArray[5].setBackgroundColor(Color.TRANSPARENT);
//                        textViewArray[6].setBackgroundColor(Color.TRANSPARENT);
                        mDateSelectedBackground(0);
                    } else {
                        //if the scrolled week is the current week then reset the selection to the current date


                        int mposition = CalUtil.mDateGap(new LocalDateTime().dayOfWeek().getAsText().substring(0, 3).toLowerCase());
                        Timber.tag("TAG").e("date Pos=>" + mposition);
                        textViewArray[mposition].setBackground(currentDateBackground);
                        textViewArray[mposition].setTextColor(getResources().getColor(R.color.white));
                        mDateSelectedBackground(mposition);
                    }
                } else {
                    //if the scrolled week is the current week then reset the selection to the current date for FDFcalender

                    textViewArray[0].setBackground(selectedDateBackground);
                    //     textViewArray[0].setTextColor(getResources().getColor(R.color.white));
                    textViewArray[1].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[2].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[3].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[4].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[5].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[6].setBackgroundColor(Color.TRANSPARENT);
                }
            }

        }


    }


    /**
     * Setting date when selected form picker
     *
     * @param mSelectedDate
     */

    public void ChangeSelector(LocalDateTime mSelectedDate) {

        LocalDateTime startDate = AppController.getInstance().getDate();
        int addDays = datePosition * 7;


        startDate = startDate.plusDays(addDays);
        if (datePosition != MyWeekCalendar.CURRENT_WEEK_POSITION) {
            for (int i = 0; i < 7; i++) {

                if (mSelectedDate.getDayOfMonth() == startDate.getDayOfMonth()) {
                    textViewArray[i].setBackground(selectedDateBackground);
                    textViewArray[i].setTextColor(getResources().getColor(R.color.white));
                    mDateSelectedBackground(i);


                }
                startDate = startDate.plusDays(1);

            }
        } else {

            for (int i = 0; i < 7; i++) {

                if (mSelectedDate.getDayOfMonth() == startDate.getDayOfMonth()) {
                    textViewArray[i].setBackground(selectedDateBackground);
                    textViewArray[i].setTextColor(getResources().getColor(R.color.white));
                    mDateSelectedBackground(i);
                }

                startDate = startDate.plusDays(1);

            }
//            int position = new LocalDateTime().dayOfWeek().get();
//            textViewArray[position].setTextColor(currentDateIndicatorValue);
        }

    }
}
