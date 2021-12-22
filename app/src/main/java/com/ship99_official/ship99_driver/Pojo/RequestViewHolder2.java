package com.ship99_official.ship99_driver.Pojo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ship99_official.ship99_driver.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestViewHolder2 extends RecyclerView.ViewHolder {

   public CircleImageView img;


    public TextView orderId;
    public TextView clientAddress;
    public TextView numberOfClient;
    public TextView status;
    public TextView name;
    public TextView price;
   public CardView cardView;


    public RequestViewHolder2(@NonNull View itemView)
    {
        super(itemView);
        img=(CircleImageView)itemView.findViewById(R.id.photoOfProduct);
        orderId=(TextView)itemView.findViewById(R.id.orderId);
        clientAddress=(TextView)itemView.findViewById(R.id.ClientAddress);
        numberOfClient=(TextView)itemView.findViewById(R.id.NumberOfClient);
        status=(TextView)itemView.findViewById(R.id.status);
        name = (TextView)itemView.findViewById(R.id.nameOfClient);
        price = (TextView)itemView.findViewById(R.id.priceofproduct);


        cardView = (CardView)itemView.findViewById(R.id.groupLinearLayout);


    }



}
