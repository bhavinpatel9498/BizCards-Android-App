package m.com.dboperation;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Bhavin on 24-Apr-18.
 */

public class FireBaseConfig {

    public static Firebase fbRef = new Firebase("https://qrcodegeneratordemo.firebaseio.com/");
    public static String strFBURL = "https://qrcodegeneratordemo.firebaseio.com/";

    public DatabaseReference fbDBRef = FirebaseDatabase.getInstance().getReference();

    public static String userKey = "userprofiles";
    public static String contactKey = "contacts";

}
