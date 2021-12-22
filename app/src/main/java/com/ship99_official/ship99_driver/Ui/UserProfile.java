
package com.ship99_official.ship99_driver.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.ship99_official.ship99_driver.R;

public class UserProfile extends Fragment {

    Button LogOUT ;
    ImageView qrCodeImage;
    FirebaseAuth mAth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.activity_user_profile, container, false);

        mAth = FirebaseAuth.getInstance();

        LogOUT = (Button)v.findViewById(R.id.button1);

        qrCodeImage = (ImageView) v.findViewById(R.id.imageQrCode);


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(mAth.getCurrentUser().getUid().toString(), BarcodeFormat.QR_CODE,350,300);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

        qrCodeImage.setImageBitmap(bitmap);

        // Adding click listener to Log Out button.

        LogOUT.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {


                //Finishing current DashBoard activity on button click.

                Toast.makeText(getContext(),"Log Out Successfull", Toast.LENGTH_SHORT).show();
                //Intent intent=new Intent(DashboardActivity.this,login.class);
                //startActivity(intent);
               if (v.getId() == R.id.button1) {
                    AuthUI.getInstance()
                            .signOut(getContext())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete( Task<Void> task) {
                                    // user is now signed out
                                    startActivity(new Intent(getContext(), login.class));
                                    getActivity().finish();
                                }
                            });
                }

            }
        });


        return v;
    }
}