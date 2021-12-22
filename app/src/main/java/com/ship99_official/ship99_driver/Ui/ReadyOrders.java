package com.ship99_official.ship99_driver.Ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ship99_official.ship99_driver.Pojo.InfoModel;
import com.ship99_official.ship99_driver.Pojo.RequestModel;
import com.ship99_official.ship99_driver.Pojo.RequestViewHolder;
import com.ship99_official.ship99_driver.R;
import com.ship99_official.ship99_driver.main.SectionsPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReadyOrders extends Fragment {

//    //     FirebaseRecyclerAdapter adapter;
//    private RecyclerView recview;
//    private FirebaseAuth mAuth;
//    private Query reference, userRef;
//    private DatabaseReference ref;
//    private FirebaseRecyclerOptions<RequestModel> options;
//    private FirebaseRecyclerAdapter<RequestModel, RequestViewHolder2> adapter;
//    private Long orderNum = Long.valueOf(0);
//    private ImageView imageView;
//    private List<RequestModel> model;
//    private ProgressBar progressbar;
//    private ArrayList<String> arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View v = inflater.inflate(R.layout.fragment_ready, container, false);


//        fab = v.findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                scanCode();
//            }
//        });
//
//        ref =  FirebaseDatabase.getInstance().getReference();
//
//
//        progressbar = v.findViewById(R.id.progressbar);
//        imageView = (ImageView) v.findViewById(R.id.reservation_empty_view);
//
//
//
//        mAuth = FirebaseAuth.getInstance();
//
//        reference = FirebaseDatabase.getInstance().getReference("Requests");
//
//
//
//
//        recview = (RecyclerView) v.findViewById(R.id.reservationsRV);
//        recview.setLayoutManager(new LinearLayoutManager(getContext()));
//        recview.setHasFixedSize(true);
//
//
//        BottomNavigationView navView = v.findViewById(R.id.top_navgation);
//
//        //Pass the ID's of Different destinations
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.clientNavigation, R.id.hubNavigation)
//                .build();
//
//        //Initialize NavController.
//        NavController navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
////        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
//
//
//
//
//
//        Query query = FirebaseDatabase.getInstance().getReference("Requests").orderByChild("status")
//                .equalTo("Accepted: "+mAuth.getCurrentUser().getEmail());
//
//     //   Log.d("pojo0001",driverZone);
//
//
//        options = new FirebaseRecyclerOptions.Builder<RequestModel>()
//                .setQuery(query, RequestModel.class)
//                .build();
//
//
//
//
//
//
//        adapter = new FirebaseRecyclerAdapter<RequestModel, RequestViewHolder2>(options) {
//
//            @Override
//            public RequestViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = inflater.inflate(R.layout.singlerow2, parent, false);
//                return new RequestViewHolder2(view);
//            }
//
//            @Override
//            public int getItemCount() {
//                return super.getItemCount();
//
//            }
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull RequestViewHolder2 holder, int position, @NonNull RequestModel model) {
//
//
////                Query query2 = ref.child("Requests").orderByChild("status").equalTo("Accepted: "+mAuth.getCurrentUser().getEmail());
////
////                query2.addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//////                            String key = snapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//////                            String path = "/" +dataSnapshot.getKey() + "/" + key;
////                            InfoModel modelDriver = snapshot.getValue(InfoModel.class);
//
//
////                            if(model.getPickupAddress().equals(modelDriver.getzoneLocation())){
//
//                                holder.price.setText(model.getTotalPrice());
//
//                                holder.orderId.setText(String.valueOf(model.getOrderNumber()));
//
//
//                                holder.cardView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent i = new Intent(getContext(),InfoOfSingleShipment.class);
//                                        i.putExtra("status",model.getStatus());
//                                        i.putExtra("date",model.getDate());
//                                        startActivity(i);
//                                    }
//                                });
//
//                                holder.clientAddress.setText(model.getClientAddress());
//
//                                holder.status.setText(model.getStatus());
//
//                                holder.name.setText(model.getName());
//
//                                if (model.getNumberOfClient() == null) {
//                                    Toast.makeText(getContext(), "null object", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    holder.numberOfClient.setText(model.getNumberOfClient());
//                                }
//
//                                Log.d("pojo photoTarckFragment",model.getPhotoOfProduct());
//                                Picasso.with(holder.img.getContext()).load(model.getPhotoOfProduct()).fit().centerCrop()
//                                        .placeholder(R.drawable.loading)
//                                        .error(R.drawable.loading)
//                                        .into(holder.img);
//
////                    Picasso.with(holder.img.getContext()).load(model.getPhotoOfProduct()).into(holder.img);
//
//
//                                //  Glide.with(holder.img.getContext()).load(model.getPhotoOfProduct()).into(holder.img);
//
////                            }
////                            else{
//////                                Toast.makeText(getContext(),"notFount",Toast.LENGTH_SHORT).show();
////                                holder.cardView.removeAllViews();
////
////                            }
//
//                        }
//
//
//
//
//
//
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                    }
////                });
//
//
//
////            }
//
//        };
//
//        recview.setAdapter(adapter);
//        adapter.startListening();


//        BottomNavigationView navView = v.findViewById(R.id.top_navgation);
//
//        //Pass the ID's of Different destinations
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.clientNavigation, R.id.hubNavigation)
//                .build();
//
//
//
//
////        //Initialize NavController.
//        NavController navController = Navigation.findNavController(getActivity(),v.findViewById(R.id.navHostFragment).getId());
//        NavigationUI.setupWithNavController(navView, navController);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        return v;
    }



//    @Override
//    public void onStart() {
//        super.onStart();
////        if (adapter != null) {
////            adapter.startListening();
////        }
//
//    }
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
////        if (adapter != null) {
////            adapter.startListening();
////        }
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
////
////        if (adapter != null) {
////            adapter.startListening();
////        }
//
//    }


//    private void scanCode(){
//
//        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ReadyOrders.this);
//        integrator.setCaptureActivity(CaptureAct.class);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.setOrientationLocked(false);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//        integrator.setPrompt("Scanning Code");
//        integrator.initiateScan();
//    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//
//    IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
//    if (result != null){
//        if (result.getContents() != null){
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setMessage(result.getContents());
//            builder.setTitle("Scanning Result");
//            builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    scanCode();
//                }
//            }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    try {
//                        finalize();
//                    } catch (Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
//        else {
//            Toast.makeText(getContext(),"No Result",Toast.LENGTH_SHORT).show();
//        }
//    }else {
//        super.onActivityResult(requestCode,resultCode,data);
//    }
//}


}




























