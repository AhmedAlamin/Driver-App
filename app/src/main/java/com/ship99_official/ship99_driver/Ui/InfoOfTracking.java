package com.ship99_official.ship99_driver.Ui;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ship99_official.ship99_driver.Pojo.RequestModel;
import com.ship99_official.ship99_driver.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class InfoOfTracking extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 2;
    private Map<String, TextView> textFieldsOfShipment;
    private ImageView productImage;
    private LinearLayout cardSingleItem;
    private FloatingActionButton callCustomer;
    private Button acceptbtn,rejectbtn;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String statusSelected="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_track);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Requests");

        Bundle extras = getIntent().getExtras();

        acceptbtn = (Button) findViewById(R.id.acceptbtn);
        rejectbtn = (Button) findViewById(R.id.rejectbtn);
        callCustomer = (FloatingActionButton) findViewById(R.id.callCustomer);

        cardSingleItem = (LinearLayout) findViewById(R.id.cardSingleItem);
        //collects the editText
        textFieldsOfShipment = new HashMap<>();
        textFieldsOfShipment.put("Name", (TextView) findViewById(R.id.Name));
        textFieldsOfShipment.put("NumberOfClient", (TextView) findViewById(R.id.NumberOfClient));
        textFieldsOfShipment.put("TotalPrice", (TextView) findViewById(R.id.TotalPrice));
        textFieldsOfShipment.put("Weight", (TextView) findViewById(R.id.Weight));
        textFieldsOfShipment.put("nearest_sign_note", (TextView) findViewById(R.id.nearest_sign_note));
        textFieldsOfShipment.put("notes_of_shipping", (TextView) findViewById(R.id.notes_of_shipping));
        textFieldsOfShipment.put("status", (TextView) findViewById(R.id.status));
        textFieldsOfShipment.put("date", (TextView) findViewById(R.id.date));
        textFieldsOfShipment.put("processNum", (TextView) findViewById(R.id.processNum));
        textFieldsOfShipment.put("orderNumber", (TextView) findViewById(R.id.orderNumber));
        textFieldsOfShipment.put("clientAddress", (TextView) findViewById(R.id.clientAddress));
        productImage = findViewById(R.id.photo);
        if (extras != null){
            //retrieve the inserted data
            if (extras.get("activity").equals("FromClientActivity")){
//

                if (extras.get("status").equals("pickedUp")){
                    ViewGroup.LayoutParams params = acceptbtn.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = 0;
                params.width = 0;
                acceptbtn.setLayoutParams(params);

                ViewGroup.LayoutParams params2 = rejectbtn.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params2.height = 0;
                params2.width = 0;
                rejectbtn.setLayoutParams(params2);

                }else {

                    acceptbtn.setText("PickedUp");


                    acceptbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Query query2 = reference.orderByChild("ProcessNum").equalTo("First");
                            AlertDialog.Builder alert = new AlertDialog.Builder(InfoOfTracking.this);

                            alert.setTitle("Ship99");
                            alert.setMessage("Did you Delivered package Successfully?");

//                            // Set an EditText view to get user input
//                            final EditText input = new EditText(getContext());
//                            alert.setView(input);

                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                                    RequestModel requestModel = new RequestModel();
                                                    requestModel = ds.getValue(RequestModel.class);


                                                    if (requestModel.getOrderNumber().equals(extras.get("orderNumber"))){
//

                                                        String key = ds.getKey();
                                                        reference.child(key).child
                                                                ("status").setValue("pickedUp");
                                                        reference.child(key).child("status_pickupAddress").setValue
                                                                ("pickedUp" + ":" + requestModel.getPickupAddress());

                                                        reference.child(key).child("status_SecondDriverUid").setValue
                                                                ("pickedUp" + ":" + mAuth.getCurrentUser().getUid());

//                                                    reference.child(key).child("recievedFrom").setValue
//                                                            ("client");
                                                        reference.child(key).child("status_UserId").setValue
                                                                ("pickedUp" + ":" +requestModel.getuserId());

                                                        reference.child(key).child("status_WakeelUid").setValue
                                                                ("pickedUp" + ":" +requestModel.getWakeelUid());
                                                        reference.child(key).child("status_uid").setValue
                                                                ("pickedUp" + ":" +extras.get("userId"));

                                                        reference.child(key).child("FirstDriverUid").setValue(mAuth.getCurrentUser().getUid());

                                                    }

//
                                                }
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });



                                }

                            });

                            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Canceled.
                                }
                            });

