package pushnotification;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

//import com.codeversed.example.Notifications.MainActivity;
//import pushnotification.R;
import com.sitepoint.example02.MainActivity;
import com.sitepoint.example02.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

//import notification.CreateNotification;
//import java.util.Locale;

public class Demm {

    private final static String sample_url = "http://youngturks.in.com/media/50235_quikr-070415044441.jpg";
    Context n;
    private final static int BIG_TEXT_STYLE = 0x01;
    private final static int NORMAL = 0x00;
    NotificationManager mNotificationManager;
    private double slat, slon, dlat, dlon;
    private CharSequence title, MAIN_TEXT, SMALL_TEXT = "House Shorlisted Nearby";

    public Demm(MainActivity c) {
        this.n = c;
        mNotificationManager = (NotificationManager) n.getSystemService(Context.NOTIFICATION_SERVICE);
        ;

    }

    public void runn(double slat, double slon, double dlat, double dlon, CharSequence title, CharSequence maintext) {
        this.slat = slat;
        this.slon = slon;
        this.dlat = dlat;
        this.dlon = dlon;
        this.MAIN_TEXT = maintext;
        this.title = title;
        new CreateNotification().execute();
    }

    public class CreateNotification extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Notification noti = new Notification();
            Notification notii = new Notification();

            noti = setBigTextStyleNotification();
            notii = setBigTextStyleNotification();
            notii.defaults |= Notification.DEFAULT_LIGHTS;
            notii.defaults |= Notification.DEFAULT_VIBRATE;
            notii.defaults |= Notification.DEFAULT_SOUND;
            notii.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

            noti.defaults |= Notification.DEFAULT_LIGHTS;
            noti.defaults |= Notification.DEFAULT_VIBRATE;
            noti.defaults |= Notification.DEFAULT_SOUND;

            noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

            //mNotificationManager.notify(1, noti);
            mNotificationManager.notify(2, notii);

            return null;

        }
    }

    private Notification setBigTextStyleNotification() {
        Bitmap remote_picture = null;

        // Create the style object with BigTextStyle subclass.
        NotificationCompat.BigTextStyle notiStyle = new NotificationCompat.BigTextStyle();
        notiStyle.setBigContentTitle(MAIN_TEXT);
        notiStyle.setSummaryText(SMALL_TEXT);

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(sample_url).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the big text to the style.
        CharSequence bigText = title;
        notiStyle.bigText(bigText);

        // Creates an explicit intent for an ResultActivity to receive.
        //   Intent resultIntent = new Intent(this, ResultActivity.class);

        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(n);

        // Adds the back stack for the Intent (but not the Intent itself).
        // stackBuilder.addParentStack(ResultActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack.

        Intent rintent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + slat + "," + slon + "&daddr=" + dlat + "," + dlon));
        stackBuilder.addNextIntent(rintent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(n)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
//                .addAction(R.drawable.ic_launcher, "One", resultPendingIntent)
//                .addAction(R.drawable.ic_launcher, "Two", resultPendingIntent)
//                .addAction(R.drawable.ic_launcher, "Three", resultPendingIntent)
                .setContentTitle(MAIN_TEXT)
                .setContentText(SMALL_TEXT)
                .setStyle(notiStyle).build();
    }


}
