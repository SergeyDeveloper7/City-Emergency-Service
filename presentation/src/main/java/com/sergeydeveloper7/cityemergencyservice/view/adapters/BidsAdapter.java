package com.sergeydeveloper7.cityemergencyservice.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.model.general.Bid;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.AdminFragment;
import com.sergeydeveloper7.cityemergencyservice.view.fragments.WorkersScreenFragment;
import com.sergeydeveloper7.cityemergencyservice.view.holders.RequestViewHolder;

import java.util.ArrayList;

/**
 * Created by serg on 23.01.18.
 */

public class BidsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int             VIEW_TYPE_REQUEST = 0;
    private final int             VIEW_TYPE_WORKER = 1;
    private Context               context;
    private ArrayList<Bid>        bids;
    private AdminFragment         adminFragment;
    private WorkersScreenFragment workersScreenFragment;

    public BidsAdapter(Context context, ArrayList<Bid> bids, AdminFragment adminFragment) {
        this.context = context;
        this.bids = bids;
        this.adminFragment = adminFragment;
    }

    public BidsAdapter(Context context, ArrayList<Bid> bids, WorkersScreenFragment workersScreenFragment) {
        this.context = context;
        this.bids = bids;
        this.workersScreenFragment = workersScreenFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid, parent, false);
        return new RequestViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RequestViewHolder viewHolder = (RequestViewHolder) holder;
        viewHolder.requestsNumberTextViewDetail.setText(String.valueOf(bids.get(viewHolder.getAdapterPosition()).getId()));
        viewHolder.requestsTitleTextViewDetail.setText(bids.get(viewHolder.getAdapterPosition()).getTitle());
//        viewHolder.requestsStatusTextViewDetail.setText(bids.get(viewHolder.getAdapterPosition()).getStatus());
        viewHolder.itemView.setOnClickListener((View v) -> {
            if(adminFragment != null){
                adminFragment.viewBidDetails(bids.get(viewHolder.getAdapterPosition()));
            } else if(workersScreenFragment != null){

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.bids == null ? 0 : this.bids.size();
    }


}
