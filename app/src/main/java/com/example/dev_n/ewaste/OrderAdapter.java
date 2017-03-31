package com.example.dev_n.ewaste;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Dev_N on 23-03-2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<OrderData> mDataset;
    private static Context context;
    public static final int CAMERA_REQUEST = 1;
    SharedPreferences sharedPref ;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView IdView;
        public TextView nameView;
        public TextView countView;
        public TextView typeView;
        public ImageButton scanBarcodeButton;


        public ViewHolder(View v) {
            super(v);
            //mTextView = v;
            IdView = (TextView) v.findViewById(R.id.order_fragment_ID_field);
            nameView = (TextView) v.findViewById(R.id.order_fragment_Name_field);
            countView = (TextView) v.findViewById(R.id.order_fragment_Count_field);
            typeView = (TextView) v.findViewById(R.id.order_fragment_Type_field);
            scanBarcodeButton = (ImageButton) v.findViewById(R.id.scan_barcode_bt);



            scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CAMERA}, 1);
                    } else {
                        Intent intent = new Intent(context, SimpleScannerActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderAdapter(ArrayList<OrderData> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
         sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_fragment, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        holder.IdView.setText(mDataset.get(position).getOrderId());
        holder.typeView.setText(mDataset.get(position).getOrderType());
        holder.countView.setText(mDataset.get(position).getOrderCount());
        holder.nameView.setText(mDataset.get(position).getOrderName());


        holder.scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, SimpleScannerActivity.class);
                it.putExtra("orderPosition", position);

                ((Activity)context).startActivity(it);

            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }




}
