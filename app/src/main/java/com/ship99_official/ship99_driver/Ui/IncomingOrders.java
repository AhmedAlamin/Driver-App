package com.ship99_official.ship99_driver.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ship99_official.ship99_driver.Pojo.InfoModel;
import com.ship99_official.ship99_driver.Pojo.PickupModel;
import com.ship99_official.ship99_driver.Pojo.RequestModel;
import com.ship99_official.ship99_driver.Pojo.RequestViewHolder;
import com.ship99_official.ship99_driver.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class IncomingOrders extends Fragment {

    //     FirebaseRecyclerAdapter adapter;
    private RecyclerView recview;
    private FirebaseAuth mAuth;
    private Query query ;
    private DatabaseReference reference, userRef;
    private FirebaseRecyclerOptions<PickupModel> options;
    private FirebaseRecyclerAdapter<PickupModel, RequestViewHolder> adapter;
    private ImageView imageView;
    private List<PickupModel> model;
    private Button acceptBtn,rejectBtn;
    private String driverZone ="zero";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_incoming_orders, container, false);



        acceptBtn = (Button)v.findViewById(R.id.acceptbtn);
        rejectBtn =(Button) v.findViewById(R.id.rejectbtn);


        imageView = (ImageView) v.findViewById(R.id.reservation_empty_view);


        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("Requests");

        recview = (RecyclerView) v.findViewById(R.id.reservationsRV);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        recview.setHasFixedSize(true);





        Query query2 = FirebaseDatabase.getInstance().getReference().child("Drivers/"+ mAuth.getCurrentUser().getUid()).child("zoneLocation");

//        Query query3 = FirebaseDatabase.getInstance().getReference("Requests").orderByChild("status")
//                .equalTo("Active");




        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                driverZone = dataSnapshot.getValue(String.class);




                query = FirebaseDatabase.getInstance().getReference("Requests").orderByChild("status_pickupAddress")
                        .equalTo("Waiting For Picking_"+driverZone);


                options = new FirebaseRecyclerOptions.Builder<PickupModel>()
                        .setQuery(query, PickupModel.class)
                        .build();


                adapter = new FirebaseRecyclerAdapter<PickupModel, RequestViewHolder>(options) {

                    @Override
                    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = inflater.inflate(R.layout.singlerow, parent, false);
                        return new RequestViewHolder(view);
                    }

                    @Override
                    public int getItemCount() {

                        if (super.getItemCount() == 0) {

                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            // Changes the height and width to the specified *pixels*
                            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                            imageView.setLayoutParams(params);

                            ViewGroup.LayoutParams params2 = recview.getLayoutParams();
                          // Changes the height and width to the specified *pixels*
                            params2.height = 0;
                            params2.width = 0;
                            recview.setLayoutParams(params2);
                        }else {
                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            // Changes the height and width to the specified *pixels*
                            params.height = 0;
                            params.width = 0;
                            imageView.setLayoutParams(params);

                            ViewGroup.LayoutParams params2 = recview.getLayoutParams();
                            params2.height = RecyclerView.LayoutParams.MATCH_PARENT;
                            params2.width = RecyclerView.LayoutParams.MATCH_PARENT;
                            recview.setLayoutParams(params2);

                        }
                        return super.getItemCount();





                    }



                    @Override
                    protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull PickupModel model) {

//                        if (getItemCount() < 1){
//                            imageView.setVisibility(View.VISIBLE);
//                        }else {
//                            imageView.setVisibility(View.GONE);
//                        }
//




//                Query query = FirebaseDatabase.getInstance().getReference("Requests").orderByChild("pickupAddress")
//                        .equalTo(model.getPickupAddress());
//
//
//
//                options = new FirebaseRecyclerOptions.Builder<PickupModel>()
//                        .setQuery(query, PickupModel.class)
//                        .build();
//
//
//                adapter = new FirebaseRecyclerAdapter<PickupModel, RequestViewHolder>(options) {
//
//                    @Override
//                    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                        View view = inflater.inflate(R.layout.singlerow, parent, false);
//                        return new RequestViewHolder(view);
//                    }
//
//                    @Override
//                    public int getItemCount() {
//                        return super.getItemCount();
//
//                    }
//
//
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull PickupModel model) {
//
//
//                        holder.weight.setText(model.getWeight());
//
//                        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Query query2 = reference.child("Requests").orderByChild("status");
//                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//
//                                        alert.setTitle("Ship99");
//                                        alert.setMessage("Are you sure you want Accept this shipment?");
//                                        alert.setIcon(R.drawable.shipping_car);
//
//
//
//                                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int whichButton) {
//                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                                    String key = snapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//                                                    String path = "/" +dataSnapshot.getKey() + "/" + key;
//                                                    reference.child(path).child("status").setValue("Accepted: "+mAuth.getCurrentUser().getEmail());
//                                                }
//
//                                            }
//                                        });
//
//                                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int whichButton) {
//                                                // Canceled.
//                                            }
//                                        });
//
//                                        alert.show();
//
////                                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
////                                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
////                                String path = "/" +dataSnapshot.getKey() + "/" + key;
////                                reference.child(path).child("status").setValue("Accepted: "+mAuth.getCurrentUser().getEmail());
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                            }
//                        });
//
//                        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//
//                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//
//                                alert.setTitle("Ship99");
//                                alert.setMessage("Are you sure you want Reject this shipment?");
//                                alert.setIcon(R.drawable.shipping_car);
//
//
//
//                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                                        Toast.makeText(getContext(),"Still Use less",Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                        // Canceled.
//                                    }
//                                });
//
//                                alert.show();
//
//                            }
//                        });
//
//                        holder.nearestSignNotes.setText(String.valueOf(model.getNearest_sign_note()));
//                        holder.notesOfShipping.setText(String.valueOf(model.getNotes_of_shipping()));
//                        holder.pickupAddress.setText(String.valueOf(model.getPickupAddress()));
//
//                        Picasso.with(holder.img.getContext()).load(model.getPhotoOfProduct()).fit().centerCrop()
//                                .placeholder(R.drawable.loading)
//                                .error(R.drawable.loading)
//                                .into(holder.img);
//
//                    }
//
//
//
//
//
//                };


