package com.portpolio.FireStock.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    public static String makeTodayDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(date);
        return now;
    }

}
