package com.sergeydeveloper7.cityemergencyservice.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.utils.Const;
import com.sergeydeveloper7.cityemergencyservice.view.holders.RegisterButtonViewHolder;
import com.sergeydeveloper7.cityemergencyservice.view.holders.RequestViewHolder;
import com.sergeydeveloper7.cityemergencyservice.view.holders.WorkerViewHolder;

import java.util.ArrayList;

/**
 * Created by serg on 23.01.18.
 */

public class AdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int         VIEW_TYPE_REQUEST = 0;
    private final int         VIEW_TYPE_WORKER = 1;
    private Context           context;
    private ArrayList<String> index;

    public AdminAdapter(Context context, ArrayList<String> index) {
        this.context = context;
        this.index = index;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_REQUEST) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid, parent, false);
            return new RequestViewHolder(layoutView);
        } else if (viewType == VIEW_TYPE_WORKER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker, parent, false);
            return new WorkerViewHolder(layoutView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RegisterButtonViewHolder) {
            RequestViewHolder viewHolder = (RequestViewHolder) holder;

        } else if(holder instanceof WorkerViewHolder) {
            WorkerViewHolder viewHolder = (WorkerViewHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return this.index == null ? 0 : this.index.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch(index.get(position)){
            case Const.REGISTER_FIELD_REQUEST:
                return VIEW_TYPE_REQUEST;
            default:
                return VIEW_TYPE_WORKER;
        }
    }

}