//                    alert.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    });

                            alert.show();
                        }
                    });

                    rejectbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View child = getLayoutInflater().inflate(R.layout.couldnotreachuser, null);
                            Spinner spinner = child.findViewById(R.id.selectStatus);
                            Button setButton = child.findViewById(R.id.buttonOk);
                            Button cancelButton = child.findViewById(R.id.buttonCancel);
                            EditText editText = child.findViewById(R.id.edit);


                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InfoOfTracking.this,
                                    R.array.chooseStatusOfCustomer, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                    statusSelected = parent.getItemAtPosition(position).toString();


                                }


                                @Override
                                public void onNothingSelected(AdapterView<?> arent) {

                                }
                            });



                            setButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(InfoOfTracking.this,"set Button",Toast.LENGTH_SHORT).show();
                                    Query query3 = reference.orderByChild("ProcessNum").equalTo("First");

                                    query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                                    RequestModel requestModel = new RequestModel();
                                                    requestModel = ds.getValue(RequestModel.class);


                                                    if (requestModel.getOrderNumber().equals(extras.get("orderNumber"))){
//                                                            String key = dataSnapshot.getChildren().iterator().next().getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//                                                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                                                        String key = ds.getKey();

                                                        reference.child(key).child
                                                                ("reasonForNotDlivered").setValue(statusSelected);
                                                        reference.child(key).child
                                                                ("status").setValue("Pickup Cancelled");
                                                        if (editText.getText().toString().equals("")){
                                                            reference.child(key).child
                                                                    ("NotesWhynotDelivered").setValue("no Notes Found from Driver");
                                                        }else{
                                                            reference.child(key).child
                                                                    ("NotesWhynotDelivered").setValue(editText.getText().toString());
                                                        }

                                                        reference.child(key).child("status_pickupAddress").setValue
                                                                ("Pickup Cancelled" + ":" + requestModel.getPickupAddress());

                                                        reference.child(key).child("status_FirstDriverUid").setValue
                                                                ("Pickup Cancelled" + ":" + mAuth.getCurrentUser().getUid());

//
                                                        reference.child(key).child("status_UserId").setValue
                                                                ("Pickup Cancelled" + ":" +requestModel.getuserId());

//
                                                        reference.child(key).child("ProcessNum").setValue("-1");


                                                        reference.child(key).child("FirstDriverUid").setValue(mAuth.getCurrentUser().getUid());

                                                    }

//
                                                }
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            });

                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(InfoOfTracking.this,"cancel Button",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

//                        image.setImageBitmap(bitmap);
                            // image.setImageDrawable(Drawable.createFromPath(String.valueOf(R.drawable.photoofme)));



                            Dialog settingsDialog = new Dialog(cardSingleItem.getContext());
                            settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                            settingsDialog.setContentView(child);

                            settingsDialog.show();

                        }
                    });
                }



            }
            else{

                acceptbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Query query2 = reference.orderByChild("ProcessNum").equalTo("Third");
                        AlertDialog.Builder alert = new AlertDialog.Builder(InfoOfTracking.this);

                        alert.setTitle("Ship99");
                        alert.setMessage("Did you Delivered package Successfully?");

//                            // Set an EditText view to get user input
//                            final EditText input = new EditText(getContext());
//                            alert.setView(input);

                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                                RequestModel requestModel = new RequestModel();
                                                requestModel = ds.getValue(RequestModel.class);


                                                if (requestModel.getOrderNumber().equals(extras.get("orderNumber"))){
//
                                                    String key = ds.getKey();
                                                    reference.child(key).child
                                                            ("status").setValue("Shipped");
                                                    reference.child(key).child("status_pickupAddress").setValue
                                                            ("Shipped" + ":" + requestModel.getPickupAddress());

                                                    reference.child(key).child("status_SecondDriverUid").setValue
                                                            ("Shipped" + ":" + mAuth.getCurrentUser().getUid());

//                                                    reference.child(key).child("recievedFrom").setValue
//                                                            ("client");
                                                    reference.child(key).child("status_UserId").setValue
                                                            ("Shipped" + ":" +extras.get("userId"));

                                                    reference.child(key).child("status_WakeelUid").setValue
                                                            ("Shipped" + ":" +requestModel.getWakeelUid());

                                                    reference.child(key).child("ProcessNum").setValue("Done");

                                                    reference.child(key).child("SecondDriverUid").setValue(mAuth.getCurrentUser().getUid());

                                                }

//
                                            }
                                        finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                            }

                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                            }
                        });

                        alert.show();
                    }
                });

                rejectbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View child = getLayoutInflater().inflate(R.layout.couldnotreachuser, null);
                        Spinner spinner = child.findViewById(R.id.selectStatus);
                        Button setButton = child.findViewById(R.id.buttonOk);
                        Button cancelButton = child.findViewById(R.id.buttonCancel);
                        EditText editText = child.findViewById(R.id.edit);


                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InfoOfTracking.this,
                                R.array.chooseStatusOfCustomer, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                statusSelected = parent.getItemAtPosition(position).toString();


                            }


                            @Override
                            public void onNothingSelected(AdapterView<?> arent) {

                            }
                        });



                        setButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(InfoOfTracking.this,"set Button",Toast.LENGTH_SHORT).show();
                                Query query3 = reference.orderByChild("ProcessNum").equalTo("Third");

                                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                                RequestModel requestModel = new RequestModel();
                                                requestModel = ds.getValue(RequestModel.class);


                                                if (requestModel.getOrderNumber().equals(extras.get("orderNumber"))){
//                                                            String key = dataSnapshot.getChildren().iterator().next().getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//                                                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                                                    String key = ds.getKey();

                                                    reference.child(key).child
                                                            ("reasonForNotDlivered").setValue(statusSelected);
                                                    reference.child(key).child
                                                            ("status").setValue("Failed to deliver");
                                                    if (editText.getText().toString().equals("")){
                                                        reference.child(key).child
                                                                ("NotesWhynotDelivered").setValue("no Notes Found from Driver");
                                                    }else{
                                                        reference.child(key).child
                                                                ("NotesWhynotDelivered").setValue(editText.getText().toString());
                                                    }

                                                    reference.child(key).child("status_pickupAddress").setValue
                                                            ("Failed to deliver" + ":" + requestModel.getPickupAddress());

                                                    reference.child(key).child("status_SecondDriverUid").setValue
                                                            ("Failed to deliver" + ":" + mAuth.getCurrentUser().getUid());

//                                                    reference.child(key).child("recievedFrom").setValue
//                                                            ("client");
                                                    reference.child(key).child("status_UserId").setValue
                                                            ("Failed to deliver" + ":" +requestModel.getuserId());

                                                    reference.child(key).child("status_WakeelUid").setValue
                                                            ("Failed to deliver" + ":" +requestModel.getWakeelUid());


                                                    reference.child(key).child("ProcessNum").setValue("-1");
                                                    // number track the two chances for customer to get the order and he has two chcances only then the order will
                                                    //considered cancelled and go back to hub
                                                    reference.child(key).child("ChanceNumberToDeliver").setValue("1");


                                                    reference.child(key).child("SecondDriverUid").setValue(mAuth.getCurrentUser().getUid());

                                                }

