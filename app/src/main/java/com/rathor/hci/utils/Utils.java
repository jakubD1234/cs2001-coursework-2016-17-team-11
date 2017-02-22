package com.rathor.hci.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static boolean isTablet(Activity activity) {
        return (activity.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static Bitmap decodeFile(File f, int w) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = w;
            System.out.println("screen wdth " + 200);
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            System.out.println("image with === " + width_tmp);
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static  String chnageTime12Format(String startTime) {
        String[] parts = startTime.split(" ");
        String[] dateParts = parts[0].split("-");

        DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss");

        String formatedDateTime = null;
        try {
            Date dt = parseFormat.parse(parts[1]);
            formatedDateTime = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0] + " " + outputFormat.format(dt);
        } catch (ParseException exc) {
            exc.printStackTrace();
        }

        return formatedDateTime;
    }

    public static String getTimeDifference(Date date, String timeFormat, String timeZone) {
        int diffInDays = 0;
        String timex = null;

        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        format.setTimeZone(TimeZone.getTimeZone(timeZone));

        Calendar c = Calendar.getInstance();
        String formattedDate = format.format(c.getTime());

        Date d1 = c.getTime();
        Date d2 = null;
        try {

            d1 = format.parse(formattedDate);

            d2 = format.parse(format.format(date));

            long diff = d1.getTime() - d2.getTime();
            diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
            if (diffInDays > 0) {
                if (diffInDays == 1) {
                    timex = diffInDays + "d ago";
                } else {
                    timex = diffInDays + "d ago";
                }
            } else {
                int diffHours = (int) (diff / (60 * 60 * 1000));
                if (diffHours > 0) {
                    if (diffHours == 1) {
                        timex = diffHours + "h ago";
                    } else {
                        timex = diffHours + "h ago";
                    }
                } else {
                    int diffMinutes = (int) ((diff / (60 * 1000) % 60));
                    if (diffMinutes > 0) {
                        if (diffMinutes == 1) {
                            timex = diffMinutes + "m ago";
                        } else {
                            timex = diffMinutes + "m ago";
                        }
                    } else {
                        int diffSeconds = (int) ((diff / (1000) % 60));
                        if (diffSeconds == 1) {
                            timex = diffSeconds + "s ago";
                        } else {
                            timex = diffSeconds + "s ago";
                        }
                    }

                }
            }

        } catch (Exception e) {
            System.out.println("Err: " + e);
            e.printStackTrace();
        }
        return timex;
    }



}