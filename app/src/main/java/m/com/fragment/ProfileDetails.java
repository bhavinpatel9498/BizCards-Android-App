package m.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import m.com.bizcards.MainActivity;
import m.com.bizcards.R;
import m.com.bo.UserProfileBO;
import m.com.common.CommonFunctions;
import m.com.dboperation.FBUserProfileOperation;
import m.com.storage.LocalProfileOperation;

/**
 * Created by Bhavin on 01-May-18.
 */

public class ProfileDetails extends Fragment {

        private AwesomeValidation awesomeValidation;
        private EditText editTextName, editTextEmail, editTextMobile,
            editTextTel, editTextWeb, editTextlastName,
            editTextJob,editTextCity,editTextCompany,editTextPin,editTextNote;

        public ProfileDetails() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.profile_details, container, false);

            Button updatebtn = rootView.findViewById(R.id.updatebtnid);


            editTextName = (EditText) rootView.findViewById(R.id.firstName);
            editTextlastName = (EditText) rootView.findViewById(R.id.lastName);
            editTextMobile = (EditText) rootView.findViewById(R.id.phone);
            editTextTel = (EditText) rootView.findViewById(R.id.landline);
            editTextEmail = (EditText) rootView.findViewById(R.id.email);
            editTextCompany = (EditText) rootView.findViewById(R.id.company);
            editTextJob = (EditText) rootView.findViewById(R.id.job);
            editTextCity = (EditText) rootView.findViewById(R.id.city);
            editTextWeb = (EditText) rootView.findViewById(R.id.website);
            editTextPin = (EditText) rootView.findViewById(R.id.Pin);
            editTextNote = (EditText) rootView.findViewById(R.id.Note);

            final Context ctx = container.getContext();


            UserProfileBO upboTemp = LocalProfileOperation.readData(container.getContext());

            if(upboTemp!=null)
            {
                editTextName.setText(upboTemp.getFirstName());
                editTextEmail.setText(upboTemp.getEmail());
                editTextMobile.setText(upboTemp.getPhone());
                editTextTel.setText(upboTemp.getDeskphone());
                editTextWeb.setText(upboTemp.getWebsite());
                editTextlastName.setText(upboTemp.getLastName());
                editTextJob.setText(upboTemp.getTitle());
                editTextCity.setText(upboTemp.getAddress());
                editTextCompany.setText(upboTemp.getCompany());
                editTextPin.setText(upboTemp.getPin());
                editTextNote.setText(upboTemp.getNotes());
                editTextEmail.setFocusable(false);
            }
            else
            {
                editTextEmail.setFocusable(true);
                editTextEmail.setClickable(false);
                editTextName.setText("Bhavin PATEL");
                editTextEmail.setText("bpatel68@hawk.iit.edu");
                editTextMobile.setText("3124091731");
                editTextTel.setText("3124091731");
                editTextWeb.setText("https://www.linkedin.com/in/bhavinkumarpatel/");
                editTextlastName.setText("Patel");
                editTextJob.setText("Student");
                editTextCity.setText("Chicago");
                editTextCompany.setText("IIT");
                editTextPin.setText("12345");
                editTextNote.setText("This is My Profile");
            }


            /* Update Button listener */

            updatebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


                    awesomeValidation.addValidation((Activity) ctx, R.id.firstName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.firstnameerror);
                    awesomeValidation.addValidation((Activity) ctx, R.id.lastName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lastnameerror);
                    awesomeValidation.addValidation((Activity) ctx, R.id.phone, "^[2-9]{1}[0-9]{9}$", R.string.mobileerror);
                    //awesomeValidation.addValidation(this, R.id.landline, "^[0-9][1-9]{9}$", R.string.telmobileerror);
                    awesomeValidation.addValidation((Activity) ctx, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);

                    awesomeValidation.addValidation((Activity) ctx, R.id.company, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.companyerror);
                    awesomeValidation.addValidation((Activity) ctx, R.id.job, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.joberror);
                    awesomeValidation.addValidation((Activity) ctx, R.id.city, "[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.cityerror);

                    //awesomeValidation.addValidation(this, R.id.editTextPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);

                    //awesomeValidation.addValidation(this, R.id.editTextTel, "/^\\(0\\d{1,2}\\)\\d{3}-\\d{4}$/", R.string.telmobileerror);
                    awesomeValidation.addValidation((Activity) ctx, R.id.website, "((http|https)://)?[a-zA-Z]\\w*(\\.\\w+)+(/\\w*(\\.\\w+)*)*(\\?.+)*", R.string.weberror);

                    // awesomeValidation.addValidation(this, R.id.editTextWeb, "(^(http[s]?://)?([w]{3}[.])?([a-z0-9]+[.])+com(((/[a-z0-9]+)*(/[a-z0-9]+/))*([a-z0-9]+[.](html|php|gif|png))?)$)|(^([.]/)?((([a-z0-9]+)/?)+|(([a-z0-9]+)/)+([a-z0-9]+[.](html|php|gif|png)))?$)", R.string.weberror);

                    awesomeValidation.addValidation((Activity) ctx, R.id.Pin, "^\\d{5}(?:[-\\s]\\d{4})?$", R.string.pinerror);
                    awesomeValidation.addValidation((Activity) ctx, R.id.Note, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.noteerror);


                    if (awesomeValidation.validate()) {

                        // buttonSubmit = (Button) view.findViewById(R.id.updatebtnid);
                        try {

                            UserProfileBO upboTemp = LocalProfileOperation.readData(ctx);

                            UserProfileBO upbo = new UserProfileBO();

                            //Write your code below this line to populate data with business object user upbo Object to populate data


                            upbo.setFirstName(editTextName.getText().toString());
                            upbo.setLastName(editTextlastName.getText().toString());

                            upbo.setPhone(editTextMobile.getText().toString());
                            upbo.setDeskphone(editTextTel.getText().toString());
                            upbo.setEmail(editTextEmail.getText().toString());
                            upbo.setCompany(editTextCompany.getText().toString());
                            upbo.setTitle(editTextJob.getText().toString());
                            upbo.setAddress(editTextCity.getText().toString());
                            upbo.setWebsite(editTextWeb.getText().toString());
                            //upbo.set

                            upbo.setWebsite(editTextWeb.getText().toString());
                            upbo.setPin(editTextPin.getText().toString());
                            upbo.setNotes(editTextNote.getText().toString());


                            //Write your code above this line and populate userprofileBO with UI values
                            LocalProfileOperation.saveData(upbo, ctx);
                            upbo = LocalProfileOperation.readData(ctx);

                            Log.d("bhavin", "EMAIL :" + upbo.getEmail());

                            Log.d("First Name: ", upbo.getFirstName());
                            Log.d("bhavin","LandLine :"+upbo.getDeskphone());

                            boolean update = true;

                            if (upboTemp == null) {
                                update = false;
                            }

                            Log.d("submitForm: ", update + "value");

                            if (update) {
                                FBUserProfileOperation.updateUser(upbo);
                            } else {
                                FBUserProfileOperation.insertUser(upbo);
                            }

                            Toast.makeText(ctx, "Profile Updated for user " + upbo.getEmail(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ctx, MainActivity.class);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();

                            Toast.makeText(ctx, "Error Occurred while update profile operation. Please try again."+e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });

            /* Delete Button listener */

            Button deletebtn = rootView.findViewById(R.id.deletebtnid);

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    LocalProfileOperation lobj = new LocalProfileOperation();
                    UserProfileBO upbo = lobj.readData(ctx);

                    if(upbo!=null)
                    {
                        FBUserProfileOperation.deleteUser(CommonFunctions.EncodeKeyString(upbo.getEmail()));
                        LocalProfileOperation.deleteData(ctx);
                        Toast.makeText(ctx, "Profile Deleted for the user.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(ctx, "Profile does not exist for user.", Toast.LENGTH_SHORT).show();
                    }

                    editTextName.setText("");
                    editTextEmail.setText("");
                    editTextMobile.setText("");
                    editTextTel.setText("");
                    editTextWeb.setText("");
                    editTextlastName.setText("");
                    editTextJob.setText("");
                    editTextCity.setText("");
                    editTextCompany.setText("");
                    editTextPin.setText("");
                    editTextNote.setText("");

                    Intent intent = new Intent(ctx, MainActivity.class);
                    startActivity(intent);

                    upbo = null;
                }
            });



            return rootView;
        }



}
