package m.com.dboperation;

import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import m.com.bizcards.MainActivity;
import m.com.bo.ContactBO;
import m.com.bo.UserProfileBO;
import m.com.common.CommonFunctions;

/**
 * Created by Bhavin on 24-Apr-18.
 */

public class FBUserProfileOperation
{

    public static void insertUser(UserProfileBO upBO)
    {
        String url = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail());
        Firebase alaRef = FireBaseConfig.fbRef.child(url);
        alaRef.setValue(upBO);
        Log.d("bhavin","Insert success");

    }

    public static void deleteUser(String mainKey)
    {
        String url = "/"+FireBaseConfig.userKey+"/"+CommonFunctions.EncodeKeyString(mainKey);
        Firebase alaRef = FireBaseConfig.fbRef.child(url);
        alaRef.removeValue();

    }

    public static void updateUser(UserProfileBO upBO)
    {
        //firstname
        String url = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/firstName";
        Firebase alaRef = FireBaseConfig.fbRef.child(url);
        alaRef.setValue(upBO.getFirstName());

        //lastName
        String url1 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/lastName";
        Firebase alaRef1 = FireBaseConfig.fbRef.child(url1);
        alaRef1.setValue(upBO.getLastName());

        //email
        String url2 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/email";
        Firebase alaRef2 = FireBaseConfig.fbRef.child(url2);
        alaRef2.setValue(upBO.getEmail());

        //phone
        String url3 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/phone";
        Firebase alaRef3 = FireBaseConfig.fbRef.child(url3);
        alaRef3.setValue(upBO.getPhone());

        //company
        String url4 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/company";
        Firebase alaRef4 = FireBaseConfig.fbRef.child(url4);
        alaRef4.setValue(upBO.getCompany());

        //deskphone
        String url5 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/deskphone";
        Firebase alaRef5 = FireBaseConfig.fbRef.child(url5);
        alaRef5.setValue(upBO.getDeskphone());

        //title
        String url6 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/title";
        Firebase alaRef6 = FireBaseConfig.fbRef.child(url6);
        alaRef6.setValue(upBO.getTitle());

        //address
        String url7 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/address";
        Firebase alaRef7 = FireBaseConfig.fbRef.child(url7);
        alaRef7.setValue(upBO.getAddress());

        //website
        String url8 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/website";
        Firebase alaRef8 = FireBaseConfig.fbRef.child(url8);
        alaRef8.setValue(upBO.getWebsite());

        //notes
        String url9 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/notes";
        Firebase alaRef9 = FireBaseConfig.fbRef.child(url9);
        alaRef9.setValue(upBO.getNotes());

        //pin
        String url10 = "/"+FireBaseConfig.userKey+"/"+ CommonFunctions.EncodeKeyString(upBO.getEmail())+"/pin";
        Firebase alaRef10 = FireBaseConfig.fbRef.child(url10);
        alaRef10.setValue(upBO.getPin());
    }

    public static void fetchUser(String mainKey, final Context ctx)
    {
        String url = "/"+FireBaseConfig.userKey+"/"+CommonFunctions.EncodeKeyString(mainKey);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(url);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    UserProfileBO upBO = (UserProfileBO) snapshot.getValue(UserProfileBO.class);
                    Log.d("bhavin","USER PROFILE FETCHED");
                    //populateFetchContact(contBO, ctx);

                    MainActivity m = new MainActivity();
                    m.toastMsg(upBO,ctx);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d("bhavin","error in fetch"+firebaseError.getMessage());
            }
        });
    }
}
