package com.ship99_official.ship99_driver.Pojo;

import android.sax.TextElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ship99_official.ship99_driver.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestViewHolder extends RecyclerView.ViewHolder {

   public CircleImageView img;

   public CardView cardSingleItem;

   public TextView pickupAddress,weight,notesOfShipping,nearestSignNotes,timerHour,timerSeconds;

   public Button acceptBtn,rejectBtn;



    public RequestViewHolder(@NonNull View itemView)
    {
        super(itemView);
        img=(CircleImageView)itemView.findViewById(R.id.photoOfProduct);

        cardSingleItem = (CardView) itemView.findViewById(R.id.cardSingleItem);

        pickupAddress = (TextView) itemView.findViewById(R.id.pickupaddress);
        weight = (TextView) itemView.findViewById(R.id.weightofproduct);
        notesOfShipping = (TextView) itemView.findViewById(R.id.notesOfShipping);
        nearestSignNotes = (TextView) itemView.findViewById(R.id.nearestsign);

        acceptBtn =(Button) itemView.findViewById(R.id.acceptbtn);
        rejectBtn= (Button) itemView.findViewById(R.id.rejectbtn);

        timerHour =(TextView) itemView.findViewById(R.id.pickuponehour);
        timerSeconds=(TextView)itemView.findViewById(R.id.pickuponeminute);


    }



}
