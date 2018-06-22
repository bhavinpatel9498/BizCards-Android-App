package m.com.fragment;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import net.glxn.qrgen.android.QRCode;

import m.com.bizcards.R;
import m.com.bo.ContactBO;
import m.com.bo.UserProfileBO;
import m.com.dboperation.FBContactDBOperation;
import m.com.storage.LocalProfileOperation;

/**
 * Created by Bhavin on 29-Apr-18.
 */

public class ViewQR extends Fragment {

    public ViewQR()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.qrview, container, false);

        try
        {
            LocalProfileOperation lobj = new LocalProfileOperation();
            UserProfileBO upbo = lobj.readData(container.getContext());

            if (upbo != null)
            {
                Bitmap myBitmap = QRCode.from(upbo.toString()).bitmap();

               ImageView myImage = (ImageView) rootView.findViewById(R.id.imageView);
                //myImage.setImageBitmap(myBitmap);
                //myImage.setScaleType(ImageView.ScaleType.FIT_XY);

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = multiFormatWriter.encode(upbo.toString(), BarcodeFormat.QR_CODE,300,300);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmpap = barcodeEncoder.createBitmap(bitMatrix);

                ImageView myImage1 = (ImageView) rootView.findViewById(R.id.imageView);
                myImage.setImageBitmap(bitmpap);

            }
            else
            {
                Toast.makeText(container.getContext(), "User Profile Not Available. Cannot Generate QR Code", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(container.getContext(), "Error Occurred. Please try again.", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

}