//                    Log.d("pojo000011",driverZone);
//                    Query query2 = reference.child("Requests").orderByChild("pickupAddress").equalTo(driverZone);
//
//                    query2.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                String key = snapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//                                String path = "/" + dataSnapshot.getKey() + "/" + key;
//                                InfoModel modelDriver = snapshot.getValue(InfoModel.class);
//
//                                Log.d("pojo--", modelDriver.getzoneLocation());
//                                Log.d("pojo---", String.valueOf(model.getPickupAddress().equals(modelDriver.getzoneLocation())));
//
//
////                                if (model.getPickupAddress().equals(modelDriver.getzoneLocation())) {
//
//                                    Log.d("pojo--", driverZone);



                        holder.weight.setText(model.getWeight());

                        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Query query2 = reference.orderByChild("status");
                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                                        alert.setTitle("Ship99");
                                        alert.setMessage("Are you sure you want Accept this shipment?");
                                        alert.setIcon(R.drawable.shipping_car);


                                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                if (dataSnapshot.exists()) {
                                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                                        RequestModel requestModel = new RequestModel();
                                                        requestModel = ds.getValue(RequestModel.class);


                                                        if (requestModel.getStatus().equals("Waiting For Picking")){
//                                                            String key = dataSnapshot.getChildren().iterator().next().getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//                                                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                                                            String key = ds.getKey();
                                                    reference.child(key).child
                                                            ("status").setValue("Picking In The way");
                                                    reference.child(key).child("status_pickupAddress").setValue
                                                            ("First" + mAuth.getCurrentUser().getEmail() + ":" + model.getPickupAddress());

                                                    reference.child(key).child("PickedBy").setValue
                                                            (mAuth.getCurrentUser().getEmail());

                                                    reference.child(key).child("recievedFrom").setValue
                                                            ("client");

                                                    reference.child(key).child("ProcessNum").setValue("First");

                                                    reference.child(key).child("DriverUid").setValue(mAuth.getCurrentUser().getUid());

                                                            Log.d("pojotest", " datasnapshop path:" + key);
                                                        }

//
                                                    }
                                                }

                                            }

                                        });

                                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                // Canceled.
                                            }
                                        });

                                        alert.show();

//                                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
//                                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//                                String path = "/" +dataSnapshot.getKey() + "/" + key;
//                                reference.child(path).child("status").setValue("Accepted: "+mAuth.getCurrentUser().getEmail());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });

                        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                                alert.setTitle("Ship99");
                                alert.setMessage("Are you sure you want Reject this shipment?");
                                alert.setIcon(R.drawable.shipping_car);


                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Toast.makeText(getContext(), "Still Use less", Toast.LENGTH_SHORT).show();
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

                        holder.nearestSignNotes.setText(String.valueOf(model.getNearest_sign_note()));
                        holder.notesOfShipping.setText(String.valueOf(model.getNotes_of_shipping()));
                        holder.pickupAddress.setText(String.valueOf(model.getPickupAddress()));

                        Picasso.with(holder.img.getContext()).load(model.getPhotoOfProduct()).fit().centerCrop()
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.loading)
                                .into(holder.img);
//                                }
//                                else
//                           {
//                               Toast.makeText(getContext(),"notFount",Toast.LENGTH_SHORT).show();
//                               recview.setVisibility(View.GONE);
//                               holder.cardSingleItem.removeAllViews();
//                           }
                    }
//                       }

//                        @Override
//                       public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                       }
//                  });
//
//                }






                };


                recview.setAdapter(adapter);

                adapter.startListening();



                //------------




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });













        return v;
    }


}