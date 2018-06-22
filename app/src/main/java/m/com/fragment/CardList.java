package m.com.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import m.com.bizcards.MainActivity;
import m.com.bizcards.R;
import m.com.bo.ContactBO;
import m.com.bo.UserProfileBO;
import m.com.common.CommonFunctions;
import m.com.dboperation.FBContactDBOperation;
import m.com.dboperation.FireBaseConfig;
import m.com.storage.LocalProfileOperation;

public class CardList extends Fragment {

    private ArrayList<ContactBO> contactList = new ArrayList<ContactBO>();
    private static Context contx11;

    public CardList() {
        // Required empty public constructor
    }

    public void refreshPage(ArrayList<ContactBO> ctboList, Context ctx, View view)
    {
        ContactBO contBO = new ContactBO();
        contBO.setFirstName("Bhavin");
        contBO.setLastName("Desai");
        contBO.setEmail("rdesai30@hawk.iit.edu");
        contBO.setCompany("Illinois Tech");

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        contactList = ctboList;

        MyAdapter adapter = new MyAdapter(contactList, ctx);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        /* Fetching contact List from DB */

        UserProfileBO upboTemp = LocalProfileOperation.readData(container.getContext());

        contx11 = container.getContext();
        /* Temporary Code to save Contacts */

       /*

        ContactBO contBO = new ContactBO();
        contBO.setFirstName("F Name");
        contBO.setLastName("L NAME");
        contBO.setEmail("rdesai30@hawk.iit.edu");
        contBO.setCompany("Illinois Tech");
        contBO.setPhone("3124091731");
        contBO.setNotes("This is My Profile");
        contBO.setWebsite("https://www.google.com/");
        contBO.setDeskphone("3124091731");


        ContactBO contBO1 = new ContactBO();
        contBO1.setFirstName("First11 nam22e");
        contBO1.setLastName("last 2n22ame");
        contBO1.setEmail("firsdsadt@ac.com");
        contBO1.setCompany("ABCL LTD");

        ContactBO contBO2 = new ContactBO();
        contBO2.setFirstName("33First name");
        contBO2.setLastName("la43534st name");
        contBO2.setEmail("dsadt@ac.com");
        contBO2.setCompany("asdasd LTD");

        ContactBO contBO3 = new ContactBO();
        contBO3.setFirstName("First name");
        contBO3.setLastName("last name");
        contBO3.setEmail("fidsa@sado.com");
        contBO3.setCompany("ABCL LTD");

        if(upboTemp!=null) {
            FBContactDBOperation obj = new FBContactDBOperation();
            obj.insertContact(contBO, upboTemp.getEmail());
            obj.insertContact(contBO1, upboTemp.getEmail());
            obj.insertContact(contBO2, upboTemp.getEmail());
            obj.insertContact(contBO3, upboTemp.getEmail());

        }


        */

        if(upboTemp!=null)
        {
            FBContactDBOperation.fetchAllContacts(upboTemp.getEmail(), container.getContext(), rootView);
            MyAdapter adapter = new MyAdapter(contactList, container.getContext());


            rv.setAdapter(adapter);

        }
        else
        {
            Toast.makeText(container.getContext(), "User Profile Not Available. Please create a profile.", Toast.LENGTH_SHORT).show();
        }
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    public void callContactScreen(String strEmail, Context ctx1)
    {
        Activity activity = (Activity)ctx1;

        Intent intent = new Intent(ctx1, MainActivity.class);

        Bundle dataBundle = new Bundle();
        dataBundle.putString("contactFragment", "true");
        dataBundle.putString("emaild", strEmail);
        intent.putExtras(dataBundle);
        activity.startActivity(intent);

    }


}