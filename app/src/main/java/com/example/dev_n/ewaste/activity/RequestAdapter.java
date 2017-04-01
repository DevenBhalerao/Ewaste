package com.example.dev_n.ewaste.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.dev_n.ewaste.R;

import java.util.ArrayList;

/**
 * Created by Dev_N on 23-03-2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private ArrayList<RequestData> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView IdView;
        public TextView addressView;
        public TextView weightView;
        private Button viewRequestButton;

        public ViewHolder(View v) {
            super(v);
            //mTextView = v;
            IdView = (TextView) v.findViewById(R.id.request_fragment_ID_field);
            addressView = (TextView) v.findViewById(R.id.request_fragment_Address_field);
            weightView = (TextView) v.findViewById(R.id.request_fragment_Items_Weight_field);
            viewRequestButton = (Button) v.findViewById(R.id.view_request_bt);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RequestAdapter(ArrayList<RequestData> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_fragment, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.IdView.setText(mDataset.get(position).getID());
        holder.addressView.setText(mDataset.get(position).getAddress());
        holder.weightView.setText(mDataset.get(position).getApproxWeight());

        holder.viewRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context ,OrderDetailsActivity.class);
                context.startActivity(it);
            }
        });




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
