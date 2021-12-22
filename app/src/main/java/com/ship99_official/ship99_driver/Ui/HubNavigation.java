package com.ship99_official.ship99_driver.Ui;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ship99_official.ship99_driver.Pojo.RequestModel;
import com.ship99_official.ship99_driver.Pojo.RequestViewHolder2;
import com.ship99_official.ship99_driver.R;
import com.squareup.picasso.Picasso;

public class HubNavigation extends Fragment {
    private FloatingActionButton fab;
    static final int PERMISSION_REQUEST_CODE =0 ;
    private RecyclerView recview;
    private FirebaseAuth mAuth;
    private DatabaseReference reference, userRef;
    private DatabaseReference ref;
    private FirebaseRecyclerOptions<RequestModel> options;
    private FirebaseRecyclerAdapter<RequestModel, RequestViewHolder2> adapter;

    public HubNavigation() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hub_navigation, container, false);

        fab = v.findViewById(R.id.fab);
        ref =  FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Requests");

        recview = (RecyclerView) v.findViewById(R.id.reservationsRV);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        recview.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().getReference("Requests").orderByChild("ProcessNum")
                .equalTo("Fourth");



        options = new FirebaseRecyclerOptions.Builder<RequestModel>()
                .setQuery(query, RequestModel.class)
                .build();


        adapter = new FirebaseRecyclerAdapter<RequestModel, RequestViewHolder2>(options) {

            @Override
            public RequestViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.singlerow2, parent, false);
                return new RequestViewHolder2(view);
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();

            }


            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder2 holder, int position, @NonNull RequestModel model) {




                holder.price.setText(model.getTotalPrice());

                holder.orderId.setText(String.valueOf(model.getOrderNumber()));

                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        View child = getLayoutInflater().inflate(R.layout.image_layout, null);
                        ImageView image = child.findViewById(R.id.imageee);

//                        image.setImageBitmap(bitmap);
                       // image.setImageDrawable(Drawable.createFromPath(String.valueOf(R.drawable.photoofme)));

                        Picasso.with(image.getContext()).load(model.getPhotoOfProduct()).fit().centerCrop()
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.loading)
                                .into(image);

                        Dialog settingsDialog = new Dialog(getContext());
                        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                        settingsDialog.setContentView(child);

                        settingsDialog.show();
                    }
                });


                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getContext(),InfoOfTracking.class);
                        i.putExtra("activity","FromHubActivity");
                        i.putExtra("status",model.getStatus());
                        i.putExtra("date",model.getDate());
                        i.putExtra("name",model.getName());
                        i.putExtra("clientAddress",model.getClientAddress());
                        i.putExtra("orderNumber",model.getOrderNumber());
                        i.putExtra("phone",model.getNumberOfClient());
                        i.putExtra("processNum",model.getProcessNum());
                        i.putExtra("photo",model.getPhotoOfProduct());
                        i.putExtra("numberOfClient",model.getNumberOfClient());
                        i.putExtra("totalPrice",model.getTotalPrice());
                        i.putExtra("weight",model.getWeight());
                        i.putExtra("nearestSignNote",model.getNearest_sign_note());
                        i.putExtra("notesOfShipping",model.getNotes_of_shipping());
                        startActivity(i);
                    }
                });

                holder.clientAddress.setText(model.getClientAddress());

                holder.status.setText(model.getStatus());

                holder.name.setText(model.getName());

                if (model.getNumberOfClient() == null) {
                    Toast.makeText(getContext(), "null object", Toast.LENGTH_SHORT).show();
                } else {
                    holder.numberOfClient.setText(model.getNumberOfClient());
                }

                Log.d("pojo photoTarckFragment",model.getPhotoOfProduct());
                Picasso.with(holder.img.getContext()).load(model.getPhotoOfProduct()).fit().centerCrop()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading)
                        .into(holder.img);


            }


//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });



//            }

        };

        recview.setAdapter(adapter);
        adapter.startListening();




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    scanCode();
                }else{
                    requestPermission();
                }
            }
        });

        return v;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
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


    private void scanCode(){

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(HubNavigation.this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }



    private void pickFromHUb(String hubUid){

        FirebaseDatabase.getInstance().getReference("Requests").orderByChild("WakeelUid").equalTo(hubUid).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot nodeDataSnapshot : dataSnapshot.getChildren()) {

                        RequestModel requestModel = new RequestModel();
                        requestModel = nodeDataSnapshot.getValue(RequestModel.class);

                        if (requestModel == null){
                            Log.d("pojokin22",requestModel.toString());
                        }else{

                        if (requestModel.getProcessNum().equals("Third") ){
                            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                            reference.child(key).child("ProcessNum").setValue("Fourth");
                            reference.child(key).child
                                    ("status").setValue("inIt'sWay");

                        }

                        }

                    }
                }else{
                    Toast.makeText(getContext(),"Noting Found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() != null){

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(result.getContents());
                builder.setTitle("Scanning Result");
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        pickFromHUb(result.getContents());
                        Toast.makeText(getContext(),"order Added",Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }).setNeutralButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                Toast.makeText(getContext(),"No Result",Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}