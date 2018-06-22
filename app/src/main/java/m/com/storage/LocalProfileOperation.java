package m.com.storage;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import m.com.bo.UserProfileBO;

/**
 * Created by Bhavin on 25-Apr-18.
 */

public class LocalProfileOperation extends Activity {

    public static String saveData(UserProfileBO upBO, Context ctx)
    {
        ObjectOutputStream o = null;
        FileOutputStream f = null;
        String strResponse = "error";
        try
        {
            File file = new File(ctx.getFilesDir(), "userprofile.ser");
            f = ctx.openFileOutput("userprofile.ser", Context.MODE_PRIVATE);
            o = new ObjectOutputStream(f);
            o.writeObject(upBO);
            strResponse = "success";
        }
        catch (Exception e)
        {
            strResponse = "error";
        }
        finally
        {
            try
            {
                if (o != null)
                    o.close();
                if (f != null)
                    f.close();
            }
            catch (Exception e)
            {
            }
        }

        return strResponse;
    }

    public static UserProfileBO readData(Context ctx)
    {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        UserProfileBO upBO = null;

        try
        {
            fi = ctx.openFileInput("userprofile.ser");
            oi = new ObjectInputStream(fi);
            upBO = (UserProfileBO) oi.readObject();
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            try
            {
                if (oi != null)
                    oi.close();
                if (fi != null)
                    fi.close();
            }
            catch (Exception e)
            {
            }
        }

        return  upBO;
    }

    public static void deleteData(Context ctx)
    {
        try
        {
            File file = new File(ctx.getFilesDir(), "userprofile.ser");
            file.delete();
        }
        catch (Exception e)
        {
            //Do nothing
        }

    }


}
