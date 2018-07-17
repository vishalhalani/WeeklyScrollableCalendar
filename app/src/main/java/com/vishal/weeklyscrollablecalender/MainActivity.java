package com.vishal.weeklyscrollablecalender;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vishal.weeklycalendar.MyWeekCalendar;
import com.vishal.weeklycalendar.listener.MyCalenderListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.LocalDateTime;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private MyWeekCalendar myWeekCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWeekCalanderView();
    }

    /**
     * Set week view calender
     */
    private void setWeekCalanderView() {

        myWeekCalendar = new MyWeekCalendar();

        /*Define you startDate and end Date*/
        myWeekCalendar.startDate(1989, 9, 1);//Start date

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 2);
        myWeekCalendar.endDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));//Ending date

        Bundle args = new Bundle();

        /*Should add this attribute if you adding  the NOW_BACKGROUND or DATE_SELECTOR_BACKGROUND Attribute*/
        args.putString(MyWeekCalendar.PACKAGENAME, getApplicationContext().getPackageName());

        /* IMPORTANT: Customization for the calender commenting or un commenting any of the attribute below will reflect change in calender*/

//---------------------------------------------------------------------------------------------------------------------//


        //set Calender type you want if you don't set any normal calender will be set
        // if (getIntent().getExtras().getInt(MyWeekCalendar.CALENDER_TYPE) == MyWeekCalendar.FDF_CALENDER) {

        /** Set Calender type to FIRSTDAYFIRST (FDF_CALENDER)here
         * the week days will start as current day as first entry
         * eg if current day is friday calender start with fri,sat,etc
         * */
        //     args.putInt(MyWeekCalendar.CALENDER_TYPE, MyWeekCalendar.FDF_CALENDER);
        //  } else {

        /**
         * set Calender type to normal here the week days will
         * start as normal  be like Sun,Mon etc
         * */
        args.putInt(MyWeekCalendar.CALENDER_TYPE, MyWeekCalendar.NORMAL_CALENDER);
        //  }


//      args.putInt(MyWeekCalendar.CALENDER_BACKGROUND, ContextCompat.getColor(this,R.color.md_pink_700));//set background color to calender

        args.putString(MyWeekCalendar.DATE_SELECTOR_BACKGROUND, "bg_select");//set background to the selected dates
        args.putString(MyWeekCalendar.CURRENT_DATE_SELECTOR_BACKGROUND, "bg_select_current");



        args.putInt(MyWeekCalendar.CURRENT_DATE_BACKGROUND_COLOR, ContextCompat.getColor(MainActivity.this, R.color.dark_green));//set color to the currentdate
        args.putInt(MyWeekCalendar.DATE_SELECTOR_BACKGROUND_COLOR, ContextCompat.getColor(MainActivity.this, R.color.dark_blue));//set color to the currentdate


        args.putInt(MyWeekCalendar.CURRENT_DATE_TEXT_COLOR, ContextCompat.getColor(MainActivity.this, R.color.white));//set color to the currentdate

        args.putInt(MyWeekCalendar.MONTH_TEXT_COLOR, ContextCompat.getColor(MainActivity.this, R.color.secondary_text_color));//Set color to the primary views (Month name and dates)

//        args.putInt(MyWeekCalendar.SECONDARY_BACKGROUND, ContextCompat.getColor(this,R.color.md_green_500));//Set color to the secondary views (now view and week names)

//---------------------------------------------------------------------------------------------------------------------//


        myWeekCalendar.setArguments(args);
        final Handler handler = new Handler();
        // Attach to the activity
        handler.post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.weel_cal_container, myWeekCalendar);
                t.commit();
            }
        });

        MyCalenderListener myCalenderListener = new MyCalenderListener() {
            @Override
            public void onSelectPicker() {
                //User can use any type of pickers here the below picker is only Just a example

                Calendar c = Calendar.getInstance();

                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();

                                calendar.set(year, monthOfYear, dayOfMonth);
                                myWeekCalendar.setDateWeek(calendar);//Sets the selected date from Picker

                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                dpd.show(getFragmentManager(), "Datepicker");


            }

            @Override
            public void onSelectDate(LocalDateTime mSelectedDate) {
                //TODO Your logic for selected date here
                Toast.makeText(MainActivity.this,"selected date =>"+mSelectedDate.getDayOfMonth(),Toast.LENGTH_SHORT).show();
            }
        };


        //setting the listener
        if (myWeekCalendar != null) {
            myWeekCalendar.setMyCalenderListener(myCalenderListener);
        }
    }


}
