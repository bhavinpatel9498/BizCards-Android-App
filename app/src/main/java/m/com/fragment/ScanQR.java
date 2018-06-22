package m.com.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;

import m.com.bo.UserProfileBO;
import m.com.storage.LocalProfileOperation;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import m.com.bo.UserProfileBO;
import m.com.common.CommonFunctions;
import m.com.storage.LocalProfileOperation;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Bhavin on 29-Apr-18.
 */

public class ScanQR extends Fragment implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private Context cntx;

    public ScanQR() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cntx = container.getContext();

        UserProfileBO upboTemp = LocalProfileOperation.readData(cntx);

        if(upboTemp!=null)
        {
            try {
                mScannerView = new ZXingScannerView(container.getContext());

                //setContentView(mScannerView);
                int currentapiVersion = Build.VERSION.SDK_INT;
                if (currentapiVersion >= Build.VERSION_CODES.M) {
                    if (checkPermission()) {
                        //Toast.makeText(container.getContext(), "Permission already granted", Toast.LENGTH_LONG).show();

                    } else {
                        requestPermission();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(container.getContext(), "Error Occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(container.getContext(), "User Profile Not Available. Please create a profile.", Toast.LENGTH_SHORT).show();
        }
        return mScannerView;
    }

    private boolean checkPermission() {
        //Log.d("bhavin", "Permission: " + PackageManager.PERMISSION_GRANTED);
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        //Log.d("bhavin", "requestPermission");
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        //Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Context.CAMERA_SERVICE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Context.CAMERA_SERVICE},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }

    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(getContext());
                    //setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mScannerView.stopCamera();
        //mScannerView.stopCameraPreview();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        final String result = rawResult.getText();


        //UserProfileBO upbo1 = LocalProfileOperation.readData(getApplicationContext());
        //String strMainKey = upbo1.getEmail();
        //Toast.makeText(getApplicationContext(), "strMainKey :"+strMainKey, Toast.LENGTH_SHORT).show();
        //CommonFunctions.extractQRData(rawResult, strMainKey);
        //Toast.makeText(getContext(), "Save Success :", Toast.LENGTH_SHORT).show();

//        Log.d("QRCodeScanner", rawResult.getText());
  //      Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());
        LocalProfileOperation lobj = new LocalProfileOperation();
        UserProfileBO upbo1 = lobj.readData(getContext());
        String strMainKey = upbo1.getEmail();
        //Toast.makeText(getContext(), "strMainKey :"+strMainKey, Toast.LENGTH_SHORT).show();
        String strErr = CommonFunctions.extractQRData(rawResult.getText(), strMainKey);

        if(!"error".equalsIgnoreCase(strErr))
        {
            Toast.makeText(getContext(), "Contact Saved Successfully.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "Error occurred while Save Operation!", Toast.LENGTH_SHORT).show();
        }

        mScannerView = new ZXingScannerView(cntx);

    }


}
