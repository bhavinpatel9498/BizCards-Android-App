package m.com.interfaces;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Bhavin on 29-Apr-18.
 */

public class ServiceCalls extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;


    public void sendSMS(String strPhone, Context ctx)
    {
        Activity activity = (Activity)ctx;
        Uri sms_uri = Uri.parse("smsto:"+strPhone);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", "Message from BizCards App");

        try
        {
            activity.startActivity(sms_intent);
            finish();
        } catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(ctx,"SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }


    public void sendEmail(String strToEmailID, Context ctx)
    {
        /* Create the Intent */
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);

        Activity activity = (Activity)ctx;

        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{strToEmailID});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "What's up?");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello,\nJust wanted to say hello.");

        /* Send it off to the Activity-Chooser */
        activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

    }

    public void browserCall(String strURL, Context ctx)
    {
        Activity activity = (Activity)ctx;
        Intent bIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(strURL));
        activity.startActivity(bIntent);
    }

    private boolean checkPermission(String permission, Activity act)
    {
        return ContextCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                   // Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


    public void callNumber(String strPhone, Context ctx)
    {
        Activity activity = (Activity)ctx;
        if (!TextUtils.isEmpty(strPhone))
        {
            if (checkPermission(Manifest.permission.CALL_PHONE, activity))
            {
                String dial = "tel:" + strPhone;
                activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
            else
            {
                //Toast.makeText(MainActivity.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            //Toast.makeText(MainActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
        }

        if (checkPermission(Manifest.permission.CALL_PHONE, activity))
        {

        }
        else
        {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }


    }


}
