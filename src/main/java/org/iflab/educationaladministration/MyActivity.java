package org.iflab.educationaladministration;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;


public class MyActivity extends Activity implements View.OnTouchListener {

    private RelativeLayout relativeLayout;


    private HorizontalScrollView horizontalScrollView;
    private ScrollView scrollView;
    private HorizontalScrollView courseHo;
    private ScrollView courseSc;

    private RelativeLayout.LayoutParams horizontalScrollViewlayoutParams;
    private RelativeLayout.LayoutParams scrollViewlayoutParams;
    private LinearLayout soLinear;
    private LinearLayout hoLinear;

    private RelativeLayout courseRe;

    private MyLinearLayout myRelatiLayout;

    private int down_scroX;
    private int down_scroY;
    private int down_x;
    private int down_y;
    private int now_x;
    private int now_y;

    private int weekHeight;
    private String[] weekNames = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        init();
        relativeLayout = (RelativeLayout) findViewById(R.id.aa);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scro);
        scrollView = (ScrollView) findViewById(R.id.scroview);
        courseHo = (HorizontalScrollView) findViewById(R.id.course_ho);
        courseSc = (ScrollView) findViewById(R.id.course_sc);
        hoLinear = (LinearLayout) findViewById(R.id.hoLinear);
        soLinear = (LinearLayout) findViewById(R.id.soLinear);
        courseRe = (RelativeLayout) findViewById(R.id.course_re);
        myRelatiLayout = (MyLinearLayout) findViewById(R.id.myrela);
        myRelatiLayout.setOnTouchListener(this);
        for (int i = 0; i < 7; i++) {
            setWeek(weekNames[i], i);
        }
        for (int i = 0; i < 12; i++) {
            setCourseNumber(i + 1);
        }
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        setCourse(12);
    }

    private void init() {
        weekHeight = dipTopx(this, 50);
        System.out.println(weekHeight);
    }

    public static int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setWeek(String weekName, int week) {
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(weekHeight * 2, weekHeight);
        button.setText(weekName);
        button.setLayoutParams(params);
        if (week % 2 == 1) {
            button.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.md_light_blue_A200));
        }
        button.setTextColor(getResources().getColor(R.color.md_white_1000));
        hoLinear.addView(button);
    }

    private void setCourseNumber(int courseNumber) {
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(weekHeight, weekHeight * 2);
        button.setText(String.valueOf(courseNumber));
        button.setLayoutParams(params);
        if (courseNumber % 2 == 1) {
            button.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.md_light_blue_A200));
        }
        button.setTextColor(getResources().getColor(R.color.md_white_1000));
        soLinear.addView(button);
    }

    private void setCourse(int courseNumber) {
        for (int i = 0; i < courseNumber / 2; i++) {
            for (int j = 0; j < 7; j++) {
                Button button = new Button(this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(weekHeight * 2, weekHeight * 4);
                params.leftMargin = j * weekHeight * 2;
                params.topMargin = i * weekHeight * 4;
                button.setLayoutParams(params);
                button.setText(i + " : " + j);
                if (j % 2 == 0) {
                    button.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    button.setBackgroundColor(getResources().getColor(R.color.md_light_blue_A200));
                }
                courseRe.addView(button);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                horizontalScrollView.getScrollX();
                down_x = (int) event.getX();
                down_y = (int) event.getY();
                down_scroX = horizontalScrollView.getScrollX();
                down_scroY = scrollView.getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                now_x = (int) event.getX();
                now_y = (int) event.getY();
                horizontalScrollView.scrollTo(down_x - now_x + down_scroX, 0);
                scrollView.scrollTo(0, down_y - now_y + down_scroY);
                courseHo.scrollTo(down_x - now_x + down_scroX, 0);
                courseSc.scrollTo(0, down_y - now_y + down_scroY);
                break;
        }
        relativeLayout.invalidate();
        return true;
    }
}