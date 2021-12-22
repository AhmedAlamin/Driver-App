package com.ship99_official.ship99_driver.Ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ship99_official.ship99_driver.Pojo.RequestModel;
import com.ship99_official.ship99_driver.Pojo.RequestViewHolder2;
import com.ship99_official.ship99_driver.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ClientOrdersNavigation extends Fragment {

    //     FirebaseRecyclerAdapter adapter;
    private RecyclerView recview;
    private FirebaseAuth mAuth;
    private Query reference, userRef;
    private DatabaseReference ref;
    private FirebaseRecyclerOptions<RequestModel> options;
    private FirebaseRecyclerAdapter<RequestModel, RequestViewHolder2> adapter;
    private Long orderNum = Long.valueOf(0);
    private ImageView imageView;
    private List<RequestModel> model;
    private ProgressBar progressbar;
    private ArrayList<String> arrayList;
    private FloatingActionButton fab;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View v = inflater.inflate(R.layout.fragment_client_orders_navigation, container, false);



        ref =  FirebaseDatabase.getInstance().getReference();


        progressbar = v.findViewById(R.id.progressbar);
        imageView = (ImageView) v.findViewById(R.id.reservation_empty_view);



        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Requests");




        recview = (RecyclerView) v.findViewById(R.id.reservationsRV);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        recview.setHasFixedSize(true);


        Query query = FirebaseDatabase.getInstance().getReference("Requests").orderByChild("ProcessNum")
                .equalTo("First");



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


//                Query query2 = ref.child("Requests").orderByChild("status").equalTo("Accepted: "+mAuth.getCurrentUser().getEmail());
//
//                query2.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                            String key = snapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
////                            String path = "/" +dataSnapshot.getKey() + "/" + key;
//                            InfoModel modelDriver = snapshot.getValue(InfoModel.class);


//                            if(model.getPickupAddress().equals(modelDriver.getzoneLocation())){

                holder.price.setText(model.getTotalPrice());

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

                holder.orderId.setText(String.valueOf(model.getOrderNumber()));


                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent i = new Intent(getContext(),InfoOfTracking.class);
//                        startActivity(i);

//                        Toast.makeText(getContext(), "Sorry.Not Authorized Access", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getContext(),InfoOfTracking.class);
                        i.putExtra("activity","FromClientActivity");
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
                        i.putExtra("userId",model.getuserId());
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

//                    Picasso.with(holder.img.getContext()).load(model.getPhotoOfProduct()).into(holder.img);


                //  Glide.with(holder.img.getContext()).load(model.getPhotoOfProduct()).into(holder.img);

//                            }
//                            else{
////                                Toast.makeText(getContext(),"notFount",Toast.LENGTH_SHORT).show();
//                                holder.cardView.removeAllViews();
//
//                            }

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



        return v;
    }



    @Override
    public void onStart() {
        super.onStart();


        if (adapter != null) {
            adapter.startListening();
        }

    }


    @Override
    public void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.startListening();
        }

    }

    @Override
    public void onResume() {
        super.onResume();


        if (adapter != null) {
            adapter.startListening();
        }

    }




}