//
                                            }
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });

                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(InfoOfTracking.this,"cancel Button",Toast.LENGTH_SHORT).show();
                              finish();
                            }
                        });

//                        image.setImageBitmap(bitmap);
                        // image.setImageDrawable(Drawable.createFromPath(String.valueOf(R.drawable.photoofme)));



                        Dialog settingsDialog = new Dialog(cardSingleItem.getContext());
                        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                        settingsDialog.setContentView(child);

                        settingsDialog.show();

                    }
                });


            }


            textFieldsOfShipment.get("Name").setText(extras.get("name").toString());
            textFieldsOfShipment.get("NumberOfClient").setText(extras.get("phone").toString());
            textFieldsOfShipment.get("Weight").setText(extras.get("weight").toString());
            textFieldsOfShipment.get("TotalPrice").setText(extras.get("totalPrice").toString());
            textFieldsOfShipment.get("notes_of_shipping").setText(extras.get("notesOfShipping").toString());
            textFieldsOfShipment.get("nearest_sign_note").setText(extras.get("nearestSignNote").toString());
            textFieldsOfShipment.get("status").setText(extras.get("status").toString());
            textFieldsOfShipment.get("date").setText(extras.get("date").toString());
            textFieldsOfShipment.get("processNum").setText(extras.get("processNum").toString());
            textFieldsOfShipment.get("orderNumber").setText(extras.get("orderNumber").toString());
            textFieldsOfShipment.get("clientAddress").setText(extras.get("clientAddress").toString());
            Picasso.with(productImage.getContext()).load(extras.get("photo").toString()).fit().centerCrop()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(productImage);

            productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View child = getLayoutInflater().inflate(R.layout.image_layout, null);
                    ImageView image = child.findViewById(R.id.imageee);

//                        image.setImageBitmap(bitmap);
                    // image.setImageDrawable(Drawable.createFromPath(String.valueOf(R.drawable.photoofme)));

                    Picasso.with(image.getContext()).load(extras.get("photo").toString()).fit().centerCrop()
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.loading)
                            .into(image);

                    Dialog settingsDialog = new Dialog(cardSingleItem.getContext());
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                    settingsDialog.setContentView(child);

                    settingsDialog.show();
                }
            });

            callCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ContextCompat.checkSelfPermission(InfoOfTracking.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(InfoOfTracking.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+extras.get("phone").toString()));//change the number
                        startActivity(callIntent);
                    }


                }
            });



        }else{

            Toast.makeText(this,"Error found!",Toast.LENGTH_SHORT).show();
        }

            

        }









//    public void init(){
//
//        one = (ImageView) findViewById(R.id.onecheck);
//        two = (ImageView) findViewById(R.id.twocheck);
//        three = (ImageView) findViewById(R.id.threecheck);
//        four = (ImageView) findViewById(R.id.fourcheck);
//        five = (ImageView) findViewById(R.id.fivecheck);
//        six = (ImageView) findViewById(R.id.sixcheck);
//
//
//        text1 = (TextView) findViewById(R.id.undert1);
//
//
//        text2 = (TextView) findViewById(R.id.undert2);
//
//
//        text3 = (TextView) findViewById(R.id.undert3);
//
//
//        text4 = (TextView) findViewById(R.id.undert4);
//
//
//        text5 = (TextView) findViewById(R.id.undert5);
//
//
//        text6 = (TextView) findViewById(R.id.undert6);
//
//        text7 = (TextView) findViewById(R.id.datenumber);
//        text8 = (TextView) findViewById(R.id.datemonthandyear);
//
//        reference = FirebaseDatabase.getInstance()
//                .getReference("Requests");
//
//
//    }
}
