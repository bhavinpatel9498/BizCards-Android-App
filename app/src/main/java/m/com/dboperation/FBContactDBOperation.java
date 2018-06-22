package m.com.dboperation;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import m.com.bizcards.MainActivity;
import m.com.bo.ContactBO;
import m.com.common.CommonFunctions;
import m.com.fragment.CardList;
import m.com.fragment.ContactDetails;

/**
 * Created by Bhavin on 24-Apr-18.
 */


public class FBContactDBOperation {

    private ArrayList<ContactBO> contactList = null;

    public void insertContact(ContactBO contBO, String mainKey)
    {
        String url = "/"+FireBaseConfig.userKey+"/"+CommonFunctions.EncodeKeyString(mainKey)+"/"+
                FireBaseConfig.contactKey+"/"+CommonFunctions.EncodeKeyString(contBO.getEmail());
        Firebase alaRef = FireBaseConfig.fbRef.child(url);
        alaRef.setValue(contBO);
        Log.d("bhavin","Insert success");
    }

    public void deleteContact(String mainKey, String contKey)
    {
        String url = "/"+FireBaseConfig.userKey+"/"+CommonFunctions.EncodeKeyString(mainKey)+"/"+
                FireBaseConfig.contactKey+"/"+CommonFunctions.EncodeKeyString(contKey);
        Firebase alaRef = FireBaseConfig.fbRef.child(url);
        alaRef.removeValue();
    }

    public void updateContact()
    {

    }

    public void fetchContact(String mainKey, String contKey, final Context ctx, final View view)
    {
        String url = "/"+FireBaseConfig.userKey+"/"+CommonFunctions.EncodeKeyString(mainKey)+"/"+
                FireBaseConfig.contactKey+"/"+CommonFunctions.EncodeKeyString(contKey);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(url);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    ContactBO contBO = (ContactBO) snapshot.getValue(ContactBO.class);
                    ContactDetails c = new ContactDetails();
                    c.populateContactDet(contBO, view, ctx);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d("bhavin","error in fetch"+firebaseError.getMessage());
            }
        });
   }

    private void populateFetchContact(ContactBO contBo, Context ctx)
    {
        Log.d("bhavin","fetchcontact Method");
        Toast.makeText(ctx, "Save Success"+contBo.getFirstName(), Toast.LENGTH_SHORT).show();
    }

    public static void fetchAllContacts(String mainKey, final Context ctx, final View view)
    {
        String url = "/"+FireBaseConfig.userKey+"/"+CommonFunctions.EncodeKeyString(mainKey)+"/"+
                FireBaseConfig.contactKey;

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(url);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot)
            {

                if (snapshot.exists())
                {
                    ArrayList<ContactBO> contacts = new ArrayList<>();

                    for (DataSnapshot dCont : snapshot.getChildren())
                    {
                        ContactBO c = dCont.getValue(ContactBO.class);
                        contacts.add(c);
                    }


                    CardList cardList = new CardList();
                    cardList.refreshPage(contacts, ctx, view);

                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d("bhavin","error in fetch"+firebaseError.getMessage());
            }
        });

    }

}
