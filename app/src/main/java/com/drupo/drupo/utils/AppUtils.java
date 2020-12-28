package com.drupo.drupo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.drupo.drupo.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class AppUtils {

    public static final int FADE_DURATION = 1000 ;


    public static boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPasswordLength(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            if (target.length() < 6 || target.length() > 15) {
                return false;
            } else {
                return true;
            }
        }

    }

    public static void closeKeyboard(AppCompatActivity appCompatActivity, View view) {
        InputMethodManager imm = (InputMethodManager) appCompatActivity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public static String convertTo12Hour(String time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            String convertedTime = new SimpleDateFormat("hh:mm a").format(dateObj);
            return convertedTime;
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertTo24Hour(String Time) {
        DateFormat f1 = new SimpleDateFormat("h:mm aaa"); //11:00 pm
        Date d = null;
        try {
            d = f1.parse(Time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("HH");
        String x = f2.format(d); // "23:00"

        return x;
    }

    public static String convertTo24Hour_min(String Time) {
        DateFormat f1 = new SimpleDateFormat("h:mm aaa"); //11:00 pm
        Date d = null;
        try {
            d = f1.parse(Time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("mm");
        String x = f2.format(d); // "23:00"

        return x;
    }

    public static void hideKeyboard(Context context) {
        try {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if ((((Activity) context).getCurrentFocus() != null) && (((Activity) context).getCurrentFocus().getWindowToken() != null)) {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void setScaleAnimation(View view){
        ScaleAnimation anim = new ScaleAnimation(0.6f, 1.0f, 0.6f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(Long.valueOf(FADE_DURATION));
        view.startAnimation(anim);
    }

    public static void customToastGreenBottom(String title,Context context){

        Toast toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        View toastView = toast.getView(); // This'll return the default View of the Toast.
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(18);
        toastMessage.setTextColor(ContextCompat.getColor(context, R.color.toast_txtColour_green));
        toastView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green));
        toast.show();
    }

}
