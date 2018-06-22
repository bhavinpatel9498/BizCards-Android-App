package m.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import m.com.bizcards.MainActivity;
import m.com.bizcards.R;
import m.com.bo.ContactBO;
import m.com.bo.UserProfileBO;
import m.com.common.CommonFunctions;
import m.com.dboperation.FBContactDBOperation;
import m.com.dboperation.FBUserProfileOperation;
import m.com.interfaces.ServiceCalls;
import m.com.storage.LocalProfileOperation;

/**
 * Created by Bhavin on 02-May-18.
 */

public class ContactDetails extends Fragment {

    private String sEmailid;
    private EditText editTextName, editTextEmail, editTextMobile,
            editTextTel, editTextWeb, editTextlastName,
            editTextJob,editTextCity,editTextCompany,editTextNote;


    private String strMobileNo = "";
    private String strEmailID = "";
    private String strURL = "";
    private String strlandline = "";

    public ContactDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle dataBundle =  getArguments();

        if(dataBundle!=null)
        {
            sEmailid = dataBundle.getString("emailid");
        }


    }

    public void populateContactDet(ContactBO contBO, View view, Context ctx)
    {
        editTextName = (EditText) view.findViewById(R.id.firstName);
        editTextlastName = (EditText) view.findViewById(R.id.lastName);
        editTextMobile = (EditText) view.findViewById(R.id.phone);
        editTextTel = (EditText) view.findViewById(R.id.landline);
        editTextEmail = (EditText) view.findViewById(R.id.email);
        editTextCompany = (EditText) view.findViewById(R.id.company);
        editTextJob = (EditText) view.findViewById(R.id.job);
        editTextCity = (EditText) view.findViewById(R.id.city);
        editTextWeb = (EditText) view.findViewById(R.id.website);
        editTextNote = (EditText) view.findViewById(R.id.Note);

        editTextName.setText(contBO.getFirstName());
        editTextEmail.setText(contBO.getEmail());
        editTextMobile.setText(contBO.getPhone());
        editTextTel.setText(contBO.getDeskphone());
        editTextWeb.setText(contBO.getWebsite());
        editTextlastName.setText(contBO.getLastName());
        editTextJob.setText(contBO.getTitle());
        editTextCity.setText(contBO.getAddress());
        editTextCompany.setText(contBO.getCompany());
        editTextNote.setText(contBO.getNotes());


        strMobileNo = contBO.getPhone();
        strEmailID = contBO.getEmail();
        strURL = contBO.getWebsite();
        strlandline = contBO.getDeskphone();

        // editTextEmail.setFocusable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.card_view, container, false);

        final Context ctx=container.getContext();

        editTextName = (EditText) rootView.findViewById(R.id.firstName);
        editTextlastName = (EditText) rootView.findViewById(R.id.lastName);
        editTextMobile = (EditText) rootView.findViewById(R.id.phone);
        editTextTel = (EditText) rootView.findViewById(R.id.landline);
        editTextEmail = (EditText) rootView.findViewById(R.id.email);
        editTextCompany = (EditText) rootView.findViewById(R.id.company);
        editTextJob = (EditText) rootView.findViewById(R.id.job);
        editTextCity = (EditText) rootView.findViewById(R.id.city);
        editTextWeb = (EditText) rootView.findViewById(R.id.website);
        editTextNote = (EditText) rootView.findViewById(R.id.Note);


        LocalProfileOperation lobj = new LocalProfileOperation();
        UserProfileBO upbo1 = lobj.readData(getContext());
        if(upbo1!=null) {
            String strMainKey = upbo1.getEmail();

            FBContactDBOperation obj = new FBContactDBOperation();
            obj.fetchContact(strMainKey, sEmailid, container.getContext(), rootView);
        }

        final ServiceCalls sObj = new ServiceCalls();



        Button smsbtn = (Button)rootView.findViewById(R.id.smsmobile);

        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText phone = rootView.findViewById(R.id.phone);
                String ph = phone.getText().toString();


                if("".equalsIgnoreCase(ph) || ph == null || ph == "" )
                {
                    Toast.makeText(view.getContext(), "Cannot Send SMS."+ph, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sObj.sendSMS(ph, view.getContext());
                }
            }
        });


        Button emailBtn = rootView.findViewById(R.id.emailbtn);

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText email = rootView.findViewById(R.id.email);
                String email1 = email.getText().toString();


                if("".equalsIgnoreCase(email1) || email1 == null || email1 == "" )
                {
                    Toast.makeText(view.getContext(), "Cannot Send Email."+email1, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sObj.sendEmail(email1, view.getContext());
                }
            }
        });



        Button urlclickbtn = rootView.findViewById(R.id.urlclick);

        urlclickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                EditText website = rootView.findViewById(R.id.website);
                String web = website.getText().toString();

                if("".equalsIgnoreCase(web) || web == null || web == "" )
                {
                    Toast.makeText(view.getContext(), "Cannot Open URL."+web, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sObj.browserCall(web, view.getContext());
                }
            }
        });


        Button callmobilebtn = rootView.findViewById(R.id.callmobile);

        callmobilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                EditText phone = rootView.findViewById(R.id.phone);
                String ph = phone.getText().toString();

                if("".equalsIgnoreCase(ph) || ph == null || ph == "" )
                {
                    Toast.makeText(view.getContext(), "Cannot Call Mobile."+ph, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sObj.callNumber(ph, view.getContext());
                }
            }
        });


        Button landlinecallbtn = rootView.findViewById(R.id.landlinecall);

        landlinecallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                EditText phone = rootView.findViewById(R.id.landline);
                String ph = phone.getText().toString();

                if("".equalsIgnoreCase(ph) || ph == null || ph == "" )
                {
                    Toast.makeText(view.getContext(), "Cannot Call Desk Phone."+ph, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sObj.callNumber(ph, view.getContext());
                }
            }
        });


        Button deletebtn = (Button)rootView.findViewById(R.id.deletebtnid);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText email = rootView.findViewById(R.id.email);
                String email1 = email.getText().toString();

                if("".equalsIgnoreCase(email1) || email1 == null || email1 == "" )
                {
                    //Toast.makeText(view.getContext(), "Cannot Send SMS."+email1, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LocalProfileOperation lobj = new LocalProfileOperation();
                    UserProfileBO upbo1 = lobj.readData(getContext());
                    String strMainKey = upbo1.getEmail();

                    FBContactDBOperation obj = new FBContactDBOperation();
                    obj.deleteContact(strMainKey,email1);

                    Toast.makeText(view.getContext(), "Contact Deleted Successfully.", Toast.LENGTH_SHORT).show();

                    Activity activity = (Activity)view.getContext();

                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    activity.startActivity(intent);
                }
            }
        });

        return rootView;
    }
}
