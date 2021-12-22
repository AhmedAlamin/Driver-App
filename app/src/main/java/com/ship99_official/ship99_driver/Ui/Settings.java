package com.ship99_official.ship99_driver.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ship99_official.ship99_driver.Pojo.InfoModel;
import com.ship99_official.ship99_driver.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class Settings extends AppCompatActivity {

    RelativeLayout signout;
    TextView name,address,number;
    FirebaseAuth mAuth;
    FloatingActionButton change_im;
    CircleImageView imageView;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static int RESULT_LOAD_IMG = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);



        mAuth = FirebaseAuth.getInstance();

        change_im = (FloatingActionButton) findViewById(R.id.change_im) ;
        
        imageView = (CircleImageView) findViewById(R.id.ivBackground);
        signout = (RelativeLayout) findViewById(R.id.logout);
        name = (TextView) findViewById(R.id.nameOfClient);
        address = (TextView) findViewById(R.id.defaultAddress);
        number = (TextView) findViewById(R.id.NumberOfClient);

        change_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Settings.this,LoginPickNameAddress.class);
                i.putExtra("Settings","1");
                startActivity(i);

            }
        });
        
       DatabaseReference database = FirebaseDatabase.getInstance().getReference("Drivers/"+mAuth.getCurrentUser().getUid());


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                InfoModel infoModel = new InfoModel();
                infoModel = snapshot.getValue(InfoModel.class);
                name.setText(infoModel.getName());
                number.setText(infoModel.getPhone());
                address.setText(infoModel.getzoneLocation());
                Picasso.with(imageView.getContext()).load(infoModel.getPhoto()).fit().centerCrop()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading)
                        .into(imageView);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Settings.this,"Signed out",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Settings.this,login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });


    }



}
